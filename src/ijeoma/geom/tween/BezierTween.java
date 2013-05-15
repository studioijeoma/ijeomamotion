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

import ijeoma.geom.Bezier;
import ijeoma.motion.MotionConstant;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.Tween;

import java.lang.reflect.Method;

import processing.core.PApplet;
import processing.core.PVector;

public class BezierTween extends Tween { // implements Comparable {
	private Method tweenBezierStartedMethod, tweenBezierEndedMethod,
			tweenBezierChangedMethod, tweenBezierRepeatedMethod;

	private Bezier bezier;

	public BezierTween(Bezier _bezier, float _begin, float _end,
			float _duration, float _delay, String _easing) {
		super(_duration, _delay, _easing);
		bezier = _bezier;
	}

	public BezierTween(Bezier _bezier, float _begin, float _end,
			float _duration, float _delay) {
		super(_duration, _delay);
		bezier = _bezier;
	}

	public BezierTween(Bezier _bezier, float _begin, float _end, float _duration) {
		super(_duration);
		bezier = _bezier;
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
					MotionEvent.TWEEN_STARTED,
					new Class[] { BezierTween.class });
		} catch (Exception e) {
		}

		try {
			tweenBezierEndedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_ENDED, new Class[] { BezierTween.class });
		} catch (Exception e) {
		}

		try {
			tweenBezierChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_CHANGED,
					new Class[] { BezierTween.class });
		} catch (Exception e) {
		}

		try {
			tweenBezierRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_REPEATED,
					new Class[] { BezierTween.class });
		} catch (Exception e) {
		}
	}

	@Override
	public BezierTween play() { 
		return (BezierTween) super.play();
	}

	@Override
	public BezierTween stop() {
		return (BezierTween) super.stop();
	}

	@Override
	public BezierTween pause() {
		return (BezierTween) super.pause();
	}

	@Override
	public BezierTween resume() {
		return (BezierTween) super.resume();
	}

	@Override
	public BezierTween seek(float _value) {
		return (BezierTween) super.seek(_value);
	}

	public BezierTween setName(String _name) {
		return (BezierTween) super.setName(_name);
	}

	public BezierTween setTimeScale(float _timeScale) {
		return (BezierTween) super.setTimeScale(_timeScale);
	}

	public BezierTween setDuration(float _duration) {
		return (BezierTween) super.setDuration(_duration);
	}

	public BezierTween delay(float _delay) {
		return (BezierTween) super.delay(_delay);
	}

	public BezierTween noDelay() {
		return (BezierTween) super.noDelay();
	}

	public BezierTween repeatDelay() {
		return (BezierTween) super.repeatDelay();
	}

	public BezierTween noRepeatDelay() {
		return (BezierTween) super.noRepeatDelay();
	}

	public BezierTween setEasing(String _easing) {
		return (BezierTween) super.setEasing(_easing);
	}

	public BezierTween noEasing() {
		return (BezierTween) super.setEasing(MotionConstant.LINEAR_BOTH);
	}

	public BezierTween setTimeMode(String _timeMode) {
		return (BezierTween) super.setTimeMode(_timeMode);
	}

	public BezierTween autoUpdate() {
		return (BezierTween) super.autoUpdate();
	}

	public BezierTween noAutoUpdate() {
		return (BezierTween) super.noAutoUpdate();
	}

	public BezierTween addEventListener(MotionEventListener listener) {
		return (BezierTween) super.addEventListener(listener);
	}
	
	public PVector getPoint() {
		return bezier.getPointAt(getPosition());
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

	public void setBezier(Bezier _bezier) {
		bezier = _bezier;
	}

	public Bezier getBezier() {
		return bezier;
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
