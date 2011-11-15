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
import ijeoma.motion.MotionSequenceController;
import ijeoma.motion.event.MotionEvent;

import java.lang.reflect.Method;
import java.util.Collections;

import processing.core.PApplet;

public class TweenSequence extends MotionSequenceController {

	private Motion currentChild;
	private Tween currentTween;
	private TweenParallel currentTweenParallel;

	private int currentChildIndex = 0;

	private Method tweenSequenceStartedMethod, tweenSequenceEndedMethod,
			tweenSequenceChangedMethod, tweenSequenceRepeatedMethod;

	public TweenSequence() {
		super();
		setupEvents();
	}

	public TweenSequence(String _name, Motion[] _children) {
		super();
		setName(_name);
		appendChildren(_children);
		setupEvents();
	}

	@Override
	protected void setupEvents() {
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

	// public void reverse() {
	// Collections.sort(children);
	//
	// PApplet.println("NORMAL " + children);
	//
	// float[] playTimes = new float[children.size()];
	//
	// Collections.reverse(children);
	//
	// for (int i = 0; i < children.size(); i++)
	// if (i == 0)
	// playTimes[i] = 0;
	// else
	// playTimes[i] = playTimes[i - 1]
	// + children.get(i - 1).getDuration();
	//
	// for (int i = 0; i < children.size(); i++)
	// children.get(i).setPlayTime(playTimes[i]);
	//
	// PApplet.println("REVERSED " + children);
	// }

	@Override
	public void update() {
		super.update();

		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getClass().getSimpleName().equals("Tween")) {
				Tween t = (Tween) children.get(i);

				if (time >= t.getPlayTime()
						&& time <= (t.getPlayTime() + t.getDuration())) {
					currentChildIndex = i;
					currentChild = t;
					currentTween = t;
				}
			} else {
				TweenParallel tg = (TweenParallel) children.get(i);

				if (time >= tg.getPlayTime()
						&& time <= (tg.getPlayTime() + tg.getDuration())) {
					currentChildIndex = i;
					currentChild = tg;
					currentTweenParallel = tg;
				}
			}
		}
	}

	public void update(float _time) {
		super.update(_time);
		
		if (isPlaying()) {
			setTime(_time);

			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getClass().getSimpleName().equals("Tween")) {
					Tween t = (Tween) children.get(i);

					if (time >= t.getPlayTime()
							&& time <= (t.getPlayTime() + t.getDuration())) {
						currentChildIndex = i;
						currentChild = t;
						currentTween = t;
					}
				} else {
					TweenParallel tg = (TweenParallel) children.get(i);

					if (time >= tg.getPlayTime()
							&& time <= (tg.getPlayTime() + tg.getDuration())) {
						currentChildIndex = i;
						currentChild = tg;
						currentTweenParallel = tg;
					}
				}
			}
		}

	}

	public float getCurrentChildPosition() {
		float position = 0;

		if (currentChild.getClass().getSimpleName().equals("Tween"))
			position = currentChild.getPosition();

		return position;
	}

	public float getCurrentChildPosition(String _parameterName) {
		float position = 0;

		if (currentChild.getClass().getSimpleName().equals("Tween")) {
			position = ((Tween) currentChild).getProperty(_parameterName)
					.getPosition();
		} else {
			position = ((TweenParallel) currentChild).getChild(_parameterName)
					.getPosition();
		}

		return position;
	}

	/**
	 * returns the current object (either Tween or TweenGroup)
	 */
	public Motion getCurrentChild() {
		return currentChild;
	}

	/**
	 * returns the current Tween (useful if you're sequencing Tweens only)
	 */
	public Tween getCurrentTween() {
		return currentTween;
	}

	/**
	 * returns the current TweenGroup (useful if you're sequencing TweenGroups
	 * only)
	 */
	public TweenParallel getCurrentTweenGroup() {
		return currentTweenParallel;
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
		return (currentChild.getClass().getSimpleName().equals("Tween")) ? "Tween"
				: "TweenParallel";
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
		String tweenNames = "";

		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getClass().getSimpleName().equals("Tween"))
				tweenNames += ((Tween) children.get(i)).getName();
			else
				tweenNames += ((TweenParallel) children.get(i)).toString();

			tweenNames += (i < children.size() - 1) ? ", " : "";
		}

		return ("TweenSequence[tweens: {" + tweenNames + "}]");
	}

	public void onMotionEvent(MotionEvent te) {
		// TODO Auto-generated method stub

	}
}
