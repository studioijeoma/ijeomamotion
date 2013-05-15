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

package ijeoma.motion.tween;

import ijeoma.motion.Callback;
import ijeoma.motion.Motion;
import ijeoma.motion.MotionController;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;

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
		updateCurrentChild();
	}

	private void updateCurrentChild() {
		for (int i = 0; i < children.size(); i++)
			if (children.get(i).isInsidePlayingTime(time)) {
				currentChildIndex = i;
				currentChild = children.get(i);

				break;
			}
	}

	public Sequence play() {
		return (Sequence) super.play();
	}

	public Sequence stop() {
		return (Sequence) super.stop();
	}

	public Sequence pause() {
		return (Sequence) super.pause();
	}

	public Sequence resume() {
		return (Sequence) super.resume();
	}

	public Sequence seek(float _value) {
		updateCurrentChild();
		return (Sequence) super.seek(_value);
	}

	public Sequence delay(float _delay) {
		return (Sequence) super.delay(_delay);
	}

	public Sequence noDelay() {
		return (Sequence) super.noDelay();
	}

	public Sequence repeatDelay() {
		return (Sequence) super.repeatDelay();
	}

	public Sequence noRepeatDelay() {
		return (Sequence) super.noRepeatDelay();
	}

	public Sequence repeat() {
		return (Sequence) super.repeat();
	}

	public Sequence repeat(int _repeat) {
		return (Sequence) super.repeat(_repeat);
	}

	public Sequence noRepeat() {
		return (Sequence) super.noRepeat();
	}

	public Sequence reverse() {
		return (Sequence) super.reverse();
	}

	public Sequence noReverse() {
		return (Sequence) super.noReverse();
	}

	public Sequence setName(String _name) {
		return (Sequence) super.setName(_name);
	}

	public Sequence setTimeScale(float _timeScale) {
		return (Sequence) super.setTimeScale(_timeScale);
	}

	public Sequence setDuration(float _duration) {
		return (Sequence) super.setDuration(_duration);
	}

	public Sequence setEasing(String _easing) {
		return (Sequence) super.setEasing(_easing);
	}

	public Sequence noEasing() {
		return (Sequence) super.noEasing();
	}

	public Sequence setTimeMode(String _timeMode) {
		return (Sequence) super.setTimeMode(_timeMode);
	}

	public Sequence autoUpdate() {
		return (Sequence) super.autoUpdate();
	}

	public Sequence noAutoUpdate() {
		return (Sequence) super.noAutoUpdate();
	}

	public Sequence addEventListener(MotionEventListener listener) {
		return (Sequence) super.addEventListener(listener);
	}

	@Override
	public Sequence add(Motion _child) {
		currentChild = _child;
		return (Sequence) super.insert(_child, duration);
	}

	/**
	 * Removes Motion object
	 */
	public Sequence remove(Motion child) {
		return (Sequence) super.remove(child);
	}

	/**
	 * adds multiple Motion objects
	 */
	public Sequence addAll(Motion[] children) {
		return (Sequence) super.addAll(children);
	}

	/**
	 * Removes all Motion objects
	 */
	public Sequence removeAll() {
		return (Sequence) super.removeAll();
	}

	public Sequence addCall(Callback call) {
		return (Sequence) super.addCall(call);
	}

	public Sequence onBegin(Object object, String method) {
		return (Sequence) super.onBegin(object, method);
	}

	public Sequence onBegin(String method) {
		return (Sequence) super.call(getTween(0).getProperty(0).getObject(),
				method, 0);
	}

	public Sequence onEnd(Object object, String method) {
		return (Sequence) super.onEnd(object, method);
	}

	public Sequence onEnd(String method) {
		return (Sequence) super.call(getTween(0).getProperty(0).getObject(),
				method, duration);
	}

	public Sequence onChange(Object object, String method) {
		return (Sequence) super.onChange(object, method);
	}

	public Sequence onChange(String method) {
		return (Sequence) super.call(getTween(0).getProperty(0).getObject(),
				method, -1);
	}

	/**
	 * returns the current object (either Tween or TweenParallel)
	 */
	public Motion getChild() {
		return currentChild;
	}

	/**
	 * returns the current child type which is either a Tween or TweenParallel
	 */
	public String getChildType() {
		return (currentChild.getClass().getSimpleName());
	}

	/**
	 * returns the current child index;
	 */
	public int getCount() {
		return currentChildIndex;
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
