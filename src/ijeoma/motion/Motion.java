/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package ijeoma.motion;

import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;

public class Motion implements MotionConstant, Comparator<Motion>,
		Comparable<Motion> {
	protected static PApplet parent;
	protected static Class parentClass;

	protected String name = "";

	protected ArrayList<Callback> calls = new ArrayList<Callback>();
	public HashMap<String, Callback> callMap = new HashMap<String, Callback>();

	protected float playTime;
	protected float playCount = 0;

	protected float time = 0f;
	protected float timeScale = 1f;

	protected float duration = 0f;

	protected float delay = 0f;

	protected String easing = LINEAR_IN;
	protected Class<?> easingClass;
	protected Method easingMethod;

	protected int repeatCount = 0;
	protected int repeatDuration = 0;

	protected boolean isPlaying = false;
	// protected boolean isDelaying = false;
	protected boolean isRepeating = false;
	protected boolean isRepeatingDelay = false;
	protected boolean isReversing = false;

	public boolean isAutoUpdating = true;

	protected boolean isRegistered = false;

	protected String timeMode = FRAMES;

	protected float reverseTime = 0;

	protected int order = 0;

	protected ArrayList<MotionEventListener> listeners = new ArrayList<MotionEventListener>();

	protected Method motionStartedMethod, motionEndedMethod,
			motionChangedMethod, motionRepeatedMethod;

	protected Class<?> motionStartedParameterClass, motionEndedParameterClass,
			motionChangedParameterClass, motionRepatedParameterClass;

	private static class LibraryNotInitializedException extends
			NullPointerException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		LibraryNotInitializedException() {
			super(
					"Call Motion.setup(this); before using the other Motion objects");
		}
	}

	/**
	 * Constructs a Motion
	 * 
	 */
	public Motion(String name, float duration, float delay, String easing) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	public Motion(String name, float duration, float delay) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	public Motion(String name, float duration) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	public Motion(float duration, float delay, String easing) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	public Motion(float duration, float delay) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	public Motion(float duration) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	public Motion(String name) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	public Motion() {
		setup("", duration, delay, easing);
		setupEvents();
	}

	protected void setup(String name, float duration, float delay, String easing) {
		this.name = name;

		this.duration = duration;

		this.delay = delay;

		playTime = 0;

		setEasing(easing);
	}

	protected void setup(float duration, float delay, String easing) {
		setup("", duration, delay, easing);
	}

	public static void setup(PApplet parent) {
		Motion.parent = parent;
	}

	protected void setupEvents() {
		// getParent().registerPre(this);

		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			motionStartedMethod = parentClass.getMethod(
					MotionEvent.MOTION_STARTED, new Class[] { Motion.class });
		} catch (Exception e) {
		}

		try {
			motionEndedMethod = parentClass.getMethod(MotionEvent.MOTION_ENDED,
					new Class[] { Motion.class });
		} catch (Exception e) {
		}

		try {
			motionChangedMethod = parentClass.getMethod(
					MotionEvent.MOTION_CHANGED, new Class[] { Motion.class });
		} catch (Exception e) {
		}

		try {
			motionRepeatedMethod = parentClass.getMethod(
					MotionEvent.MOTION_REPEATED, new Class[] { Motion.class });
		} catch (Exception e) {
		}
	}

	public void update() {
		if (isRegistered)
			if (isPlaying) {
				updateTime();
				updateCalls();

				if (!isInsideDelayingTime(time) && !isInsidePlayingTime(time))
					stop();
			}
	}

	public void update(float time) {
		if (isInsidePlayingTime(time)) {
			if (!isPlaying)
				play();

			setTime(time);
			updateCalls();
		} else if (isPlaying)
			stop();
	}

	public void updateCalls() {
		for (Callback c : calls)
			if (time > c.getTime()) {
				if (!c.hasInvoked() || c.getTime() < 0)
					c.invoke();
			} else
				c.noInvoke();
	}

	protected void updateTime() {
		time = ((timeMode == SECONDS) ? ((parent.millis() - playTime) / 1000)
				: (parent.frameCount - playTime)) * timeScale;

		if (isReversing && reverseTime != 0)
			time = reverseTime - time;
	}

	public void pre() {
		if (isAutoUpdating)
			update();
	}

	/**
	 * Starts the tween from the beginning position
	 */
	public Motion play() {
		// PApplet.println(this + ".play()");

		seek(0);
		resume();

		playCount++;
		repeatCount = 0;

		dispatchMotionStartedEvent();

		return this;
	}

	/**
	 * Ends the tween at the ending position
	 */
	public Motion stop() {
		// PApplet.println(this + ".stop()");

		reverseTime = (reverseTime == 0) ? duration : 0;

		if (isRepeating
				&& (repeatDuration == 0 || repeatCount < repeatDuration)) {
			seek(0);
			resume();

			if (!isRepeatingDelay)
				delay = 0;

			repeatCount++;

			dispatchMotionRepeatedEvent();
		} else {
			seek(1);
			pause();

			repeatCount = 0;

			dispatchMotionEndedEvent();
		}

		return this;
	}

	/**
	 * Pauses the tween
	 */
	public Motion pause() {
		if (isRegistered) {
			parent.unregisterMethod("pre", this);
			isRegistered = false;
		}

		isPlaying = false;

		playTime = time;

		return this;
	}

	/**
	 * Resumes the tween
	 */
	public Motion resume() {
		if (!isRegistered) {
			parent.registerMethod("pre", this);
			isRegistered = true;
		}

		isPlaying = true;

		playTime = (timeMode == SECONDS) ? (parent.millis() - playTime * 1000)
				: (parent.frameCount - playTime);

		return this;
	}

	/**
	 * Changes the time of the tween to a time percentage between 0 and 1
	 */
	public Motion seek(float value) {
		playTime = (delay + duration) * value;

		if (playTime != time) {
			setTime(playTime);
			updateCalls();
		}

		return this;
	}

	public Motion delay(float delay) {
		this.delay = delay;
		// isDelaying = true;

		return this;
	}

	public Motion noDelay() {
		delay = 0;
		// isDelaying = false;

		return this;
	}

	public float getDelay() {
		return delay;
	}

	public Motion repeatDelay() {
		isRepeating = true;
		isRepeatingDelay = true;
		return this;
	}

	public Motion noRepeatDelay() {
		isRepeating = false;
		isRepeatingDelay = false;
		return this;
	}

	/**
	 * Sets the tween to repeat after ending
	 */
	public Motion repeat() {
		isRepeating = true;
		return this;
	}

	/**
	 * 
	 */
	public Motion repeat(int repeat) {
		isRepeating = true;
		repeatDuration = repeat;
		return this;
	}

	/**
	 * Sets tweens to not repeat after ending
	 */
	public Motion noRepeat() {
		isRepeating = false;
		repeatDuration = 0;
		return this;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	/**
	 * Sets the tween to reverse
	 */
	public Motion reverse() {
		isReversing = true;
		return this;
	}

	public Motion noReverse() {
		isReversing = false;
		return this;
	}

	public Motion setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	protected void setTime(float time) {
		this.time = time;

		if (isReversing && reverseTime != 0)
			time = reverseTime - time;
	}

	public float getTime() {
		return (time < delay) ? 0f : (time - delay);
	}

	public Motion setTimeScale(float timeScale) {
		this.timeScale = timeScale;

		return this;
	}

	public float getTimeScale() {
		return timeScale;
	}

	public float getPosition() {
		return getTime() / duration;
	}

	public Motion setDuration(float duration) {
		this.duration = duration;

		return this;
	}

	public float getDuration() {
		return duration;
	}

	/**
	 * @param easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Motion setEasing(String easing) {
		this.easing = easing;

		int index = easing.indexOf("Ease");

		String easingClassName = "ijeoma.motion.easing."
				+ firstCharToUpperCase(easing.substring(0, index));
		String easingMethodName = firstCharToLowerCase(easing.substring(index,
				easing.length()));

		try {
			easingClass = Class.forName(easingClassName);
			easingMethod = easingClass.getMethod(easingMethodName, new Class[] {
					Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE });
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return this;
	}

	public String getEasing() {
		return easing;
	}

	public Motion noEasing() {
		setEasing(MotionConstant.LINEAR_BOTH);

		return this;
	}

	/**
	 * @param timeMode
	 *            SECONDS, FRAMES
	 */
	public Motion setTimeMode(String timeMode) {
		this.timeMode = timeMode;

		return this;
	}

	public String getTimeMode() {
		return timeMode;
	}

	protected void setOrder(int index) {
		order = index;
	}

	public int getOrder() {
		return order;
	}

	public Motion autoUpdate() {
		isAutoUpdating = true;

		return this;
	}

	public Motion noAutoUpdate() {
		isAutoUpdating = false;

		return this;
	}

	public boolean isAutoUpdating() {
		return isAutoUpdating;
	}

	public boolean isDelaying() {
		return (time < delay);
		// isDelaying;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	boolean isReversing() {
		return isReversing;
	}

	public boolean isInsideDelayingTime(float value) {
		return (value > 0 && value <= delay);
	}

	public boolean isInsidePlayingTime(float value) {
		return (value > delay && value <= delay + duration);
	}

	public boolean isAbovePlayingTime(float value) {
		return value > delay + duration;
	}

	public static PApplet getParent() {
		if (parent == null)
			throw new LibraryNotInitializedException();

		return parent;
	}

	public Motion call(Object object, String name) {
		return addCall(new Callback(this, object, name, duration));
	}

	public Motion call(Object object, String name, float time) {
		return addCall(new Callback(this, object, name, time));
	}

	public Motion onStart(Object object, String name) {
		return addCall(new Callback(this, object, name, 0));
	}

	public Motion onEnd(Object object, String name) {
		return addCall(new Callback(this, object, name, duration));
	}

	public Motion onChange(Object object, String name) {
		return addCall(new Callback(this, object, name, -1));
	}

	public Motion addCall(Callback call) {
		calls.add(call);
		return this;
	}

	/**
	 * Returns a Callback by id/index (useful if you're only controlling
	 * Callbacks)
	 */
	public Callback getCallback(int index) {
		if (index < calls.size())
			return calls.get(index);
		else
			return null;
	}

	/**
	 * Returns a Callback by name (useful if you're only controlling Callbacks)
	 */
	public Callback getCallback(String name) {
		return callMap.get(name);
	}

	/**
	 * Returns all Callbacks
	 */
	public Callback[] getCallbacks() {
		return calls.toArray(new Callback[calls.size()]);
	}

	/**
	 * Returns all Callbacks
	 */
	public List<Callback> getCallbackList() {
		return calls;
	}

	/**
	 * Returns child Callback object count
	 */
	public int getCallbackCount() {
		return calls.size();
	}

	public Motion addEventListener(MotionEventListener listener) {
		listeners.add(listener);

		return this;
	}

	public void removeEventListener(MotionEventListener listener) {
		listeners.remove(listener);
	}

	protected synchronized void dispatchEvent(String eventType) {
		MotionEvent te = new MotionEvent(this, eventType);

		Iterator<MotionEventListener> ls = listeners.iterator();

		while (ls.hasNext()) {
			ls.next().onMotionEvent(te);
		}
	}

	protected void dispatchMotionStartedEvent() {
		if (motionStartedMethod != null) {
			try {
				motionStartedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				motionStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.MOTION_STARTED);
	}

	protected void dispatchMotionEndedEvent() {
		if (motionEndedMethod != null) {
			try {
				motionEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				motionEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.MOTION_ENDED);
	}

	protected void dispatchMotionChangedEvent() {
		if (motionChangedMethod != null) {
			try {
				motionChangedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				motionChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.MOTION_CHANGED);
	}

	protected void dispatchMotionRepeatedEvent() {
		if (motionRepeatedMethod != null) {
			try {
				motionRepeatedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				motionRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.MOTION_REPEATED);
	}

	public boolean isTween() {
		return this.getClass().getSimpleName().equals(TWEEN);
	}

	public boolean isParallel() {
		return this.getClass().getSimpleName().equals(PARALLEL);
	}

	public boolean isSequence() {
		return this.getClass().getSimpleName().equals(SEQUENCE);
	}

	public boolean isTimeline() {
		return this.getClass().getSimpleName().equals(TIMELINE);
	}

	public boolean isKeyFrame() {
		return this.getClass().getSimpleName().equals(KEYFRAME);
	}

	public boolean usingSeconds() {
		return timeMode.equals(SECONDS);
	}

	public boolean usingFrames() {
		return timeMode.equals(FRAMES);
	}

	private String firstCharToUpperCase(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	private String firstCharToLowerCase(String s) {
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}

	@Override
	public int compareTo(Motion motion) {
		return (int) (delay - motion.getDelay());
	}

	@Override
	public int compare(Motion motion1, Motion motion2) {
		return (int) (motion2.getDelay() - motion1.getDelay());
	}

	public String toString() {
		return "Motion[" + (name.equals("") ? "" : name) + "time: " + delay
				+ ", duration: " + getDuration() + "]";
	}
}