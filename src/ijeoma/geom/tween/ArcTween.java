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

package ijeoma.geom.tween;

import ijeoma.geom.Bezier;
import ijeoma.motion.MotionConstant;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.Tween;

import java.lang.reflect.Method;

import processing.core.PApplet;
import processing.core.PVector;

public class ArcTween extends Tween { // implements Comparable {
	private Method tweenBezierStartedMethod, tweenBezierEndedMethod,
			tweenBezierChangedMethod, tweenBezierRepeatedMethod;

	public ArcTween(PVector _begin, PVector _end, float _duration,
			float _delay, String _easing) {
		super(_duration, _delay, _easing);
	}

	public ArcTween(PVector _begin, PVector _end, float _duration, float _delay) {
		super(_duration, _delay);
	}

	public ArcTween(PVector _begin, PVector _end, float _duration) {
		super(_duration);
	}

	/**
	 * Sets the events
	 */
	@Override
	protected void setupEvents() {
		super.setupEvents();

		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			tweenBezierStartedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_STARTED, new Class[] { ArcTween.class });
		} catch (Exception e) {
		}

		try {
			tweenBezierEndedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_ENDED, new Class[] { ArcTween.class });
		} catch (Exception e) {
		}

		try {
			tweenBezierChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_CHANGED, new Class[] { ArcTween.class });
		} catch (Exception e) {
		}

		try {
			tweenBezierRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_REPEATED, new Class[] { ArcTween.class });
		} catch (Exception e) {
		}
	}

	@Override
	public ArcTween play() {
		return (ArcTween) super.play();
	}

	@Override
	public ArcTween stop() {
		return (ArcTween) super.stop();
	}

	@Override
	public ArcTween pause() {
		return (ArcTween) super.pause();
	}

	@Override
	public ArcTween resume() {
		return (ArcTween) super.resume();
	}

	@Override
	public ArcTween seek(float _value) {
		return (ArcTween) super.seek(_value);
	}

	public ArcTween setName(String _name) {
		return (ArcTween) super.setName(_name);
	}

	public ArcTween setTimeScale(float _timeScale) {
		return (ArcTween) super.setTimeScale(_timeScale);
	}

	public ArcTween setDuration(float _duration) {
		return (ArcTween) super.setDuration(_duration);
	}

	public ArcTween delay(float _delay) {
		return (ArcTween) super.delay(_delay);
	}

	public ArcTween noDelay() {
		return (ArcTween) super.noDelay();
	}

	public ArcTween repeatDelay() {
		return (ArcTween) super.repeatDelay();
	}

	public ArcTween noRepeatDelay() {
		return (ArcTween) super.noRepeatDelay();
	}

	public ArcTween setEasing(String _easing) {
		return (ArcTween) super.setEasing(_easing);
	}

	public ArcTween noEasing() {
		return (ArcTween) super.setEasing(MotionConstant.LINEAR_BOTH);
	}

	public ArcTween setTimeMode(String _timeMode) {
		return (ArcTween) super.setTimeMode(_timeMode);
	}

	public ArcTween autoUpdate() {
		return (ArcTween) super.autoUpdate();
	}

	public ArcTween noAutoUpdate() {
		return (ArcTween) super.noAutoUpdate();
	}

	public ArcTween addEventListener(MotionEventListener listener) {
		return (ArcTween) super.addEventListener(listener);
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		if (tweenBezierStartedMethod != null) {
			try {
				tweenBezierStartedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenBezierStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_STARTED);
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		if (tweenBezierEndedMethod != null) {
			try {
				tweenBezierEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
				tweenBezierEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_ENDED);
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		if (tweenBezierChangedMethod != null) {
			try {
				tweenBezierChangedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenBezierChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_CHANGED);
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		if (tweenBezierRepeatedMethod != null) {
			try {
				tweenBezierRepeatedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenBezierRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_REPEATED);
	}
}
