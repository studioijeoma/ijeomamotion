/**
 * ijeomamotion
 * A cross-mode Processing library for sketching animations with numbers, colors vectors, beziers, curves and more. 
 * http://ekeneijeoma.com/processing/ijeomamotion
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
 * @modified    05/13/2013
 * @version     5.4.1 (54)
 */

package ijeoma.geom.tween;

import ijeoma.geom.Curve;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
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

	public CurveTween play() {
		return (CurveTween) super.play();
	}

	public CurveTween stop() {
		return (CurveTween) super.stop();
	}

	public CurveTween pause() {
		return (CurveTween) super.pause();
	}

	public CurveTween resume() {
		return (CurveTween) super.resume();
	}

	public CurveTween seek(float _value) {
		return (CurveTween) super.seek(_value);
	}

	public CurveTween delay(float _delay) {
		return (CurveTween) super.delay(_delay);
	}

	public CurveTween noDelay() {
		return (CurveTween) super.noDelay();
	}

	public CurveTween repeatDelay() {
		return (CurveTween) super.repeatDelay();
	}

	public CurveTween noRepeatDelay() {
		return (CurveTween) super.noRepeatDelay();
	}

	public CurveTween repeat() {
		return (CurveTween) super.repeat();
	}

	public CurveTween repeat(int _repeat) {
		return (CurveTween) super.repeat(_repeat);
	}

	public CurveTween noRepeat() {
		return (CurveTween) super.noRepeat();
	}

	public CurveTween reverse() {
		return (CurveTween) super.reverse();
	}

	public CurveTween noReverse() {
		return (CurveTween) super.noReverse();
	}

	public CurveTween setName(String _name) {
		return (CurveTween) super.setName(_name);
	}

	public CurveTween setTimeScale(float _timeScale) {
		return (CurveTween) super.setTimeScale(_timeScale);
	}

	public CurveTween setDuration(float _duration) {
		return (CurveTween) super.setDuration(_duration);
	}

	public CurveTween setEasing(String _easing) {
		return (CurveTween) super.setEasing(_easing);
	}

	public CurveTween noEasing() {
		return (CurveTween) super.noEasing();
	}

	public CurveTween setTimeMode(String _timeMode) {
		return (CurveTween) super.setTimeMode(_timeMode);
	}

	public CurveTween autoUpdate() {
		return (CurveTween) super.autoUpdate();
	}

	public CurveTween noAutoUpdate() {
		return (CurveTween) super.noAutoUpdate();
	}

	public CurveTween addEventListener(MotionEventListener listener) {
		return (CurveTween) super.addEventListener(listener);
	}

	public PVector getPoint() {
		return curve.getPointAt(getPosition());
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
