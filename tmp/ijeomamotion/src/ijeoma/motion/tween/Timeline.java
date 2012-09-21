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
 * @modified    09/20/2012
 * @version     4 (26)
 */

package ijeoma.motion.tween;

import ijeoma.motion.Callback;
import ijeoma.motion.Motion;
import ijeoma.motion.MotionController;
import ijeoma.motion.event.MotionEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import processing.core.PApplet;

public class Timeline extends MotionController {

	private Method timelineStartedMethod, timelineEndedMethod,
			timelineChangedMethod, timelineRepeatedMethod;

	public ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
	public HashMap<String, KeyFrame> keyFrameMap = new HashMap<String, KeyFrame>();

	public Timeline() {
		super();
	}

	public Timeline(Motion[] _children) {
		super(_children);
	}

	@Override
	protected void setupEvents() {
		super.setupEvents();

		Class<? extends PApplet> parentClass = parent.getClass();

		try {
			timelineStartedMethod = parentClass.getMethod(
					MotionEvent.TIMELINE_STARTED,
					new Class[] { Timeline.class });
		} catch (Exception e) {
		}

		try {
			timelineEndedMethod = parentClass.getMethod(
					MotionEvent.TIMELINE_ENDED, new Class[] { Timeline.class });
		} catch (Exception e) {
		}

		try {
			timelineRepeatedMethod = parentClass.getMethod(
					MotionEvent.TIMELINE_REPEATED,
					new Class[] { Timeline.class });
		} catch (Exception e) {
		}
	}

	public Timeline add(Motion _child, float _time) {
		KeyFrame kf = getKeyFrame(PApplet.str(_time));

		if (kf == null) {
			kf = new KeyFrame(_time);
			kf.add(_child);

			addKeyFrame(kf, PApplet.str(_time));
		} else
			kf.add(_child);

		return this;
	}

	@Override
	protected MotionController insert(Motion _child, String _name, float _time) {
		_child.setPlayTime(getPlayTime() + _time);

		_child.seek(1);

		// _child.setDelay(0);
		_child.setTimeMode(timeMode);

		_child.noAutoUpdate();

		_child.addEventListener(this);

		if (_child.isKeyFrame()) {
			keyFrames.add((KeyFrame) _child);
			if (_name != null)
				keyFrameMap.put(_name, (KeyFrame) _child);
		} else if (_child.isTween()) {
			tweens.add((Tween) _child);
			if (_name != null)
				tweenMap.put(_name, (Tween) _child);
		} else if (_child.isParallel()) {
			parallels.add((Parallel) _child);
			if (_name != null)
				parallelMap.put(_name, (Parallel) _child);
		} else if (_child.isSequence()) {
			sequences.add((Sequence) _child);
			if (_name != null)
				sequenceMap.put(_name, (Sequence) _child);
		} 
		
		// else if (_child.isCallback()) {
		// callbacks.add((Callback) _child);
		// if (_name != null)
		// callbackMap.put(_name, (Callback) _child);
		// }

		children.add(_child);
		if (_name != null)
			childrenMap.put(_name, _child);

		updateDuration();

		return this;
	}

	@Override
	public Timeline add(Motion _child, String _name) {
		// PApplet.println("insertChild(" + _child + ", " + _name + ")");
		KeyFrame c = (KeyFrame) childrenMap.get(_name);
		c.add(_child);

		children.set(children.indexOf(c), c);

		return this;
	}

	public Timeline addAll(Motion[] _children, float _time) {
		// PApplet.println("insertChildren(" + _children + ", " + _time + ")");

		KeyFrame keyFrame = getKeyFrame(_time);

		if (keyFrame == null) {
			keyFrame = new KeyFrame(_time);

			for (int j = 0; j < _children.length; j++)
				keyFrame.add(_children[j]);

			addKeyFrame(keyFrame, PApplet.str(_time));
		} else
			for (int j = 0; j < _children.length; j++)
				keyFrame.add(_children[j]);

		return this;
	}

	public Timeline addAll(Motion[] _children, String _name) {
		// PApplet.println("insertChildren(" + _children + ", " + _name + ")");
		KeyFrame c = (KeyFrame) childrenMap.get(_name);
		c.addAll(_children);

		children.set(children.indexOf(c), c);

		return this;
	}

	public Timeline addKeyFrame(KeyFrame _keyFrame) {
		return (Timeline) insert(_keyFrame, null, _keyFrame.getPlayTime());
	}

	public Timeline addKeyFrame(KeyFrame _keyFrame, String _name) {
		return (Timeline) insert(_keyFrame, _name, _keyFrame.getPlayTime());
	}

	public Timeline addKeyFrame(float _time) {
		return (Timeline) insert(new KeyFrame(_time), PApplet.str(_time), _time);
	}

	public Timeline addKeyFrame(float _time, Motion[] _children) {
		return (Timeline) insert(new KeyFrame(_time, _children),
				PApplet.str(_time), _time);
	}

	public Timeline addKeyFrame(String _name, float _time) {
		return (Timeline) insert(new KeyFrame(_time), _name, _time);
	}

