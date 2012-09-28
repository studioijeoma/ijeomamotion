package ijeoma.geom.tween;
///**
// * ijeomamotion
// * A library for sketching animations with numbers, colors vectors, beziers, curves and more! 
// * http://ekeneijeoma.com/processing/ijeomamotion/
// *
// * Copyright (C) 2012 Ekene Ijeoma http://ekeneijeoma.com
// *
// * This library is free software; you can redistribute it and/or
// * modify it under the terms of the GNU Lesser General Public
// * License as published by the Free Software Foundation; either
// * version 2.1 of the License, or (at your option) any later version.
// * 
// * This library is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// * Lesser General Public License for more details.
// * 
// * You should have received a copy of the GNU Lesser General
// * Public License along with this library; if not, write to the
// * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
// * Boston, MA  02111-1307  USA
// * 
// * @author      Ekene Ijeoma http://ekeneijeoma.com
// * @modified    09/28/2012
// * @version     4 (26)
// */
//
//package ijeoma.processing.tween;
//
//import ijeoma.motion.event.MotionEvent;
//import ijeoma.motion.tween.Tween;
//import ijeoma.processing.geom.SVGPath2D;
//
//import java.lang.reflect.Method;
//
//import processing.core.PApplet;
//import processing.core.PVector;
//
//public class SVGPath2DTween extends Tween { // implements Comparable {
//	private Method tweenSVGPathStartedMethod, tweenSVGPathEndedMethod,
//			tweenSVGPathChangedMethod, tweenSVGPathRepeatedMethod;
//
//	private SVGPath2D path;
//
//	public SVGPath2DTween(SVGPath2D _path, float _begin, float _end,
//			float _duration, float _delay, String _easing) {
//		super(_duration, _delay, _easing);
//		setupSVGPath(_path);
//	}
//
//	public SVGPath2DTween(SVGPath2D _path, float _begin, float _end,
//			float _duration, float _delay) {
//		super(_duration, _delay);
//		setupSVGPath(_path);
//	}
//
//	public SVGPath2DTween(SVGPath2D _path, float _begin, float _end,
//			float _duration) {
//		super(_duration);
//		setupSVGPath(_path);
//	}
//
//	protected void setupSVGPath(SVGPath2D _path) {
//		path = _path;
//	}
//
//	/**
//	 * Sets the events
//	 */
//	@Override
//	protected void setupEvents() {
//		super.setupEvents();
//
//		Class<? extends PApplet> parentClass = parent.getClass();
//
//		try {
//			tweenSVGPathStartedMethod = parentClass.getMethod(
//					MotionEvent.TWEEN_STARTED,
//					new Class[] { SVGPath2DTween.class });
//		} catch (Exception e) {
//		}
//
//		try {
//			tweenSVGPathEndedMethod = parentClass.getMethod(
//					MotionEvent.TWEEN_ENDED,
//					new Class[] { SVGPath2DTween.class });
//		} catch (Exception e) {
//		}
//
//		try {
//			tweenSVGPathChangedMethod = parentClass.getMethod(
//					MotionEvent.TWEEN_CHANGED,
//					new Class[] { SVGPath2DTween.class });
//		} catch (Exception e) {
//		}
//
//		try {
//			tweenSVGPathRepeatedMethod = parentClass.getMethod(
//					MotionEvent.TWEEN_REPEATED,
//					new Class[] { SVGPath2DTween.class });
//		} catch (Exception e) {
//		}
//	}
//
//	public PVector getPoint() {
//		return path.getPoint(getPosition());
//	}
//
//	public float getX() {
//		return getPoint().x;
//	}
//
//	public float getY() {
//		return getPoint().y;
//	}
//
//	public void setSVGPath(SVGPath2D _path) {
//		path = _path;
//	}
//
//	public SVGPath2D getSVGPath() {
//		return path;
//	}
//
//	@Override
//	protected void dispatchMotionStartedEvent() {
//		if (tweenSVGPathStartedMethod != null) {
//			try {
//				tweenSVGPathStartedMethod.invoke(parent, new Object[] { this });
//			} catch (Exception e) {
//				// e.printStackTrace();
//				tweenSVGPathStartedMethod = null;
//			}
//		}
//
//		dispatchEvent(MotionEvent.TWEEN_STARTED);
//	}
//
//	@Override
//	protected void dispatchMotionEndedEvent() {
//		if (tweenSVGPathEndedMethod != null) {
//			try {
//				tweenSVGPathEndedMethod.invoke(parent, new Object[] { this });
//			} catch (Exception e) {
//				e.printStackTrace();
//				tweenSVGPathEndedMethod = null;
//			}
//		}
//
//		dispatchEvent(MotionEvent.TWEEN_ENDED);
//	}
//
//	@Override
//	protected void dispatchMotionChangedEvent() {
//		if (tweenSVGPathChangedMethod != null) {
//			try {
//				tweenSVGPathChangedMethod.invoke(parent, new Object[] { this });
//			} catch (Exception e) {
//				// e.printStackTrace();
//				tweenSVGPathChangedMethod = null;
//			}
//		}
//
//		dispatchEvent(MotionEvent.TWEEN_CHANGED);
//	}
//
//	@Override
//	protected void dispatchMotionRepeatedEvent() {
//		if (tweenSVGPathRepeatedMethod != null) {
//			try {
//				tweenSVGPathRepeatedMethod
//						.invoke(parent, new Object[] { this });
//			} catch (Exception e) {
//				// e.printStackTrace();
//				tweenSVGPathRepeatedMethod = null;
//			}
//		}
//
//		dispatchEvent(MotionEvent.TWEEN_REPEATED);
//	}
// }
