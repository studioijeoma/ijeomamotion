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
 * @modified    10/30/2012
 * @version     4 (40)
 */

package ijeoma.motion.tween;

import ijeoma.motion.Motion;
import ijeoma.motion.MotionController;
import ijeoma.motion.event.MotionEvent;

import java.lang.reflect.Method;

import processing.core.PApplet;

public class KeyFrame extends MotionController {
	private Method keyframeStartedMethod, keyframeEndedMethod,
			keyframeChangedMethod, keyframeRepeatedMethod;

	public KeyFrame(float _time) {
		super(PApplet.str(_time)); 
		delay(_time);
	}

	public KeyFrame(float _time, Motion[] _children) {
		super(PApplet.str(_time)); 
		delay(_time);
		addAll(_children);
	}

	public KeyFrame(String _name, float _time) {
		super(_name);
		delay(_time); 
	}

	public KeyFrame(String _name, float _time, Motion[] _children) {
		super(_name); 
		delay(_time);
		addAll(_children);
	}

	@Override
	protected void setupEvents() {
		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			keyframeStartedMethod = parentClass.getMethod(
					MotionEvent.KEYFRAME_STARTED,
					new Class[] { KeyFrame.class });
		} catch (Exception e) {
		}

		try {
			keyframeEndedMethod = parentClass.getMethod(
					MotionEvent.KEYFRAME_ENDED, new Class[] { KeyFrame.class });
		} catch (Exception e) {
		}

		try {
			keyframeChangedMethod = parentClass.getMethod(
					MotionEvent.KEYFRAME_CHANGED,
					new Class[] { KeyFrame.class });
		} catch (Exception e) {
		}

		try {
			keyframeRepeatedMethod = parentClass.getMethod(
					MotionEvent.KEYFRAME_REPEATED,
					new Class[] { KeyFrame.class });
		} catch (Exception e) {
		}
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		dispatchEvent(MotionEvent.TIMELINE_STARTED);

		if (keyframeStartedMethod != null) {
			try {
				keyframeStartedMethod.invoke(new Motion[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				keyframeStartedMethod = null;
			}
		}
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		dispatchEvent(MotionEvent.TIMELINE_ENDED);

		if (keyframeEndedMethod != null) {
			try {
				keyframeEndedMethod.invoke(new Motion[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				keyframeEndedMethod = null;
			}
		}
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		dispatchEvent(MotionEvent.TIMELINE_CHANGED);

		if (keyframeChangedMethod != null) {
			try {
				keyframeChangedMethod.invoke(new Motion[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				keyframeChangedMethod = null;
			}
		}
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		dispatchEvent(MotionEvent.TIMELINE_REPEATED);

		if (keyframeRepeatedMethod != null) {
			try {
				keyframeRepeatedMethod.invoke(new Motion[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				keyframeRepeatedMethod = null;
			}
		}
	}

	@Override
	public String toString() {
		String childrenNames = "";
 
		return ("KeyFrame[time:" + Float.toString(delay) + ", duration: "
				+ duration + ", children[" + children + "]]");
	}

	@Override
	public void onMotionEvent(MotionEvent te) {
		// TODO Auto-generated method stub

	}
}
