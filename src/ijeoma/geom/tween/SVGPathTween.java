package ijeoma.geom.tween;

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

import ijeoma.geom.SVGPath;
import ijeoma.motion.MotionConstant;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.Tween;

import java.lang.reflect.Method;

import processing.core.PApplet;
import processing.core.PVector;

public class SVGPathTween extends Tween { // implements Comparable {
	private Method tweenSVGPathStartedMethod, tweenSVGPathEndedMethod,
			tweenSVGPathChangedMethod, tweenSVGPathRepeatedMethod;

	private SVGPath path;

	public SVGPathTween(SVGPath _path, float _begin, float _end,
			float _duration, float _delay, String _easing) {
		super(_duration, _delay, _easing);
		path = _path;
	}

	public SVGPathTween(SVGPath _path, float _begin, float _end,
			float _duration, float _delay) {
		super(_duration, _delay);
		path = _path;
	}

	public SVGPathTween(SVGPath _path, float _begin, float _end, float _duration) {
		super(_duration);
		path = _path;
	}

	/**
	 * Sets the events
	 */
	@Override
	protected void setupEvents() {
		super.setupEvents();

		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			tweenSVGPathStartedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_STARTED,
					new Class[] { SVGPathTween.class });
		} catch (Exception e) {
		}

		try {
			tweenSVGPathEndedMethod = parentClass
					.getMethod(MotionEvent.TWEEN_ENDED,
							new Class[] { SVGPathTween.class });
		} catch (Exception e) {
		}

		try {
			tweenSVGPathChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_CHANGED,
					new Class[] { SVGPathTween.class });
		} catch (Exception e) {
		}

		try {
			tweenSVGPathRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_REPEATED,
					new Class[] { SVGPathTween.class });
		} catch (Exception e) {
		}
	}

	@Override
	public SVGPathTween play() {
		return (SVGPathTween) super.play();
	}

	@Override
	public SVGPathTween stop() {
		return (SVGPathTween) super.stop();
	}

	@Override
	public SVGPathTween pause() {
		return (SVGPathTween) super.pause();
	}

	@Override
	public SVGPathTween resume() {
		return (SVGPathTween) super.resume();
	}

	@Override
	public SVGPathTween seek(float _value) {
		return (SVGPathTween) super.seek(_value);
	}

	public SVGPathTween setName(String _name) {
		return (SVGPathTween) super.setName(_name);
	}

	public SVGPathTween setTimeScale(float _timeScale) {
		return (SVGPathTween) super.setTimeScale(_timeScale);
	}

	public SVGPathTween setDuration(float _duration) {
		return (SVGPathTween) super.setDuration(_duration);
	}

	public SVGPathTween delay(float _delay) {
		return (SVGPathTween) super.delay(_delay);
	}

	public SVGPathTween noDelay() {
		return (SVGPathTween) super.noDelay();
	}

	public SVGPathTween repeatDelay() {
		return (SVGPathTween) super.repeatDelay();
	}

	public SVGPathTween noRepeatDelay() {
		return (SVGPathTween) super.noRepeatDelay();
	}

	public SVGPathTween setEasing(String _easing) {
		return (SVGPathTween) super.setEasing(_easing);
	}

	public SVGPathTween noEasing() {
		return (SVGPathTween) super.setEasing(MotionConstant.LINEAR_BOTH);
	}

	public SVGPathTween setTimeMode(String _timeMode) {
		return (SVGPathTween) super.setTimeMode(_timeMode);
	}

	public SVGPathTween autoUpdate() {
		return (SVGPathTween) super.autoUpdate();
	}

	public SVGPathTween noAutoUpdate() {
		return (SVGPathTween) super.noAutoUpdate();
	}

	public SVGPathTween addEventListener(MotionEventListener listener) {
		return (SVGPathTween) super.addEventListener(listener);
	}

	public PVector getPoint() {
		return path.getPoint(getPosition());
	}

	public float getX() {
		return getPoint().x;
	}

	public float getY() {
		return getPoint().y;
	}

	public void setSVGPath(SVGPath _path) {
		path = _path;
	}

	public SVGPath getSVGPath() {
		return path;
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		if (tweenSVGPathStartedMethod != null) {
			try {
				tweenSVGPathStartedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenSVGPathStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_STARTED);
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		if (tweenSVGPathEndedMethod != null) {
			try {
				tweenSVGPathEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
				tweenSVGPathEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_ENDED);
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		if (tweenSVGPathChangedMethod != null) {
			try {
				tweenSVGPathChangedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenSVGPathChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_CHANGED);
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		if (tweenSVGPathRepeatedMethod != null) {
			try {
				tweenSVGPathRepeatedMethod
						.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenSVGPathRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_REPEATED);
	}
}
