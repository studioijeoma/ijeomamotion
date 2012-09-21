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
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.Sequence;

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

	// protected int id;
	protected String type;

	protected ArrayList<Callback> calls = new ArrayList<Callback>();
	public HashMap<String, Callback> callMap = new HashMap<String, Callback>();

	protected float playTime = 0;

	protected float beginTime;
	protected float time = 0f;
	protected float timeScale = 1f;

	protected float duration = 0f;

	protected float delay = 0f;
	protected float delayTime = 0f;

	protected String easing = LINEAR_IN;
	protected Class<?> easingClass;
	protected Method easingMethod;

	protected int repeatCount = 0;
	protected int repeatDuration = 0;

	protected boolean isPlaying = false;
	protected boolean isRepeating = false;
	protected boolean isReversing = false;

	public boolean isAutoUpdating = true;

	protected String timeMode = FRAMES;

	protected float reverseTime = 0;

	protected ArrayList<MotionEventListener> listeners = new ArrayList<MotionEventListener>();

	protected Method motionStartedMethod, motionEndedMethod,
			motionChangedMethod, motionRepeatedMethod;

	// public Logger logger;

	private static class LibraryNotInitializedException extends
			NullPointerException {
		LibraryNotInitializedException() {
			super(
					"Call Motion.setup(this); before using the other Motion objects");
		}
	}

	/**
	 * Constructs a Tween
	 * 
	 */
	public Motion() {
		setup(duration, delay, easing);
		setupEvents();
	}

	protected void setup(float _duration, float _delay, String _easing) {
		duration = _duration;

		delay = _delay;

		playTime = 0;
		beginTime = 0;

		setEasing(_easing);

		type = this.getClass().getSimpleName();
	}

	public static void setup(PApplet _parent) {
		Motion.parent = _parent;

		parentClass = _parent.getClass();

		System.out.println("IjeomaMotion " + VERSION + " "
				+ " http://www.ekeneijeoma.com/processing/ijeomamotion");
	}

	protected void setupEvents() {
		getParent().registerPre(this);

		Class<? extends PApplet> parentClass = getParent().getClass();

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

	/**
	 * Starts the tween from the beginning position
	 */
	public Motion play() {
		// PApplet.println(this + ".play()");

		seek(0);

		time = beginTime;

		beginTime = (timeMode == SECONDS) ? (getParent().millis() - beginTime * 1000)
				: (getParent().frameCount - beginTime);

		repeatCount = 0;

		// if (delay == 0) {
		// setProperties();

		isPlaying = true;

		dispatchMotionStartedEvent();
		// }

		return this;
	}

	/**
	 * Ends the tween at the ending position
	 */
	public Motion stop() {
		// PApplet.println(this + ".stop()");

		if (isRepeating
				&& (repeatDuration == 0 || repeatCount < repeatDuration)) {

			// PApplet.println("repeatCount = " + repeatCount);
			if (isReversing)
				reverse();

			seek(0);
			resume();

			dispatchMotionRepeatedEvent();

			repeatCount++;
		} else {
			isPlaying = false;

			if (isReversing)
				reverse();

			seek(1.0f);

			dispatchMotionEndedEvent();

			repeatCount = 0;
		}

		return this;
	}

	/**
	 * Pauses the tween
	 */
	public Motion pause() {
		isPlaying = false;

		beginTime = time;

		// dispatchMotionEndedEvent();

		return this;
	}

	/**
	 * Resumes the tween
	 */
	public Motion resume() {
		isPlaying = true;

		time = beginTime;

		beginTime = (timeMode == SECONDS) ? (getParent().millis() - beginTime * 1000)
				: (getParent().frameCount - beginTime);

		// if(time == 0)
		// dispatchMotionStartedEvent();

		return this;
	}

	/**
	 * Changes the time of the tween to a time percentange between 0 and 1
	 */
	public Motion seek(float _value) {
		beginTime = time = getPlayTime() + _value * getDelayedDuration();

		updateCallbacks();

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
	public Motion repeat(int _repeatDuration) {
		isRepeating = true;
		repeatDuration = _repeatDuration;

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

	/**
	 * Sets the tween to reverse
	 */
	public Motion reverse() {
		isReversing = true;
		reverseTime = (reverseTime == 0) ? duration : 0;

		return this;
	}

	public Motion noReverse() {
		isReversing = false;
		// reverseTime = 0;

		return this;
	}

	public void update() {
		if (isPlaying()) {
			if (isAbovePlayTime(time))
				if (isBelowStopTime(time))
					updateCallbacks();
				else
					stop();

			updateTime();
		}
	}

	public void update(float _time) {
		if (isPlaying()) {
			if (isAbovePlayTime(time))
				if (isBelowStopTime(time))
					updateCallbacks();
				else
					stop();

			setTime(_time);
		}
	}

	public void updateCallbacks() {
		for (Callback c : calls)
			if (getTime() >= c.getTime() && getTime() <= c.getTime()) {
				// if (!c.hasInvoked())
				c.invoke();
			} else
				c.noInvoke();
	}

	protected void updateTime() {
		time = ((timeMode == SECONDS) ? ((getParent().millis() - beginTime) / 1000)
				: (getParent().frameCount - beginTime))
				* timeScale;
	}

	public void pre() {
		if (isAutoUpdating)
			update();
	}

	public static PApplet getParent() {
		if (parent == null)
			throw new LibraryNotInitializedException();

		return parent;
	}

	public Motion call(Object _object, String _name) {
		return addCall(new Callback(_object, _name, duration));
	}

	public Motion call(Object _object, String _name, float _time) {
		return addCall(new Callback(_object, _name, _time));
	}

	public Motion addCall(Callback _call) {
		calls.add(_call);
		return this;
	}

	/**
	 * Returns a Callback by id/index (useful if you're only controlling
	 * Callbacks)
	 */
	public Callback getCallback(int _index) {
		if (_index < calls.size())
			return calls.get(_index);
		else
			return null;
	}

	/**
	 * Returns a Callback by id/index (useful if you're only controlling
	 * Callbacks)
	 */
	public Callback getCallback(String _name) {
		return callMap.get(_name);
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

	public void setPlayTime(float _playTime) {
		playTime = _playTime;
	}

	public float getPlayTime() {
		return playTime;
	}

	public float getDelayedPlayTime() {
		return playTime + delay;
	}

	protected void setTime(float _time) {
		// logger.println(".setTime(" + _time + ")");

		time = _time;
	}

	public float getTime() {
		return (time < getDelayedPlayTime()) ? 0f
				: (time - getDelayedPlayTime());
	}

	public float getDelayTime() {
		return time;
	}

	public Motion setTimeScale(float _timeScale) {
		timeScale = _timeScale;

		return this;
	}

	public float getTimeScale() {
		return timeScale;
	}

	public float getPosition() {
		return getTime() / getDelayedDuration();
	}

	public Motion setDuration(float _duration) {
		duration = _duration;

		return this;
	}

	public float getDuration() {
		return duration;
	}

	public float getDelayedDuration() {
		return duration + delay;
	}

	public Motion setDelay(float _delay) {
		delay = _delay;

		return this;
	}

	public float getDelay() {
		return delay;
	}

	/**
	 * @param _easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Motion setEasing(String _easing) {
		easing = _easing;

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
	 * @param _timeMode
	 *            SECONDS, FRAMES
	 */
	public Motion setTimeMode(String _timeMode) {
		timeMode = _timeMode;

		return this;
	}

	public String getTimeMode() {
		return timeMode;
	}

	public Motion setRepeatDuration(int _repeatDuration) {
		repeatCount = _repeatDuration;

		return this;
	}

	public int getRepeatDuration() {
		return repeatCount;
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

	boolean isDelaying() {
		return (time >= delay);
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	boolean isReversing() {
		return isReversing;
	}

	public boolean isAbovePlayTime(float _value) {
		return (_value >= getDelayedPlayTime() + 1) ? true : false;
	}

	public boolean isBelowStopTime(float _value) {
		return (_value <= getDelayedPlayTime() + getDuration() + 1) ? true
				: false;
	}

	public boolean isInsidePlayingTime(float _value) {
		// return (_value >= getDelayedPlayTime() && _value <=
		// getDelayedPlayTime()
		// + getDelayedDuration()) ? true : false;
		return (_value >= getDelayedPlayTime() && _value <= getDelayedPlayTime()
				+ getDuration() + 1) ? true : false;
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
		// logger.println("dispatchMotionStartedEvent motion");

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

	public String getType() {
		return type;
	}

	public boolean isTween() {
		return type.equals(TWEEN);
	}

	public boolean isProperty() {
		return type.equals(PROPERTY);
	}

	public boolean isParallel() {
		return type.equals(PARALLEL);
	}

	public boolean isSequence() {
		return type.equals(SEQUENCE);
	}

	public boolean isTimeline() {
		return type.equals(TIMELINE);
	}

	public boolean isKeyFrame() {
		return type.equals(KEYFRAME);
	}

	public boolean isCallback() {
		return type.equals(CALLBACK);
	}

	public boolean usingSeconds() {
		return timeMode.equals(SECONDS);
	}

	public boolean usingFrames() {
		return timeMode.equals(FRAMES);
	}

	private String firstCharToUpperCase(String _s) {
		return Character.toUpperCase(_s.charAt(0)) + _s.substring(1);
	}

	private String firstCharToLowerCase(String _s) {
		return Character.toLowerCase(_s.charAt(0)) + _s.substring(1);
	}

	// public String getTimeCode() {
	// return PApplet.str(getDuration() - getTime());
	// }

	@Override
	public int compareTo(Motion _motion) {
		return (int) (playTime - _motion.getPlayTime());
	}

	@Override
	public int compare(Motion _motion1, Motion _motion2) {
		return (int) (_motion2.getPlayTime() - _motion1.getPlayTime());
	}
}