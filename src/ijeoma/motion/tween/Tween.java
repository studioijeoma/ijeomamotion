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

import ijeoma.geom.Path;
import ijeoma.motion.Callback;
import ijeoma.motion.Motion;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Tween extends Motion { // implements Comparable
	protected ArrayList<IProperty> properties = new ArrayList<IProperty>();
	protected HashMap<String, IProperty> propertyMap = new HashMap<String, IProperty>();

	protected Method tweenStartedMethod, tweenEndedMethod, tweenChangedMethod,
			tweenRepeatedMethod;

	public Tween() {
		super();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param name
	 * @param duration
	 *            how many seconds/frames you want the tween to take from begin
	 *            to the end
	 * @param delay
	 *            how many seconds/frames you want the tween to delay before
	 *            starting
	 * @param easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Tween(String name, float duration, float delay, String easing) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	public Tween(String name, float duration, float delay) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	public Tween(String name, float duration) {
		setup(name, duration, delay, easing);
		setupEvents();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param duration
	 *            how many seconds/frames you want the tween to take from begin
	 *            to the end
	 * @param delay
	 *            how many seconds/frames you want the tween to delay before
	 *            starting
	 * @param easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Tween(float duration, float delay, String easing) {
		setup("", duration, delay, easing);
		setupEvents();
	}

	public Tween(float duration, float delay) {
		setup(duration, delay, easing);
		setupEvents();
	}

	public Tween(float duration) {
		setup(duration, delay, easing);
		setupEvents();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param name
	 *            the id of the tween
	 * @param propertyObject
	 *            the object you want to tween
	 * @param propertyName
	 *            the parameter of the object you want to tween
	 * @param end
	 *            the position you want the tween to end at
	 * @param duration
	 *            how many seconds/frames you want the tween to take from begin
	 *            to the end
	 * @param delay
	 *            how many seconds/frames you want the tween to delay before
	 *            starting
	 * @param easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Tween(String name, Object propertyObject, String propertyName,
			float end, float duration, float delay, String easing) {
		IProperty p = new NumberProperty(propertyObject, propertyName, end);

		setup(name, p, duration, delay, easing);
		setupEvents();
	}

	public Tween(String name, Object proeprtyObject, String propertyName,
			float end, float duration, float delay) {
		IProperty p = new NumberProperty(proeprtyObject, propertyName, end);

		setup(name, p, duration, delay, easing);
		setupEvents();
	}

	public Tween(String name, Object propertyObject, String propertyName,
			float end, float duration) {
		IProperty p = new NumberProperty(propertyObject, propertyName, end);

		setup(name, p, duration, delay, easing);
		setupEvents();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param propertyObject
	 *            the object you want to tween
	 * @param propertyName
	 *            the parameter of the object you want to tween
	 * @param end
	 *            the position you want the tween to end at
	 * @param duration
	 *            how many seconds/frames you want the tween to take from begin
	 *            to the end
	 * @param delay
	 *            how many seconds/frames you want the tween to delay before
	 *            starting
	 * @param easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Tween(Object propertyObject, String propertyName, float end,
			float duration, float delay, String easing) {
		IProperty p = new NumberProperty(propertyObject, propertyName, end);

		setup(p, duration, delay, easing);
		setupEvents();
	}

	public Tween(Object propertyObject, String propertyName, float end,
			float duration, float delay) {
		IProperty p = new NumberProperty(propertyObject, propertyName, end);

		setup(p, duration, delay, easing);
		setupEvents();
	}

	public Tween(Object propertyObject, String propertyName, float end,
			float duration) {
		IProperty p = new NumberProperty(propertyObject, propertyName, end);

		setup(p, duration, delay, easing);
		setupEvents();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param name
	 *            the position you want the tween to end at
	 * @param begin
	 *            the position you want the tween to end at
	 * @param end
	 *            the position you want the tween to end at
	 * @param duration
	 *            how many seconds/frames you want the tween to take from begin
	 *            to the end
	 * @param delay
	 *            how many seconds/frames you want the tween to delay before
	 *            starting
	 * @param easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Tween(String name, float begin, float end, float duration,
			float delay, String easing) {
		IProperty p = new NumberProperty(name, begin, end);

		setup(p, duration, delay, easing);
		setupEvents();
	}

	public Tween(String name, float begin, float end, float duration,
			float delay) {
		IProperty p = new NumberProperty(name, begin, end);

		setup(p, duration, delay, easing);
		setupEvents();
	}

	public Tween(String name, float begin, float end, float duration) {
		IProperty p = new NumberProperty(name, begin, end);

		setup(p, duration, delay, easing);
		setupEvents();
	}

	/**
	 * Constructs a Tween
	 * 
	 * @param name
	 *            the position you want the tween to end at
	 * @param begin
	 *            the position you want the tween to end at
	 * @param end
	 *            the position you want the tween to end at
	 * @param duration
	 *            how many seconds/frames you want the tween to take from begin
	 *            to the end
	 * @param delay
	 *            how many seconds/frames you want the tween to delay before
	 *            starting
	 * @param easing
	 *            LINEAR_IN, LINEAR_OUT, LINEAR_BOTH, QUAD_IN, QUAD_OUT,
	 *            QUAD_BOTH, CUBIC_IN, CUBIC_BOTH, CUBIC_OUT, QUART_IN,
	 *            QUART_OUT, QUART_BOTH, QUINT_IN, QUINT_OUT, QUINT_BOTH,
	 *            SINE_IN, SINE_OUT,SINE_BOTH, CIRC_IN, CIRC_OUT, CIRC_BOTH,
	 *            EXPO_IN, EXPO_OUT, EXPO_BOTH, BACK_IN, BACK_OUT, BACK_BOTH,
	 *            BOUNCE_IN, BOUNCE_OUT, BOUNCE_BOTH, ELASTIC_IN, ELASTIC_OUT,
	 *            ELASTIC_BOTH
	 */
	public Tween(float begin, float end, float duration, float delay,
			String easing) {
		IProperty p = new NumberProperty("", begin, end);

		setup(p, duration, delay, easing);
		setupEvents();
	}

	public Tween(float begin, float end, float duration, float delay) {
		IProperty p = new NumberProperty("", begin, end);

		setup(p, duration, delay, easing);
		setupEvents();
	}

	public Tween(float begin, float end, float duration) {
		IProperty p = new NumberProperty("", begin, end);

		setup(p, duration, delay, easing);
		setupEvents();
	}

	protected void setup(String name, IProperty p, float duration, float delay,
			String easing) {
		setup(name, duration, delay, easing);
		add(p);
	}

	protected void setup(IProperty p, float duration, float delay, String easing) {
		setup(duration, delay, easing);
		add(p);
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
		super.update();

		// if (isPlaying)
		updateProperties();
	}

	@Override
	public void update(float time) {
		super.update(time);

		if (isPlaying)
			updateProperties();
	}

	public void updateProperties() {
		try {
			for (IProperty p : properties) {
				Object[] args = { getPosition(), 0f, 1f, 1f };
				float position = ((Float) easingMethod.invoke(parent, args))
						.floatValue();
				p.setPosition(position);
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
	public Tween seek(float value) {
		// super.seek(value);

		playTime = (delay + duration) * value;

		if (playTime != time) {
			setTime(playTime);
			updateCalls();
			updateProperties();
		}

		return this;
	}

	public Tween delay(float delay) {
		return (Tween) super.delay(delay);
	}

	public Tween noDelay() {
		return (Tween) super.noDelay();
	}

	public Tween repeatDelay() {
		return (Tween) super.repeatDelay();
	}

	public Tween noRepeatDelay() {
		return (Tween) super.noRepeatDelay();
	}

	public Tween repeat() {
		return (Tween) super.repeat();
	}

	public Tween repeat(int count) {
		return (Tween) super.repeat(count);
	}

	public Tween noRepeat() {
		return (Tween) super.noRepeat();
	}

	public Tween reverse() {
		return (Tween) super.reverse();
	}

	public Tween noReverse() {
		return (Tween) super.noReverse();
	}

	public Tween setName(String name) {
		return (Tween) super.setName(name);
	}

	public Tween setTimeScale(float timeScale) {
		return (Tween) super.setTimeScale(timeScale);
	}

	public Tween setDuration(float duration) {
		return (Tween) super.setDuration(duration);
	}

	public Tween setEasing(String easing) {
		return (Tween) super.setEasing(easing);
	}

	public Tween noEasing() {
		return (Tween) super.noEasing();
	}

	public Tween setTimeMode(String timeMode) {
		return (Tween) super.setTimeMode(timeMode);
	}

	public Tween autoUpdate() {
		return (Tween) super.autoUpdate();
	}

	public Tween noAutoUpdate() {
		return (Tween) super.noAutoUpdate();
	}

	public Tween addEventListener(MotionEventListener listener) {
		return (Tween) super.addEventListener(listener);
	}

	public Tween add(IProperty p) {
		if (properties.size() == 0 && name == "")
			name = p.getName();

		properties.add(p);

		if (!p.getName().equals(""))
			propertyMap.put(p.getName(), p);

		return this;
	}

	public Tween add(String name, float begin, float end) {
		return add(new NumberProperty(name, begin, end));
	}

	public Tween addNumber(String name, float begin, float end) {
		return add(new NumberProperty(name, begin, end));
	}

	public Tween addColor(String name, int begin, int end) {
		return add(new ColorProperty(name, begin, end));
	}

	public Tween addVector(String name, PVector begin, PVector end) {
		return add(new VectorProperty(name, begin, end));
	}

	public Tween add(Object object, String name, float end) {
		return add(new NumberProperty(object, name, end));
	}

	public Tween addNumber(Object object, String name, float end) {
		return add(new NumberProperty(object, name, end));
	}

	public Tween addColor(Object object, String name, int end) {
		return add(new ColorProperty(object, name, end));
	}

	public Tween addVector(Object object, String name, PVector end) {
		return add(new VectorProperty(object, name, end));
	}

	public Tween addPath(Path path, float end) {
		// return add(new PathProperty(vector, end));
		return add(new NumberProperty(path, "t", 0f, end));
	}

	public Tween removeAll() {
		properties.clear();
		propertyMap.clear();

		removeCalls();

		return this;
	}

	public Tween removeCalls() {
		calls.clear();
		callMap.clear();

		return this;
	}

	public Tween call(Object object, String method, float time) {
		return (Tween) super.call(object, method, time);
	}

	public Tween call(String method, float time) {
		return (Tween) super.call(properties.get(0).getObject(), method, time);
	}

	public Tween onBegin(Object object, String method) {
		return (Tween) super.onBegin(object, method);
	}

	public Tween onBegin(String method) {
		return (Tween) super.call(properties.get(0).getObject(), method, 0);
	}

	public Tween onEnd(Object object, String method) {
		return (Tween) super.onEnd(object, method);
	}

	public Tween onEnd(String method) {
		return (Tween) super.call(properties.get(0).getObject(), method,
				duration);
	}

	public Tween onChange(Object object, String method) {
		return (Tween) super.onChange(object, method);
	}

	public Tween onChange(String method) {
		return (Tween) super.call(properties.get(0).getObject(), method, -1);
	}

	public Tween addCall(Callback callback) {
		return (Tween) super.addCall(callback);
	}

	public NumberProperty get(int index) {
		return (NumberProperty) getProperty(index);
	}

	public NumberProperty get(String name) {
		return (NumberProperty) getProperty(name);
	}

	public NumberProperty getNumber(int index) {
		return (NumberProperty) getProperty(index);
	}

	public NumberProperty getNumber(String name) {
		return (NumberProperty) getProperty(name);
	}

	public ColorProperty getColor(int index) {
		return (ColorProperty) getProperty(index);
	}

	public ColorProperty getColor(String name) {
		return (ColorProperty) getProperty(name);
	}

	public VectorProperty getVector(int index) {
		return (VectorProperty) getProperty(index);
	}

	public VectorProperty getVector(String name) {
		return (VectorProperty) getProperty(name);
	}

	public IProperty getProperty(int index) {
		return properties.get(index);
	}

	public IProperty getProperty(String name) {
		return propertyMap.get(name);
	}

	public IProperty[] getProperties() {
		return properties.toArray(new IProperty[properties.size()]);
	}

	public int getPropertyCount() {
		return properties.size();
	}

	public String getPropertyName(int index) {
		return properties.get(index).getName();
	}

	public String[] getPropertyNames() {
		String[] propertyNames = new String[properties.size()];

		for (int i = 0; i < properties.size(); i++)
			propertyNames[i] = properties.get(i + 1).getName();

		return propertyNames;
	}

	public List<String> getPropertyNamesList() {
		ArrayList<String> propertyNamesList = new ArrayList<String>();

		for (IProperty p : properties)
			propertyNamesList.add(p.getName());

		return propertyNamesList;
	}

	@Override
	public String toString() {
		return "Tween[" + (name.equals("") ? "" : "name: " + name + " ")
				+ "time: " + delay + ", duration: " + getDuration() + "]";
	}

	@Override
	protected synchronized void dispatchEvent(String eventType) {
		MotionEvent te = new MotionEvent(this, eventType);

		Iterator<MotionEventListener> ls = listeners.iterator();

		while (ls.hasNext())
			ls.next().onMotionEvent(te);
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		if (tweenStartedMethod != null) {
			try {
				tweenStartedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
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
				tweenRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_REPEATED);
	}
}
