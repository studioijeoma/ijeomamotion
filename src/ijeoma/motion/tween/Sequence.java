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

public class Sequence extends MotionController {

	private Motion currentChild;

	private int currentChildIndex = 0;

	private Method tweenSequenceStartedMethod, tweenSequenceEndedMethod,
			tweenSequenceChangedMethod, tweenSequenceRepeatedMethod;

	public Sequence() {
		super();
		setupEvents();
	}

	public Sequence(Motion[] _children) {
		super();
		addAll(_children);
		setupEvents();
	}

	public Sequence(String _name) {
		super(_name);
		setupEvents();
	}

	public Sequence(String _name, Motion[] _children) {
		super(_name);
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
					new Class[] { Sequence.class });
		} catch (Exception e) {
		}

		try {
			tweenSequenceEndedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_SEQUENCE_ENDED,
					new Class[] { Sequence.class });
		} catch (Exception e) {
		}

		try {
			tweenSequenceChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_SEQUENCE_CHANGED,
					new Class[] { Sequence.class });
		} catch (Exception e) {
		}

		try {
			tweenSequenceRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_SEQUENCE_REPEATED,
					new Class[] { Sequence.class });
		} catch (Exception e) {
		}
	}

	@Override
	public void update() {
		super.update();

		int i = 0;
 
		if (isPlaying)
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
		
		int i = 0; 

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
	public Sequence play() {
		return (Sequence) super.play();
	}

	@Override
	public Sequence stop() {
		return (Sequence) super.stop();
	}

	@Override
	public Sequence pause() {
		return (Sequence) super.pause();
	}

	@Override
	public Sequence resume() {
		return (Sequence) super.resume();
	}

	@Override
	public Sequence seek(float _value) {
		return (Sequence) super.seek(_value);
	}

	@Override
	public Sequence repeat() {
		return (Sequence) super.repeat();
	}

	@Override
	public Sequence repeat(int _repeatDuration) {
		return (Sequence) super.repeat(_repeatDuration);
	}

	@Override
	public Sequence noRepeat() {
		return (Sequence) super.noRepeat();
	}

	@Override
	public Sequence reverse() {
		return (Sequence) super.reverse();
	}

	@Override
	public Sequence noReverse() {
		return (Sequence) super.noReverse();
	}

	@Override
	public Sequence setTimeScale(float _timeScale) {
		return (Sequence) super.setTimeScale(_timeScale);
	}

	@Override
	public Sequence setDuration(float _duration) {
		return (Sequence) super.setDuration(_duration);
	}

	@Override
	public Sequence delay(float _delay) {
		return (Sequence) super.delay(_delay);
	}

	@Override
	public Sequence setEasing(String _easing) {
		return (Sequence) super.setEasing(_easing);
	}

	@Override
	public Sequence noEasing() {
		return (Sequence) super.noEasing();
	}

	@Override
	public Sequence setTimeMode(String _timeMode) {
		return (Sequence) super.setTimeMode(_timeMode);
	}

	@Override
	public Sequence autoUpdate() {
		return (Sequence) super.autoUpdate();
	}

	@Override
	public Sequence noAutoUpdate() {
		return (Sequence) super.noAutoUpdate();
	}

	@Override
	public Sequence add(Motion _child) {
		currentChild = _child;
		return (Sequence) super.insert(_child, duration);
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
