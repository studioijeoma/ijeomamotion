/**
 * ijeomamotion
 * A collection of utilities creating flash-like animations.
 * http://ekeneijeoma.com/processing/ijeomamotion/
 *
 * Copyright (C) 2012 Ekene Ijeoma http://ekeneijeoma.com
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
 * @author      Ekene Ijeoma http://ekeneijeoma.com
 * @modified    08/21/2012
 * @version     4 (26)
 */

package ijeoma.motion.tween;

import ijeoma.motion.Motion;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.property.ColorProperty;
import ijeoma.motion.property.NumberProperty;
import ijeoma.motion.property.PVectorProperty;
import ijeoma.motion.property.Property;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PVector;

public class Tween extends Motion { // implements Comparable
	protected ArrayList<Property> properties = new ArrayList<Property>();

	protected Method tweenStartedMethod, tweenEndedMethod, tweenChangedMethod,
			tweenRepeatedMethod;

	public Tween() {
		super();
	}

	/**
	 * Constructs a Tween
	 * 
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
	public Tween(float _duration, float _delay, String _easing) {
		setup(_duration, _delay, _easing);
		setupEvents();
	}

	public Tween(float _duration, float _delay) {
		setup(_duration, _delay, easing);
		setupEvents();
	}

	public Tween(float _duration) {
		setup(_duration, delay, easing);
		setupEvents();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param _name
	 *            the id of the tween
	 * @param _tweenObject
	 *            the object you want to tween
	 * @param _tweenObjectProperty
	 *            the parameter of the object you want to tween
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
	public Tween(Object _tweenObject, String _tweenObjectProperty, float _end,
			float _duration, float _delay, String _easing) {
		Property p = new NumberProperty(_tweenObject, _tweenObjectProperty,
				_end);

		setup(p, _duration, _delay, _easing);
		setupEvents();
	}

	public Tween(Object _tweenObject, String _tweenObjectProperty, float _end,
			float _duration, float _delay) {
		Property p = new NumberProperty(_tweenObject, _tweenObjectProperty,
				_end);

		setup(p, _duration, _delay, easing);
		setupEvents();
	}

	public Tween(Object _tweenObject, String _tweenObjectProperty, float _end,
			float _duration) {
		Property p = new NumberProperty(_tweenObject, _tweenObjectProperty,
				_end);

		setup(p, _duration, delay, easing);
		setupEvents();
	}

	protected void setup(Property _p, float _duration, float _delay,
			String easing) {
		setup(_duration, _delay, easing);
		addProperty(_p);
	}

	@Override
	protected void setupEvents() {
		super.setupEvents();

		Class<? extends PApplet> parentClass = getParent().getClass();

		try {
			tweenStartedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_STARTED, new Class[] { Tween.class });
		} catch (Exception e) {
		}

		try {
			tweenEndedMethod = parentClass.getMethod(MotionEvent.TWEEN_ENDED,
					new Class[] { Tween.class });
		} catch (Exception e) {
		}

		try {
			tweenChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_CHANGED, new Class[] { Tween.class });
		} catch (Exception e) {
		}

		try {
			tweenRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_REPEATED, new Class[] { Tween.class });
		} catch (Exception e) {
		}
	}

	@Override
	public void update() {
		if (isPlaying()) {
			updateTime();

			if (isAbovePlayTime(time))
				if (isBelowStopTime(time)) {
					updateCallbacks();
					updateProperties();
				} else
					stop();
		}
	}

	@Override
	public void update(float _time) {
		if (isPlaying()) {
			setTime(_time);

			if (isAbovePlayTime(time))
				if (isBelowStopTime(time)) {
					updateCallbacks();
					updateProperties();
				} else
					stop();
		}
	}

	protected void updateProperties() {
		try {
			for (Property p : properties) {
				// float t = getTime();
				//
				// if (isReversing && reverseTime != 0)
				// t = reverseTime - t;

				// Object[] args = { new Float(t), new Float(p.getBegin()),
				// new Float(p.getChange()), new Float(getDuration()) };
				// p.setPosition(((Float) easingMethod.invoke(parent, args))
				// .floatValue());

				Object[] args = { getPosition(), 0, 1, 1 };
				p.setPosition(((Float) easingMethod.invoke(parent, args))
						.floatValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Tween play() {
		return (Tween) super.play();
	}

	@Override
	public Tween stop() {
		return (Tween) super.stop();
	}

	@Override
	public Tween pause() {
		return (Tween) super.pause();
	}

	@Override
	public Tween resume() {
		return (Tween) super.resume();
	}

	@Override
	public Tween seek(float _value) {
		super.seek(_value);

		updateProperties();

		return this;
	}

	@Override
	public Tween repeat() {
		return (Tween) super.repeat();
	}

	@Override
	public Tween repeat(int _repeatDuration) {
		return (Tween) super.repeat(_repeatDuration);
	}

	@Override
	public Tween noRepeat() {
		return (Tween) super.noRepeat();
	}

	@Override
	public Tween reverse() {
		return (Tween) super.reverse();
	}

	@Override
	public Tween noReverse() {
		return (Tween) super.noReverse();
	}

	@Override
	public Tween setTimeScale(float _timeScale) {
		return (Tween) super.setTimeScale(_timeScale);
	}

	@Override
	public Tween setDuration(float _duration) {
		return (Tween) super.setDuration(_duration);
	}

	@Override
	public Tween setDelay(float _delay) {
		return (Tween) super.setDelay(_delay);
	}

	@Override
	public Tween setEasing(String _easing) {
		return (Tween) super.setEasing(_easing);
	}

	@Override
	public Tween noEasing() {
		return (Tween) super.noEasing();
	}

	@Override
	public Tween setTimeMode(String _timeMode) {
		return (Tween) super.setTimeMode(_timeMode);
	}

	@Override
	public Tween setRepeatDuration(int _repeatCount) {
		return (Tween) super.setRepeatDuration(_repeatCount);
	}

	@Override
	public Tween autoUpdate() {
		return (Tween) super.autoUpdate();
	}

	@Override
	public Tween noAutoUpdate() {
		return (Tween) super.noAutoUpdate();
	}

	public Tween addProperty(Property _p) {
		properties.add(_p);

		return this;
	}

	public Tween add(Property _p) {
		properties.add(_p);

		return this;
	}

	public Tween add(Object _object, String _name, float _end) {
		addProperty(new NumberProperty(_object, _name, _end));

		return this;
	}

	public Tween addNumber(Object _object, String _name, float _end) {
		addProperty(new NumberProperty(_object, _name, _end));

		return this;
	}

	public Tween addColor(Object _object, String _name, int _end) {
		addProperty(new ColorProperty(_object, _name, _end));

		return this;
	}

	public Tween addPVector(PVector _vector, PVector _end) {
		addProperty(new PVectorProperty(_vector, _end));

		return this;
	}

	public Property get(int _index) {
		return getProperty(_index);
	}

	public Property get(String _name) {
		return getProperty(_name);
	}

	public Property getProperty(int _index) {
		return properties.get(_index);
	}

	public Property getProperty(String _name) {
		Property mp = null;

		for (int i = 0; i < properties.size(); i++)
			if (properties.get(i).getName().equals(_name)) {
				mp = properties.get(i);
				break;
			}

		return mp;
	}

	public Property[] getProperties() {
		return properties.toArray(new Property[properties.size()]);
	}

	public int getPropertyCount() {
		return properties.size();
	}

	public String getPropertyName(int _index) {
		return properties.get(_index).getName();
	}

	public String[] getPropertyNames() {
		if (properties.size() > 1) {
			String[] propertyNames = new String[properties.size() - 1];

			for (int i = 1; i < properties.size() - 1; i++)
				propertyNames[i] = properties.get(i + 1).getName();

			return propertyNames;
		} else
			return new String[] {};
	}

	@Override
	public String toString() {
		// return "Tween[name: " + getName() + ", playTime: " + getPlayTime() +
		// ", begin: " + getBegin()
		// + ", end: " + getEnd() + ", duration: " + getDuration()
		// + ", properties[" + parameterNames + "]]";
		return "Tween[time: " + getPlayTime() + ", duration: " + getDuration()
				+ "]";
		// + ", properties[" + getPropertyNames() + "]]";
	}

	@Override
	public Tween addEventListener(MotionEventListener listener) {
		listeners.add(listener);
		return this;
	}

	@Override
	public void removeEventListener(MotionEventListener listener) {
		listeners.remove(listener);
	}

	@Override
	protected synchronized void dispatchEvent(String eventType) {
		MotionEvent te = new MotionEvent(this, eventType);

		Iterator<MotionEventListener> ls = listeners.iterator();

		while (ls.hasNext()) {
			ls.next().onMotionEvent(te);
		}
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		// logger.println("dispatchMotionStartedEvent tween");

		if (tweenStartedMethod != null) {
			try {
				tweenStartedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_STARTED);
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		if (tweenEndedMethod != null) {
			try {
				tweenEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_ENDED);
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		if (tweenChangedMethod != null) {
			try {
				tweenChangedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_CHANGED);
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		if (tweenRepeatedMethod != null) {
			try {
				tweenRepeatedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_REPEATED);
	}
}
