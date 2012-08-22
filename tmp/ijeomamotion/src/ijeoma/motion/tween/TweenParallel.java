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
import ijeoma.motion.MotionController; 
import ijeoma.motion.event.MotionEvent;

import java.lang.reflect.Method;
import java.util.Collections;

import processing.core.PApplet;

public class TweenParallel extends MotionController {
	private Method tweenParallelStartedMethod, tweenParallelEndedMethod,
			tweenParallelChangedMethod, tweenParallelRepeatedMethod;

	/**
	 * Constructs a TweenParallel
	 * 
	 * @param g
	 *            refers to PApplet and is usually 'this'
	 */
	public TweenParallel() {
		super();
		setupEvents();
	}

	/**
	 * Constructs a TweenParallel
	 * 
	 * @param g
	 *            refers to PApplet and is usually 'this'
	 * @param name
	 *            is used to find a TweenParallel with in another TweenParallel
	 *            or TweenSequence
	 * @param children
	 *            is an array of type Object[] can contain Tweens and/or
	 *            TweenParallels
	 */
	public TweenParallel(Motion[] _children) {
		super();
		addAll(_children);
		setupEvents();
	} 

	/**
	 * Sets the events
	 */
	protected void setupEvents() {
		super.setupEvents();

		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			tweenParallelStartedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_PARALLEL_STARTED,
					new Class[] { TweenParallel.class });
		} catch (Exception e) {
		}

		try {
			tweenParallelEndedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_PARALLEL_ENDED,
					new Class[] { TweenParallel.class });
		} catch (Exception e) {
		}

		try {
			tweenParallelChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_PARALLEL_CHANGED,
					new Class[] { TweenParallel.class });
		} catch (Exception e) {
		}

		try {
			tweenParallelRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_PARALLEL_REPEATED,
					new Class[] { TweenParallel.class });
		} catch (Exception e) {
		}
	}
	
	public TweenParallel play() {
		return (TweenParallel) super.play();
	}

	public TweenParallel stop() {
		return (TweenParallel) super.stop();
	}

	public TweenParallel pause() {
		return (TweenParallel) super.pause();
	}

	public TweenParallel resume() {
		return (TweenParallel) super.resume();
	}

	public TweenParallel seek(float _value) {
		return (TweenParallel) super.seek(_value);  
	}

	public TweenParallel repeat() {
		return (TweenParallel) super.repeat();
	}

	public TweenParallel repeat(int _repeatDuration) {
		return (TweenParallel) super.repeat(_repeatDuration);
	}

	public TweenParallel noRepeat() {
		return (TweenParallel) super.noRepeat();
	}

	public TweenParallel reverse() {
		return (TweenParallel) super.reverse();
	}

	public TweenParallel noReverse() {
		return (TweenParallel) super.noReverse();
	}

	public TweenParallel setTimeScale(float _timeScale) {
		return (TweenParallel) super.setTimeScale(_timeScale);
	}

	public TweenParallel setDuration(float _duration) {
		return (TweenParallel) super.setDuration(_duration);
	}

	public TweenParallel setDelay(float _delay) {
		return (TweenParallel) super.setDelay(_delay);
	}

	public TweenParallel setEasing(String _easing) {
		return (TweenParallel) super.setEasing(_easing);
	}

	public TweenParallel noEasing() {
		return (TweenParallel) super.noEasing();
	}

	public TweenParallel setTimeMode(String _timeMode) {
		return (TweenParallel) super.setTimeMode(_timeMode);
	}

	public TweenParallel setRepeatDuration(int _repeatCount) {
		return (TweenParallel) super.setRepeatDuration(_repeatCount);
	}

	public TweenParallel autoUpdate() {
		return (TweenParallel) super.autoUpdate();
	}

	public TweenParallel noAutoUpdate() {
		return (TweenParallel) super.noAutoUpdate();
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

	public void onMotionEvent(MotionEvent te) {
		// TODO Auto-generated method stub
	}
}
