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
import ijeoma.motion.MotionConstant;
import ijeoma.util.Logger;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import processing.core.PApplet;

public class Motion implements MotionConstant, Comparator<Motion>,
		Comparable<Motion> {
	protected static PApplet parent;
	protected static Class parentClass;

	protected String name;
	protected String type;

	protected Property[] properties;
	protected ArrayList<Callback> callbacks = new ArrayList();

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

	protected int repeatIndex = 0;
	protected int repeatCount = 0;

	protected boolean isPlaying = false;
	protected boolean isRepeating = false;

	public boolean isAutoUpdating = true;

	protected String timeMode = FRAMES;
	protected String repeatMode = NO_REVERSE;
	protected String reverseMode = REVERSE_TIME;

	protected float reverseTime = 0;

	protected ArrayList<MotionEventListener> listeners;

	protected Method motionStartedMethod, motionEndedMethod,
			motionChangedMethod, motionRepeatedMethod;

	public Logger logger;

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
		// setup("defualt", null, duration, delay, easing);
		setup("default", new Property[] { new Property("default", 0, 0) },
				duration, delay, easing);
		setupEvents();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param _name
	 *            the id of the tween
	 * @param _begin
	 *            the position you want the tween to begin at
	 * @param _end
	 *            the position you want the tween to end at
	 * @param _duration
	 *            how many seconds/frames you want the tween to take from begin
	 *            to the end
	 */
	public Motion(String _name, float _begin, float _end, float _duration) {
		setup(_name, _begin, _end, _duration, 0, LINEAR_BOTH);
		setupEvents();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param _name
	 *            the id of the tween
	 * @param _begin
	 *            the position you want the tween to begin at
	 * @param _end
	 *            the position you want the tween to end at
	 * @param _duration
	 *            how many seconds/frames you want the tween to take from begin
	 *            to the end
	 * @param _delay
	 *            how many seconds/frames you want the tween to delay before
	 *            starting
	 * @param _easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Motion(String _name, float _begin, float _end, float _duration,
			float _delay) {
		setup(_name, _begin, _end, _duration, _delay, LINEAR_BOTH);
		setupEvents();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param _name
	 *            the id of the tween
	 * @param _begin
	 *            the position you want the tween to begin at
	 * @param _end
	 *            the position you want the tween to end at
	 * @param _duration
	 *            how many seconds/frames you want the tween to take from begin
	 *            to the end
	 * @param _delay
	 *            how many seconds/frames you want the tween to delay before
	 *            starting
	 * @param _easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Motion(String _name, float _begin, float _end, float _duration,
			float _delay, String _easing) {
		setup(_name, _begin, _end, _duration, _delay, _easing);
		setupEvents();
	}

	public Motion(String _name, String[] _motionObjectProperties,
			float _duration, float _delay, String _easing) {
		Property[] parameters = parseProperties(parent, _motionObjectProperties);

		setup(_name, parameters, _duration, _delay, _easing);
		setupEvents();
	}

	public Motion(String _name, String[] _motionObjectProperties,
			float _duration, float _delay) {
		Property[] parameters = parseProperties(parent, _motionObjectProperties);

		setup(_name, parameters, _duration, _delay, easing);
		setupEvents();
	}

	public Motion(String _name, String[] _motionObjectProperties,
			float _duration) {
		Property[] parameters = parseProperties(parent, _motionObjectProperties);

		setup(_name, parameters, _duration, delay, easing);
		setupEvents();
	}

	public Motion(String _name, Object _motionObject,
			String[] _motionObjectProperties, float _duration, float _delay,
			String _easing) {
		Property[] parameters = parseProperties(_motionObject,
				_motionObjectProperties);

		setup(_name, parameters, _duration, _delay, _easing);
		setupEvents();
	}

	public Motion(String _name, Object _motionObject,
			String[] _motionObjectProperties, float _duration, float _delay) {
		Property[] parameters = parseProperties(_motionObject,
				_motionObjectProperties);

		setup(_name, parameters, _duration, _delay, easing);
		setupEvents();
	}

	public Motion(String _name, Object _motionObject,
			String[] _motionObjectProperties, float _duration) {
		Property[] parameters = parseProperties(_motionObject,
				_motionObjectProperties);

		setup(_name, parameters, _duration, delay, easing);
		setupEvents();
	}

	public Motion(String _name, Property[] _properties, float _duration,
			float _delay, String _easing) {
		setup(_name, _properties, _duration, _delay, _easing);
		setupEvents();
	}

	public Motion(String _name, Property[] _properties, float _duration,
			float _delay) {
		setup(_name, _properties, _duration, _delay, easing);
		setupEvents();
	}

	public Motion(String _name, Property[] _properties, float _duration) {
		setup(_name, _properties, _duration, delay, easing);
		setupEvents();
	}

	protected void setup(String _name, float _begin, float _end,
			float _duration, float _delay, String _easing) {

		setup(_name, new Property[] { new Property("default", _begin, _end) },
				_duration, _delay, _easing);
	}

	protected void setup(String _name, Property[] _properties, float _duration,
			float _delay, String _easing) {

		getParent().registerPre(this);

		name = _name;
		type = this.getClass().getSimpleName();

		if (_properties == null)
			properties = new Property[1];
		else
			properties = _properties;

		duration = _duration;

		delay = _delay;

		playTime = 0;
		beginTime = 0;

		setEasing(_easing);

		listeners = new ArrayList<MotionEventListener>();

		logger = new Logger();
		logger.disable();
	}

	public static void setup(PApplet _parent) {
		Motion.parent = _parent;

		parentClass = _parent.getClass();

		System.out.println("IjeomaMotion " + VERSION + " "
				+ " http://www.ekeneijeoma.com/processing/ijeomamotion");
	}

	protected void setupEvents() {
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

	public Property[] parseProperties(Object _motionObject,
			String[] _motionObjectProperties) {
		// PApplet.println("Motion.parseProperties()");

		Property[] motionProperties = new Property[_motionObjectProperties.length];

		for (int i = 0; i < _motionObjectProperties.length; i++) {
			String[] motionObjectProperty = PApplet
					.trim(_motionObjectProperties[i].split("[:,]"));

			if (_motionObject == null) {
				if (motionObjectProperty.length == 2)
					motionProperties[i] = new Property(parent,
							motionObjectProperty[0],
							Float.parseFloat(motionObjectProperty[1]));
				else if (motionObjectProperty.length == 3)
					motionProperties[i] = new Property(parent,
							motionObjectProperty[0],
							Float.parseFloat(motionObjectProperty[1]),
							Float.parseFloat(motionObjectProperty[2]));
			} else {
				if (motionObjectProperty.length == 2)
					motionProperties[i] = new Property(_motionObject,
							motionObjectProperty[0],
							Float.parseFloat(motionObjectProperty[1]));
				else if (motionObjectProperty.length == 3)
					motionProperties[i] = new Property(_motionObject,
							motionObjectProperty[0],
							Float.parseFloat(motionObjectProperty[1]),
							Float.parseFloat(motionObjectProperty[2]));
			}
		}

		return motionProperties;
	}

	/**
	 * Starts the tween from the beginning position
	 */
	public void play() {
		isPlaying = true;

		seek(0);
		// resume();

		time = beginTime;

		beginTime = (timeMode == SECONDS) ? (getParent().millis() - beginTime * 1000)
				: (getParent().frameCount - beginTime);

		dispatchMotionStartedEvent();

		repeatIndex = 0;
	}

	/**
	 * Ends the tween at the ending position
	 */
	public void stop() {
		if (isRepeating && (repeatCount == 0 || repeatIndex < repeatCount)) {

			// PApplet.println("repeatIndex = " + repeatIndex);
			if (repeatMode == REVERSE)
				reverse();

			seek(0);
			resume();

			dispatchMotionRepeatedEvent();

			repeatIndex++;
		} else {
			isPlaying = false;

			if (repeatMode == REVERSE)
				reverse();

			seek(1);

			dispatchMotionEndedEvent();

			repeatIndex = 0;
		}
	}

	/**
	 * Pauses the tween
	 */
	public void pause() {
		isPlaying = false;

		beginTime = time;

		// dispatchMotionEndedEvent();
	}

	/**
	 * Resumes the tween
	 */
	public void resume() {
		isPlaying = true;

		time = beginTime;

		beginTime = (timeMode == SECONDS) ? (getParent().millis() - beginTime * 1000)
				: (getParent().frameCount - beginTime);

		// if(time == 0)
		// dispatchMotionStartedEvent();
	}

	/**
	 * Changes the time of the tween to a time percentange between 0 and 1
	 */
	public void seek(float _value) {
		beginTime = time = _value * getDelayDuration();

		// if (isReversingTime() && reverseTime != 0)
		// beginTime = time = reverseTime - time;

		updatePosition();
	}

	public float getSeekPosition() {
		return time / getDelayDuration();
	}

	/**
	 * Sets the tween to repeat after ending
	 */
	public void repeat() {
		isRepeating = true;
	}

	/**
	 * 
	 */
	public void repeat(int _repeatCount) {
		isRepeating = true;
		repeatCount = _repeatCount;
	}

	/**
	 * 
	 */
	public void repeat(String _repeatMode) {
		isRepeating = true;
		repeatMode = _repeatMode;
	}

	/**
	 * Sets tweens to not repeat after ending
	 */
	public void noRepeat() {
		isRepeating = false;
		repeatCount = 0;
	}

	/**
	 * Sets the tween to reverse
	 */
	public void reverse() {
		if (reverseMode == REVERSE_POSITION)
			properties[0].reverse();
		else if (reverseMode == REVERSE_TIME)
			reverseTime = (reverseTime == 0) ? duration : 0;
	}

	public void update() {
		if (isPlaying()) {
			updateTime();

			if (time > delay)
				if (time > 0 && time < getDelayDuration())
					updatePosition();
				else
					stop();
			else
				stop();
		}
	}

	public void update(float _time) {
		if (isPlaying()) {
			setTime(_time);

			if (time > delay)
				if (time > 0 && time < getDelayDuration())
					updatePosition();
				else
					stop();
			else
				stop();
		}
	}

	public void updateCallbacks() {
		for (Callback callback:callbacks)
			callback.update();
	}

	protected void updateTime() {
		time = ((timeMode == SECONDS) ? ((getParent().millis() - beginTime) / 1000)
				: (getParent().frameCount - beginTime))
				* timeScale;

		if (isReversingTime() && reverseTime != 0)
			time = reverseTime - time;
	}

	protected void updatePosition() {
		try {
			for (int i = 0; i < properties.length; i++) {
				if (easing.equals(CUBIC_BEZIER_IN)
						|| easing.equals(CUBIC_BEZIER_OUT)
						|| easing.equals(CUBIC_BEZIER_BOTH)) {
					Object[] args = { new Float(time),
							new Float(properties[i].getBegin()),
							new Float(properties[i].getChange()),
							new Float(getDuration()),
							new Float(properties[i].getBegin()),
							new Float(properties[i].getEnd()) };

					properties[i].setPosition(((Float) easingMethod.invoke(
							parent, args)).floatValue());
				} else {

					Object[] args = { new Float(getTime()),
							new Float(properties[i].getBegin()),
							new Float(properties[i].getChange()),
							new Float(getDuration()) };

					properties[i].setPosition(((Float) easingMethod.invoke(
							parent, args)).floatValue());
				}
			}

			dispatchMotionChangedEvent();

			updateCallbacks();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCallback(Callback _c) {
		callbacks.add(_c);
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

	public void setName(String _name) {
		name = _name;
	}

	public String getName() {
		return name;
	}

	public void setPlayTime(float _playTime) {
		playTime = _playTime;
	}

	public float getPlayTime() {
		return playTime;
	}

	public float getDelayPlayTime() {
		return playTime + delay;
	}

	protected void setTime(float _time) {
		logger.println(this.name + ".setTime(" + _time + ")");

		time = _time;
	}

	public float getTime() {
		return (time < delay) ? 0f : (time - delay);
	}

	public float getDelayTime() {
		return (float) time;
	}

	public void setTimeScale(float _timeScale) {
		timeScale = _timeScale;
	}

	public float getTimeScale() {
		return timeScale;
	}

	public void setPosition(float _position) {
		properties[0].setPosition(_position);
	}

	public float getPosition() {
		return properties[0].getPosition();
	}

	public void setBegin(float _begin) {
		// PApplet.println("setBegin(" + _begin + ")");
		properties[0].setBegin(_begin);
	}

	public float getBegin() {
		return properties[0].getBegin();
	}

	public void setEnd(float _end) {
		properties[0].setEnd(_end);
	}

	public float getEnd() {
		return properties[0].getEnd();
	}

	public void setChange(float _change) {
		properties[0].setChange(_change);
	}

	public float getChange() {
		return properties[0].getChange();
	}

	public void setDuration(float _duration) {
		duration = _duration;
	}

	public float getDuration() {
		return duration;
	}

	public float getDelayDuration() {
		return duration + delay;
	}

	public void setDelay(float _delay) {
		delay = _delay;
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
	public void setEasing(String _easing) {
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
	}

	public String getEasing() {
		return easing;
	}

	public void noEasing() {
		setEasing(MotionConstant.LINEAR_BOTH);
	}

	/**
	 * @param _timeMode
	 *            SECONDS, FRAMES
	 */
	public void setTimeMode(String _timeMode) {
		timeMode = _timeMode;
	}

	public String getTimeMode() {
		return timeMode;
	}

	// /**
	// * @param _repeatMode
	// * REVERSE, NO_REVERSE
	// */
	public void setRepeatMode(String _repeatMode) {
		repeatMode = _repeatMode;
	}

	public String getRepeatMode() {
		return repeatMode;
	}

	public void setRepeatCount(int _repeatCount) {
		repeatCount = _repeatCount;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setReverseMode(String _reverseMode) {
		reverseMode = _reverseMode;
	}

	public String getReverseMode() {
		return reverseMode;
	}

	public void setAutoUpdate(boolean _value) {
		isAutoUpdating = _value;
	}

	public boolean getAutoUpdate() {
		return isAutoUpdating;
	}

	boolean isDelaying() {
		return (time >= delay);
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	boolean isReversingTime() {
		return repeatMode == REVERSE && reverseMode == REVERSE_TIME;
	}

	public boolean isTimeInside() {
		return (getDelayPlayTime() > 0 && getDelayPlayTime() < getDelayDuration()) ? true
				: false;
	}

	public boolean isTimeBelow(float _value) {
		return (_value <= getDelayPlayTime() + getDelayDuration()) ? true
				: false;
	}

	public boolean isTimeAbove(float _value) {
		return (_value >= getDelayPlayTime() + getDelayDuration()) ? true
				: false;
	}

	public boolean isTimeInside(float _value) {
		return (_value >= getDelayPlayTime() && _value <= getDelayPlayTime()
				+ getDelayDuration()) ? true : false;
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

	public boolean isTweenParallel() {
		return type.equals(TWEEN_PARALLEL);
	}

	public boolean isTweenSequence() {
		return type.equals(TWEEN_SEQUENCE);
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

	private String firstCharToUpperCase(String _s) {
		return Character.toUpperCase(_s.charAt(0)) + _s.substring(1);
	}

	private String firstCharToLowerCase(String _s) {
		return Character.toLowerCase(_s.charAt(0)) + _s.substring(1);
	}

	public String getTimeCode() {
		return PApplet.str(getDuration() - getTime());
	}

	public int compareTo(Motion _motion) {
		return (int) (playTime - _motion.getPlayTime());
	}

	public int compare(Motion _motion1, Motion _motion2) {
		return (int) (_motion2.getPlayTime() - _motion1.getPlayTime());
	}
}