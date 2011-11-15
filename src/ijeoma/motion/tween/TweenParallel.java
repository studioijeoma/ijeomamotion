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
import ijeoma.motion.MotionParallelController;
import ijeoma.motion.event.MotionEvent;

import java.lang.reflect.Method;
import java.util.Collections;

import processing.core.PApplet;

public class TweenParallel extends MotionParallelController {
	private Method tweenParallelStartedMethod, tweenParallelEndedMethod, tweenParallelChangedMethod,
			tweenParallelRepeatedMethod;

	/**
	 * Constructs a TweenParallel
	 * 
	 * @param parent
	 *            refers to PApplet and is usually 'this'
	 */
	public TweenParallel() {
		super();
	}

	/**
	 * Constructs a TweenParallel
	 * 
	 * @param parent
	 *            refers to PApplet and is usually 'this'
	 * @param name
	 *            is used to find a TweenParallel with in another TweenParallel or
	 *            TweenSequence
	 * @param children
	 *            is an array of type Object[] can contain Tweens and/or
	 *            TweenParallels
	 */
	public TweenParallel(String _name, Motion[] _children) {
		super(_name, _children);
	}

	/**
	 * Sets the events
	 */
	protected void setupEvents() {
		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			tweenParallelStartedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_PARALLEL_STARTED, new Class[] { TweenParallel.class });
		} catch (Exception e) {
		}

		try {
			tweenParallelEndedMethod = parentClass.getMethod(MotionEvent.TWEEN_PARALLEL_ENDED,
					new Class[] { TweenParallel.class });
		} catch (Exception e) {
		}

		try {
			tweenParallelChangedMethod = parentClass.getMethod(MotionEvent.TWEEN_PARALLEL_CHANGED,
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

	@Override
	protected void dispatchMotionStartedEvent() {
		logger.println("dispatchMotionStartedEvent tweengroup");

		if (tweenParallelStartedMethod != null) {
			try {
				tweenParallelStartedMethod.invoke(parent, new Object[] { this });
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
				tweenParallelChangedMethod.invoke(parent, new Object[] { this });
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
				tweenParallelRepeatedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenParallelRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_PARALLEL_REPEATED);
	}

	@Override
	public String toString() {
		String tweenNames = "";

		for (int i = 0; i < children.size(); i++) {
			Object child = children.get(i);

			if (child.getClass().getSimpleName().equals("Tween"))
				tweenNames += ((Motion) child).getName() + ((i < children.size() - 1) ? ", " : "");
			else
				tweenNames += ((TweenParallel) child).toString() + ((i < children.size() - 1) ? ", " : "");
		}

		return ("TweenParallel[tweens: {" + tweenNames + "}]");
	}

	public void onMotionEvent(MotionEvent te) {
		// TODO Auto-generated method stub
	}
}
