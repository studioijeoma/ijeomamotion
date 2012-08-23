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

import processing.core.PApplet;

public class TweenSequence extends MotionController {

	private Motion currentChild;

	private int currentChildIndex = 0;

	private Method tweenSequenceStartedMethod, tweenSequenceEndedMethod,
			tweenSequenceChangedMethod, tweenSequenceRepeatedMethod;

	public TweenSequence() {
		super();
		setupEvents();
	}

	public TweenSequence(Motion[] _children) {
		super();
		addAll(_children);
		setupEvents();
	}

	@Override
	protected void setupEvents() {
		super.setupEvents();

		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			tweenSequenceStartedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_SEQUENCE_STARTED,
					new Class[] { TweenSequence.class });
		} catch (Exception e) {
		}

		try {
			tweenSequenceEndedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_SEQUENCE_ENDED,
					new Class[] { TweenSequence.class });
		} catch (Exception e) {
		}

		try {
			tweenSequenceChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_SEQUENCE_CHANGED,
					new Class[] { TweenSequence.class });
		} catch (Exception e) {
		}

		try {
			tweenSequenceRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_SEQUENCE_REPEATED,
					new Class[] { TweenSequence.class });
		} catch (Exception e) {
		}
	}

	@Override
	public void update() {
		super.update();

		int i = 0;

		// currentChildIndex = 0;
		// currentChild = null;

		for (Motion c : children) {
			if (c.isInsidePlayingTime(time)) {
				currentChildIndex = i;
				currentChild = c;

				break;
			}

			i++;
		}
	}

	@Override
	public void update(float _time) {
		super.update(_time);

		// setTime(_time);

		int i = 0;

		// currentChildIndex = 0;
		// currentChild = null;

		for (Motion c : children) {
			if (c.isInsidePlayingTime(time)) {
				currentChildIndex = i;
				currentChild = c;

				break;
			}

			i++;
		}
	}

	@Override
	public TweenSequence play() {
		return (TweenSequence) super.play();
	}

	@Override
	public TweenSequence stop() {
		return (TweenSequence) super.stop();
	}

	@Override
	public TweenSequence pause() {
		return (TweenSequence) super.pause();
	}

	@Override
	public TweenSequence resume() {
		return (TweenSequence) super.resume();
	}

	@Override
	public TweenSequence seek(float _value) {
		return (TweenSequence) super.seek(_value);
	}

	@Override
	public TweenSequence repeat() {
		return (TweenSequence) super.repeat();
	}

	@Override
	public TweenSequence repeat(int _repeatDuration) {
		return (TweenSequence) super.repeat(_repeatDuration);
	}

	@Override
	public TweenSequence noRepeat() {
		return (TweenSequence) super.noRepeat();
	}

	@Override
	public TweenSequence reverse() {
		return (TweenSequence) super.reverse();
	}

	@Override
	public TweenSequence noReverse() {
		return (TweenSequence) super.noReverse();
	}

	@Override
	public TweenSequence setTimeScale(float _timeScale) {
		return (TweenSequence) super.setTimeScale(_timeScale);
	}

	@Override
	public TweenSequence setDuration(float _duration) {
		return (TweenSequence) super.setDuration(_duration);
	}

	@Override
	public TweenSequence setDelay(float _delay) {
		return (TweenSequence) super.setDelay(_delay);
	}

	@Override
	public TweenSequence setEasing(String _easing) {
		return (TweenSequence) super.setEasing(_easing);
	}

	@Override
	public TweenSequence noEasing() {
		return (TweenSequence) super.noEasing();
	}

	@Override
	public TweenSequence setTimeMode(String _timeMode) {
		return (TweenSequence) super.setTimeMode(_timeMode);
	}

	@Override
	public TweenSequence setRepeatDuration(int _repeatCount) {
		return (TweenSequence) super.setRepeatDuration(_repeatCount);
	}

	@Override
	public TweenSequence autoUpdate() {
		return (TweenSequence) super.autoUpdate();
	}

	@Override
	public TweenSequence noAutoUpdate() {
		return (TweenSequence) super.noAutoUpdate();
	}

	@Override
	public TweenSequence add(Motion _child) {
		return add(_child, null);
	}

	@Override
	public TweenSequence add(Motion _child, String _name) {
		insert(_child, _name, getDuration());

		currentChild = _child;

		return this;
	}

	/**
	 * returns the current object (either Tween or TweenParallel)
	 */
	public Motion getCurrentChild() {
		return currentChild;
	}

	/**
	 * returns the current child index;
	 */
	public int getCurrentChildIndex() {
		return currentChildIndex;
	}

	/**
	 * returns the current child type which is either a Tween or TweenParallel
	 */
	public String getCurrentChildType() {
		return (currentChild.getClass().getSimpleName());
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		if (tweenSequenceStartedMethod != null) {
			try {
				tweenSequenceStartedMethod
						.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenSequenceStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_SEQUENCE_STARTED);
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		if (tweenSequenceEndedMethod != null) {
			try {
				tweenSequenceEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenSequenceEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_SEQUENCE_ENDED);
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		if (tweenSequenceChangedMethod != null) {
			try {
				tweenSequenceChangedMethod
						.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenSequenceChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_SEQUENCE_CHANGED);
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		if (tweenSequenceRepeatedMethod != null) {
			try {
				tweenSequenceRepeatedMethod.invoke(parent,
						new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenSequenceRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_SEQUENCE_REPEATED);
	}

	// protected void dispatchMotionDelayStartedEvent() {
	// if (tweenSequenceDelayStartedMethod != null) {
	// try {
	// tweenSequenceDelayStartedMethod.invoke(parent, new Object[] { this });
	// } catch (Exception e) {
	// // e.printStackTrace();
	// tweenSequenceDelayStartedMethod = null;
	// }
	// }
	//
	// dispatchEvent(MotionEvent.MOTION_DELAY_STARTED);
	// }
	//
	// protected void dispatchMotionDelayEndedEvent() {
	// if (tweenSequenceDelayEndedMethod != null) {
	// try {
	// tweenSequenceDelayEndedMethod.invoke(parent, new Object[] { this });
	// } catch (Exception e) {
	// // e.printStackTrace();
	// tweenSequenceDelayEndedMethod = null;
	// }
	// }
	//
	// dispatchEvent(MotionEvent.MOTION_DELAY_ENDED);
	// }

	@Override
	public String toString() {
		return ("TweenSequence[tweens: {" + tweens + "}]");
	}

	@Override
	public void onMotionEvent(MotionEvent te) {
		// TODO Auto-generated method stub

	}
}
