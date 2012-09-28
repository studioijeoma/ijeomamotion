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

import ijeoma.geom.Path3D;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.tween.Tween;

import java.lang.reflect.Method;

import processing.core.PApplet;
import processing.core.PVector;

public class Path3DTween extends Tween {
	private Method tweenPathStartedMethod, tweenPathEndedMethod,
			tweenPathChangedMethod, tweenPathRepeatedMethod;

	private Path3D path;

	public Path3DTween(Path3D _path, float _begin, float _end, float _duration,
			float _delay, String _easing) {
		super(_duration, _delay, _easing);
		setupPath(_path);
	}

	public Path3DTween(Path3D _path, float _begin, float _end, float _duration,
			float _delay) {
		super(_duration, _delay);
		setupPath(_path);
	}

	public Path3DTween(Path3D _path, float _begin, float _end, float _duration) {
		super(_duration);
		setupPath(_path);
	}

	protected void setupPath(Path3D _path) {
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
			tweenPathStartedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_STARTED,
					new Class[] { Path3DTween.class });
		} catch (Exception e) {
		}

		try {
			tweenPathEndedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_ENDED, new Class[] { Path3DTween.class });
		} catch (Exception e) {
		}

		try {
			tweenPathChangedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_CHANGED,
					new Class[] { Path3DTween.class });
		} catch (Exception e) {
		}

		try {
			tweenPathRepeatedMethod = parentClass.getMethod(
					MotionEvent.TWEEN_REPEATED,
					new Class[] { Path3DTween.class });
		} catch (Exception e) {
		}
	}

	public PVector getPoint() {
		return path.get(getPosition());
	}

	public float getX() {
		return getPoint().x;
	}

	public float getY() {
		return getPoint().y;
	}

	public float getZ() {
		return getPoint().z;
	}

	public void setPath(Path3D _path) {
		path = _path;
	}

	public Path3D getPath() {
		return path;
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		if (tweenPathStartedMethod != null) {
			try {
				tweenPathStartedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenPathStartedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_STARTED);
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		if (tweenPathEndedMethod != null) {
			try {
				tweenPathEndedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
				tweenPathEndedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_ENDED);
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		if (tweenPathChangedMethod != null) {
			try {
				tweenPathChangedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenPathChangedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_CHANGED);
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		if (tweenPathRepeatedMethod != null) {
			try {
				tweenPathRepeatedMethod.invoke(parent, new Object[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				tweenPathRepeatedMethod = null;
			}
		}

		dispatchEvent(MotionEvent.TWEEN_REPEATED);
	}
}
