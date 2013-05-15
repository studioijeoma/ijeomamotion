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

package ijeoma.motion.event;

import java.util.EventObject;

public class MotionEvent extends EventObject {
	public static String MOTION_STARTED = "motionStarted";
	public static String MOTION_ENDED = "motionEnded";
	public static String MOTION_CHANGED = "motionChanged";
	public static String MOTION_REPEATED = "motionRepeated";

	public static String CALLBACK_STARTED = "timelineStarted";
	public static String CALLBACK_ENDED = "timelineEnded";

	public static String TWEEN_STARTED = "tweenStarted";
	public static String TWEEN_ENDED = "tweenEnded";
	public static String TWEEN_CHANGED = "tweenChanged";
	public static String TWEEN_REPEATED = "tweenRepeated";

	public static String TWEEN_PARALLEL_STARTED = "tweenParallelStarted";
	public static String TWEEN_PARALLEL_ENDED = "tweenParallelEnded";
	public static String TWEEN_PARALLEL_CHANGED = "tweenParallelChanged";
	public static String TWEEN_PARALLEL_REPEATED = "tweenParallelRepeated";

	public static String TWEEN_SEQUENCE_STARTED = "tweenSequenceStarted";
	public static String TWEEN_SEQUENCE_ENDED = "tweenSequenceEnded";
	public static String TWEEN_SEQUENCE_CHANGED = "tweenSequenceChanged";
	public static String TWEEN_SEQUENCE_REPEATED = "tweenSequenceRepeated";

	public static String TIMELINE_STARTED = "timelineStarted";
	public static String TIMELINE_ENDED = "timelineEnded";
	public static String TIMELINE_CHANGED = "timelineChanged";
	public static String TIMELINE_REPEATED = "timelineRepeated";

	public static String KEYFRAME_STARTED = "keyframeStarted";
	public static String KEYFRAME_ENDED = "keyframeEnded";
	public static String KEYFRAME_CHANGED = "keyframeChanged";
	public static String KEYFRAME_REPEATED = "keyframeRepeated";

	public String type;

	public MotionEvent(Object source, String _eventType) {
		super(source);
		type = _eventType;
	}
}