	public Timeline addKeyFrame(String _name, float _time, Motion[] _children) {
		return (Timeline) insert(new KeyFrame(_time, _children), _name, _time);
	}

	public void removeKeyFrame(int _time) {
		for (Motion c : children)
			if (c.getPlayTime() == _time) {
				children.remove(children.indexOf(c));
				childrenMap.remove(c);
			}
	}

	public void removeKeyFrame(String _name) {
		KeyFrame c = (KeyFrame) childrenMap.get(_name);

		children.remove(children.indexOf(c));
		childrenMap.remove(c);
	}

	public int getKeyFrameCount() {
		return keyFrames.size();
	}

	public KeyFrame[] getCurrentKeyFrames() {
		ArrayList<KeyFrame> currentKeyFrames = new ArrayList<KeyFrame>();

		for (int i = 0; i < children.size(); i++)
			if (children.get(i).isInsidePlayingTime(getTime()))
				currentKeyFrames.add((KeyFrame) children.get(i));

		return currentKeyFrames.toArray(new KeyFrame[currentKeyFrames.size()]);
	}

	public int[] getCurrentKeyFrameIndices() {
		ArrayList<Integer> indicesList = new ArrayList<Integer>();
		int[] indices;

		for (int i = 0; i < children.size(); i++)
			if (children.get(i).isInsidePlayingTime(getTime()))
				indicesList.add(i);

		indices = new int[indicesList.size()];

		for (int i = 0; i < indicesList.size(); i++)
			indices[i] = indicesList.get(i);

		return indices;
	}
	
	public KeyFrame getKeyFrame(int _index) {
		return (KeyFrame) children.get(_index);
	}

	public KeyFrame getKeyFrame(float _time) {
		KeyFrame keyFrame = null;

		for (Motion c : children)
			if (c.getTime() == _time)
				keyFrame = (KeyFrame) c;

		return keyFrame;
	}

	public KeyFrame getKeyFrame(String _name) {
		return (KeyFrame) childrenMap.get(_name);
	}

	public KeyFrame[] getKeyFrames() {
		return children.toArray(new KeyFrame[children.size()]);
	}

	public float getKeyFrameTime(String _name) {
		return getKeyFrame(_name).getTime();
	}

	public Motion[] getKeyFrameChildren(String _name) {
		return getKeyFrame(_name).getChildren();
	}

	public void gotoAndPlay(float _time) {
		seek(_time / duration);
		resume();
	}

	public void gotoAndPlay(String _name) {
		KeyFrame kf = getKeyFrame(_name);

		seek(kf.getPlayTime() / duration);
		resume();
	}

	public void gotoAndPlay(KeyFrame _kf) {
		seek(_kf.getPlayTime() / duration);
		resume();
	}

	public void gotoAndStop(float _time) {
		seek(_time / duration);
		pause();
	}

	public void gotoAndStop(String _name) {
		KeyFrame kf = getKeyFrame(_name);

		seek(kf.getPlayTime() / duration);
		pause();
	}

	public void gotoAndStop(KeyFrame _kf) {
		seek(_kf.getPlayTime() / duration);
		pause();
	}

	@Override
	protected void dispatchMotionStartedEvent() {
		dispatchEvent(MotionEvent.TIMELINE_STARTED);

		if (timelineStartedMethod != null) {
			try {
				timelineStartedMethod.invoke(new Motion[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				timelineStartedMethod = null;
			}
		}
	}

	@Override
	protected void dispatchMotionEndedEvent() {
		dispatchEvent(MotionEvent.TIMELINE_ENDED);

		if (timelineEndedMethod != null) {
			try {
				timelineEndedMethod.invoke(new Motion[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				timelineEndedMethod = null;
			}
		}
	}

	@Override
	protected void dispatchMotionChangedEvent() {
		dispatchEvent(MotionEvent.TIMELINE_CHANGED);

		if (timelineChangedMethod != null) {
			try {
				timelineChangedMethod.invoke(new Motion[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				timelineChangedMethod = null;
			}
		}
	}

	@Override
	protected void dispatchMotionRepeatedEvent() {
		dispatchEvent(MotionEvent.TIMELINE_REPEATED);

		if (timelineRepeatedMethod != null) {
			try {
				timelineRepeatedMethod.invoke(new Motion[] { this });
			} catch (Exception e) {
				// e.printStackTrace();
				timelineRepeatedMethod = null;
			}
		}
	}

	@Override
	public String toString() {
		String keyFrameNames = "";

		Iterator i = childrenMap.entrySet().iterator();

		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			keyFrameNames += "{" + me.getKey() + "," + me.getValue() + "},";
		}

		return ("Timeline[children: [" + keyFrameNames + "] duration: "
				+ duration + "]");
	}

	public void printKeyFrames() {
		String childrenAsString = "";

		for (int i = 0; i < children.size(); i++)
			childrenAsString += children.get(i).toString()
					+ ((i < children.size() - 1) ? ", " : "");

		PApplet.println(childrenAsString);
	}

	@Override
	public void onMotionEvent(MotionEvent te) {
		// TODO Auto-generated method stub

	}
}
