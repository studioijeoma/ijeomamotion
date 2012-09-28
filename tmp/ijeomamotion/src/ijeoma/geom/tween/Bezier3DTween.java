/**
 * ijeomamotion
 * A library for sketching animations with numbers, colors vectors, beziers, curves and more! 
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
 * @modified    09/28/2012
 * @version     4 (26)
 */

package ijeoma.geom.tween;

import ijeoma.geom.Bezier3D;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.tween.Tween;

import java.lang.reflect.Method;

import processing.core.PApplet;
import processing.core.PVector;

public class Bezier3DTween extends Tween { // implements Comparable {
	private Method tweenBezierStartedMethod, tweenBezierEndedMethod,
			tweenBezierChangedMethod, tweenBezierRepeatedMethod;

	private Bezier3D bezier;

	public Bezier3DTween(Bezier3D _path, float _begin, float _end,
			float _duration, float _delay, String _easing) {
		super(_duration, _delay, _easing);
		setupBezier(_path);
	}

	public Bezier3DTween(Bezier3D _path, float _begin, float _end,
			float _duration, float _delay) {
		super(_duration, _delay);
		setupBezier(_path);
	}

	public Bezier3DTween(Bezier3D _path, float _begin, float _end,
			float _duration) {
		super(_duration);
		setupBezier(_path);
	}

	protected void setupBezier(Bezier3D _bezier) {
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
					new Class[] { Bezier3DTween.class });
		} catch (Exception e) {
		}

		try {
			tweenBezierEndedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_ENDED,
					new Class[] { Bezier3DTween.class });
		} catch (Exception e) {
		}

		try {
			tweenBezierChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_CHANGED,
					new Class[] { Bezier3DTween.class });
		} catch (Exception e) {
		}

		try {
			tweenBezierRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_REPEATED,
					new Class[] { Bezier3DTween.class });
		} catch (Exception e) {
		}
	}

	public PVector getPoint() {
		return bezier.getPoint(getPosition());
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

	public void setBezier(Bezier3D _bezier) {
		bezier = _bezier;
	}

	public Bezier3D getBezier() {
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
