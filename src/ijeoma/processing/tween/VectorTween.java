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

package ijeoma.processing.tween;

import java.lang.reflect.Method;

import ijeoma.motion.Motion;
import ijeoma.motion.Property;
import ijeoma.motion.event.MotionEvent;

import processing.core.PApplet;
import processing.core.PVector;

public class VectorTween extends Motion { // implements Comparable {
	private Method tweenPVectorStartedMethod, tweenPVectorEndedMethod,
			tweenPVectorChangedMethod, tweenPVectorRepeatedMethod;

	private PVector vector = new PVector();

	public VectorTween(String _name, PVector _vector, PVector begin,
			PVector end, float _duration, float _delay, String _easing) {
		super(_name, new Property[] {
				new Property(_vector, "x", begin.x, end.x),
				new Property(_vector, "y", begin.y, end.y),
				new Property(_vector, "z", begin.z, end.z) }, _duration,
				_delay, _easing);

		vector = _vector;
	}

	public VectorTween(String _name, PVector _vector, PVector begin,
			PVector end, float _duration, float _delay) {
		super(_name, new Property[] {
				new Property(_vector, "x", begin.x, end.x),
				new Property(_vector, "y", begin.y, end.y),
				new Property(_vector, "z", begin.z, end.z) }, _duration, _delay);

		vector = _vector;
	}

	public VectorTween(String _name, PVector _vector, PVector begin,
			PVector end, float _duration) {
		super(_name, new Property[] {
				new Property(_vector, "x", begin.x, end.x),
				new Property(_vector, "y", begin.y, end.y),
				new Property(_vector, "z", begin.z, end.z) }, _duration);

		vector = _vector;
	}

	/**
	 * Sets the events
	 */
	@Override
	protected void setupEvents() {
		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			tweenPVectorStartedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_STARTED,
					new Class[] { VectorTween.class });
		} catch (Exception e) {
		}

		try {
			tweenPVectorEndedMethod = parentClass
					.getMethod(MotionEvent.TWEEN_ENDED,
							new Class[] { VectorTween.class });
		} catch (Exception e) {
		}

		try {
			tweenPVectorChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_CHANGED,
					new Class[] { VectorTween.class });
		} catch (Exception e) {
		}

		try {
			tweenPVectorRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_REPEATED,
					new Class[] { VectorTween.class });
		} catch (Exception e) {
		}
	}

	public float getX() {
		return vector.x;
	}

	public float getY() {
		return vector.y;
	}

	public float getZ() {
		return vector.y;
	}

	public void setPVector(PVector _vector) {
		vector = _vector;
	}

	public PVector getPVector() {
		return vector;
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		logger.println("dispatchMotionStartedEvent tweengroup");

		if (tweenPVectorStartedMethod != null) {
			try {
				tweenPVectorStartedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenPVectorStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_STARTED);
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		if (tweenPVectorEndedMethod != null) {
			try {
				tweenPVectorEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
				tweenPVectorEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_ENDED);
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		if (tweenPVectorChangedMethod != null) {
			try {
				tweenPVectorChangedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenPVectorChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_CHANGED);
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		if (tweenPVectorRepeatedMethod != null) {
			try {
				tweenPVectorRepeatedMethod
						.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenPVectorRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_REPEATED);
	}
}
