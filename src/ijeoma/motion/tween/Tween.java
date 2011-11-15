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
 
package ijeoma.motion.tween;

import ijeoma.motion.Motion;
import ijeoma.motion.MotionController;
import ijeoma.motion.Property;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;

import java.lang.reflect.Method;

import java.util.Iterator;

import processing.core.PApplet;

public class Tween extends Motion { // implements Comparable

	protected Method tweenStartedMethod, tweenEndedMethod, tweenChangedMethod,
			tweenRepeatedMethod;

	public Tween() {
		super();
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
	public Tween(String _name, float _begin, float _end, float _duration,
			float _delay, String _easing) {
		super(_name, _begin, _end, _duration, _delay, _easing);
	}

	public Tween(String _name, float _begin, float _end, float _duration,
			float _delay) {
		super(_name, _begin, _end, _duration, _delay);
	}

	public Tween(String _name, float _begin, float _end, float _duration) {
		super(_name, _begin, _end, _duration);
	}

	public Tween(String _name, Property[] _tweenObjectProperties,
			float _duration, float _delay, String _easing) {
		super(_name, _tweenObjectProperties, _duration, _delay, _easing);
	}

	public Tween(String _name, Property[] _tweenObjectProperties,
			float _duration, float _delay) {
		super(_name, _tweenObjectProperties, _duration, _delay);
	}

	public Tween(String _name, Property[] _tweenObjectProperties, float _duration) {
		super(_name, _tweenObjectProperties, _duration);
	}
	
	/**
	 * Constructs a Tween
	 * 
	 * @param _name
	 *            the id of the tween
	 * @param _tweenObjectProperties
	 *            the properties of the object you want to tween
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

	public Tween(String _name, String[] _tweenObjectProperties,
			float _duration, float _delay, String _easing) {
		super(_name, _tweenObjectProperties, _duration, _delay, _easing);
	}

	public Tween(String _name, String[] _tweenObjectProperties,
			float _duration, float _delay) {
		super(_name, _tweenObjectProperties, _duration, _delay);
	}

	public Tween(String _name, String[] _tweenObjectProperties, float _duration) {
		super(_name, _tweenObjectProperties, _duration);
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param _name
	 *            the id of the tween
	 * @param _tweenObject
	 *            the object you want to tween
	 * @param _tweenObjectProperties
	 *            the parameter of the object you want to tween
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
	public Tween(String _name, Object _tweenObject,
			String[] _tweenObjectProperties, float _duration, float _delay,
			String _easing) {
		super(_name, _tweenObject, _tweenObjectProperties, _duration, _delay,
				_easing);
	}

	public Tween(String _name, Object _tweenObject,
			String[] _tweenObjectProperties, float _duration, float _delay) {
		super(_name, _tweenObject, _tweenObjectProperties, _duration, _delay);
	}

	public Tween(String _name, Object _tweenObject,
			String[] _tweenObjectProperties, float _duration) {
		super(_name, _tweenObject, _tweenObjectProperties, _duration);
	}

	@Override
	protected void setupEvents() {
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

	public void addProperty(Property _mp) {
		if (properties[0] == null)
			properties[0] = _mp;
		else
			properties = (Property[]) PApplet.append(properties, _mp);
	}

	public float getPosition(int _index) {
		return getProperty(_index).getPosition();
	}

	public void setBegin(int _index, float _begin) {
		getProperty(_index).setBegin(_begin);
	}

	public float getBegin(int _index) {
		return getProperty(_index).getBegin();
	}

	public void setEnd(int _index, float _end) {
		Property mp = getProperty(_index);

		mp.setBegin(mp.getPosition());
		mp.setEnd(_end);
	}

	public float getEnd(int _index) {
		return getProperty(_index).getEnd();
	}

	public void setChange(int _index, float _change) {
		getProperty(_index).setChange(_change);
	}

	public float getChange(int _index) {
		return getProperty(_index).getChange();
	}

	public float getPosition(String _name) {
		return getProperty(_name).getPosition();
	}

	public void setBegin(String _name, float _begin) {
		getProperty(_name).setBegin(_begin);
	}

	public float getBegin(String _name) {
		return getProperty(_name).getBegin();
	}

	public void setEnd(String _name, float _end) {
		Property mp = getProperty(_name);

		mp.setBegin(mp.getPosition());
		mp.setEnd(_end);
	}

	public float getEnd(String _name) {
		return getProperty(_name).getEnd();
	}

	public void setChange(String _name, float _change) {
		getProperty(_name).setChange(_change);
	}

	public float getChange(String _name) {
		return getProperty(_name).getChange();
	}

	public Property get(int _index) {
		return getProperty(_index);
	}

	public Property get(String _name) {
		return getProperty(_name);
	}

	public Property getProperty(int _index) {
		return properties[_index];
	}

	public Property getProperty(String _name) {
		Property mp = null;

		for (int i = 0; i < properties.length; i++)
			if (properties[i].getName().equals(_name)) {
				mp = properties[i];
				break;
			}

		return mp;
	}

	public Property[] getProperties() {
		return properties;
	}

	public int getPropertyCount() {
		return properties.length;
	}

	public String getPropertyName(int _index) {
		return properties[_index].getName();
	}

	public String[] getPropertyNames() {
		String[] propertyNames = new String[properties.length - 1];

		for (int i = 1; i < properties.length; i++)
			propertyNames[i] = properties[i].getName();

		return propertyNames;
	}

	public String toString() {
		String parameterNames = "";

		for (int i = 1; i < properties.length; i++)
			parameterNames += properties[i].getName()
					+ ((i < properties.length - 1) ? ", " : "");

		// return "Tween[name: " + getName() + ", playTime: " + getPlayTime() +
		// ", begin: " + getBegin()
		// + ", end: " + getEnd() + ", duration: " + getDuration()
		// + ", properties[" + parameterNames + "]]";
		return "Tween[name: " + getName() + ", playTime: " + getPlayTime()
				+ ", duration: " + getDuration() + "]";
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
		logger.println("dispatchMotionStartedEvent tween");

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
