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
import ijeoma.motion.event.MotionEvent;

import java.lang.reflect.Method;

import processing.core.PApplet;

public class Parallel extends MotionController {
	private Method tweenParallelStartedMethod, tweenParallelEndedMethod,
			tweenParallelChangedMethod, tweenParallelRepeatedMethod;

	/**
	 * Constructs a TweenParallel 
	 */
	public Parallel() {
		super();
		setupEvents();
	}

	/**
	 * Constructs a TweenParallel
	 *  
	 * @param children
	 *            is an array of type Object[] can contain Tweens and/or
	 *            TweenParallels
	 */
	public Parallel(Motion[] _children) {
		super();
		addAll(_children);
		setupEvents();
	}

	/**
	 * Constructs a TweenParallel
	 * 
	 * @param name
	 *            is used to find a TweenParallel with in another TweenParallel
	 *            or TweenSequence
	 */
	public Parallel(String _name) {
		super(_name);
		setupEvents();
	}

	/**
	 * Constructs a TweenParallel
	 *  
	 * @param name
	 *            is used to find a TweenParallel with in another TweenParallel
	 *            or TweenSequence
	 * @param children
	 *            is an array of type Object[] can contain Tweens and/or
	 *            TweenParallels
	 */
	public Parallel(String _name, Motion[] _children) {
		super(_name);
		addAll(_children);
		setupEvents();
	}

	/**
	 * Sets the events
	 */
	@Override
	protected void setupEvents() {
		super.setupEvents();

		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			tweenParallelStartedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_PARALLEL_STARTED,
					new Class[] { Parallel.class });
		} catch (Exception e) {
		}

		try {
			tweenParallelEndedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_PARALLEL_ENDED,
					new Class[] { Parallel.class });
		} catch (Exception e) {
		}

		try {
			tweenParallelChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_PARALLEL_CHANGED,
					new Class[] { Parallel.class });
		} catch (Exception e) {
		}

		try {
			tweenParallelRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_PARALLEL_REPEATED,
					new Class[] { Parallel.class });
		} catch (Exception e) {
		}
	}

	@Override
	public Parallel play() {
		return (Parallel) super.play();
	}

	@Override
	public Parallel stop() {
		return (Parallel) super.stop();
	}

	@Override
	public Parallel pause() {
		return (Parallel) super.pause();
	}

	@Override
	public Parallel resume() {
		return (Parallel) super.resume();
	}

	@Override
	public Parallel seek(float _value) {
		return (Parallel) super.seek(_value);
	}

	@Override
	public Parallel repeat() {
		return (Parallel) super.repeat();
	}

	@Override
	public Parallel repeat(int _repeatDuration) {
		return (Parallel) super.repeat(_repeatDuration);
	}

	@Override
	public Parallel noRepeat() {
		return (Parallel) super.noRepeat();
	}

	@Override
	public Parallel reverse() {
		return (Parallel) super.reverse();
	}

	@Override
	public Parallel noReverse() {
		return (Parallel) super.noReverse();
	}

	@Override
	public Parallel setTimeScale(float _timeScale) {
		return (Parallel) super.setTimeScale(_timeScale);
	}

	@Override
	public Parallel setDuration(float _duration) {
		return (Parallel) super.setDuration(_duration);
	}

	@Override
	public Parallel setDelay(float _delay) {
		return (Parallel) super.setDelay(_delay);
	}

	@Override
	public Parallel setEasing(String _easing) {
		return (Parallel) super.setEasing(_easing);
	}

	@Override
	public Parallel noEasing() {
		return (Parallel) super.noEasing();
	}

	@Override
	public Parallel setTimeMode(String _timeMode) {
		return (Parallel) super.setTimeMode(_timeMode);
	}

	@Override
	public Parallel setRepeatDuration(int _repeatCount) {
		return (Parallel) super.setRepeatDuration(_repeatCount);
	}

	@Override
	public Parallel autoUpdate() {
		return (Parallel) super.autoUpdate();
	}

	@Override
	public Parallel noAutoUpdate() {
		return (Parallel) super.noAutoUpdate();
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		// logger.println("dispatchMotionStartedEvent tweengroup");

		if (tweenParallelStartedMethod != null) {
			try {
				tweenParallelStartedMethod
						.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenParallelStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_PARALLEL_STARTED);
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		if (tweenParallelEndedMethod != null) {
			try {
				tweenParallelEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
				tweenParallelEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_PARALLEL_ENDED);
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		if (tweenParallelChangedMethod != null) {
			try {
				tweenParallelChangedMethod
						.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenParallelChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_PARALLEL_CHANGED);
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		if (tweenParallelRepeatedMethod != null) {
			try {
				tweenParallelRepeatedMethod.invoke(parent,
						new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenParallelRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_PARALLEL_REPEATED);
	}

	@Override
	public String toString() {
		return ("TweenParallel[tweens: {" + tweens + "}]");
	}

	@Override
	public void onMotionEvent(MotionEvent te) {
		// TODO Auto-generated method stub
	}
}
