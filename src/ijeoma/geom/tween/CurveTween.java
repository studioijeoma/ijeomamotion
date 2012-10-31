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

import ijeoma.geom.Curve;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.tween.Tween;

import java.lang.reflect.Method;

import processing.core.PApplet;
import processing.core.PVector;

public class CurveTween extends Tween { // implements Comparable {
	private Method tweenCurveStartedMethod, tweenCurveEndedMethod,
			tweenCurveChangedMethod, tweenCurveRepeatedMethod;

	private Curve curve;

	public CurveTween(Curve _curve, float _begin, float _end, float _duration,
			float _delay, String _easing) {
		super(_duration, _delay, _easing);
		curve = _curve;
	}

	public CurveTween(Curve _curve, float _begin, float _end, float _duration,
			float _delay) {
		super(_duration, _delay);
		curve = _curve;
	}

	public CurveTween(Curve _curve, float _begin, float _end, float _duration) {
		super(_duration);
		curve = _curve;
	}

	/**
	 * Sets the events
	 */
	@Override
	protected void setupEvents() {
		super.setupEvents();

		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			tweenCurveStartedMethod = parentClass
					.getMethod(MotionEvent.TWEEN_STARTED,
							new Class[] { CurveTween.class });
		} catch (Exception e) {
		}

		try {
			tweenCurveEndedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_ENDED, new Class[] { CurveTween.class });
		} catch (Exception e) {
		}

		try {
			tweenCurveChangedMethod = parentClass
					.getMethod(MotionEvent.TWEEN_CHANGED,
							new Class[] { CurveTween.class });
		} catch (Exception e) {
		}

		try {
			tweenCurveRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_REPEATED,
					new Class[] { CurveTween.class });
		} catch (Exception e) {
		}
	}

	@Override
	public CurveTween play() {
		return (CurveTween) super.play();
	}

	@Override
	public CurveTween stop() {
		return (CurveTween) super.stop();
	}

	@Override
	public CurveTween pause() {
		return (CurveTween) super.pause();
	}

	@Override
	public CurveTween resume() {
		return (CurveTween) super.resume();
	}

	@Override
	public CurveTween seek(float _value) {
		return (CurveTween) super.seek(_value);
	}

	@Override
	public CurveTween repeat() {
		return (CurveTween) super.repeat();
	}

	@Override
	public CurveTween repeat(int _repeatDuration) {
		return (CurveTween) super.repeat(_repeatDuration);
	}

	@Override
	public CurveTween noRepeat() {
		return (CurveTween) super.noRepeat();
	}

	@Override
	public CurveTween reverse() {
		return (CurveTween) super.reverse();
	}

	@Override
	public CurveTween noReverse() {
		return (CurveTween) super.noReverse();
	}

	@Override
	public CurveTween setTimeScale(float _timeScale) {
		return (CurveTween) super.setTimeScale(_timeScale);
	}

	@Override
	public CurveTween setDuration(float _duration) {
		return (CurveTween) super.setDuration(_duration);
	}

	@Override
	public CurveTween delay(float _delay) {
		return (CurveTween) super.delay(_delay);
	}

	@Override
	public CurveTween setEasing(String _easing) {
		return (CurveTween) super.setEasing(_easing);
	}

	@Override
	public CurveTween noEasing() {
		return (CurveTween) super.noEasing();
	}

	@Override
	public CurveTween setTimeMode(String _timeMode) {
		return (CurveTween) super.setTimeMode(_timeMode);
	}

	@Override
	public CurveTween autoUpdate() {
		return (CurveTween) super.autoUpdate();
	}

	@Override
	public CurveTween noAutoUpdate() {
		return (CurveTween) super.noAutoUpdate();
	}

	public PVector getPoint() {
		return curve.getPoint(getPosition());
	}

	public float getX() {
		return getPoint().x;
	}

	public float getY() {
		return getPoint().y;
	}

	public float getZ() {
		return getPoint().y;
	}

	public void setCurve(Curve _curve) {
		curve = _curve;
	}

	public Curve getCurve() {
		return curve;
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		if (tweenCurveStartedMethod != null) {
			try {
				tweenCurveStartedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenCurveStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_STARTED);
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		if (tweenCurveEndedMethod != null) {
			try {
				tweenCurveEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
				tweenCurveEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_ENDED);
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		if (tweenCurveChangedMethod != null) {
			try {
				tweenCurveChangedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenCurveChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_CHANGED);
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		if (tweenCurveRepeatedMethod != null) {
			try {
				tweenCurveRepeatedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenCurveRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_REPEATED);
	}
}
