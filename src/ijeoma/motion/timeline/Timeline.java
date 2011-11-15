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
 
package ijeoma.motion.timeline;

import ijeoma.motion.Motion;
import ijeoma.motion.Callback;
import ijeoma.motion.MotionController;
import ijeoma.motion.MotionSequenceController;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.TweenParallel;
import ijeoma.motion.tween.TweenSequence;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections; //import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;

import processing.core.PApplet;

public class Timeline extends MotionSequenceController {

	private Method timelineStartedMethod, timelineEndedMethod,
			timelineChangedMethod, timelineRepeatedMethod;

	public ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();

	public Timeline() {
		super();
		setupEvents();
	}

	public Timeline(String _name, Motion[] _childs) {
		super();
		setName(_name);
		appendChildren(_childs);
		setupEvents();
	}

	@Override
	protected void setupEvents() {
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

	/**
	 * adds a Motion object
	 */
	private void addChild(Motion _child) {
		// PApplet.println("addChild(" + _child + ")");

		_child.setTimeMode(timeMode);
		_child.setAutoUpdate(false);
		_child.addEventListener(this);

		if (_child.isTween())
			tweens.add((Tween) _child);
		else if (_child.isTweenParallel())
			tweenParallels.add((TweenParallel) _child);
		else if (_child.isKeyFrame())
			keyFrames.add((KeyFrame) _child);
		else if (_child.isTweenSequence())
			tweenSequences.add((TweenSequence) _child);
		else if (_child.isCallback())
			callbacks.add((Callback) _child);

		children.add(_child);

		updateDuration();
	}

	public void insertChild(Motion _child, float _time) {
		// PApplet.println("insertChild(" + _child + ", " + _time + ")");

		KeyFrame kf = getKeyFrame(_time);

		if (kf == null) {
			kf = new KeyFrame(Float.toString(_time), _time);
			kf.addChild(_child);

			addKeyFrame(kf);
		} else
			kf.addChild(_child);

		// Collections.sort(children);
	}

	public void insertChildren(Motion[] _children, float _time) {
		// PApplet.println("insertChildren(" + _children + ", " + _time + ")");

		KeyFrame keyFrame = getKeyFrame(_time);

		if (keyFrame == null) {
			keyFrame = new KeyFrame(Float.toString(_time), _time);

			for (int j = 0; j < _children.length; j++)
				keyFrame.addChild(_children[j]);

			addKeyFrame(keyFrame);
		} else
			for (int j = 0; j < _children.length; j++)
				keyFrame.addChild(_children[j]);

		// Collections.sort(children);
	}

	public void insertChild(Motion _child, String _name) {
		// PApplet.println("insertChild(" + _child + ", " + _name + ")");
		for (int i = 0; i < children.size(); i++)
			if (children.get(i).getName().equals(_name))
				((KeyFrame) children.get(i)).addChild(_child);

		// Collections.sort(children);
	}

	public void insertChildren(Motion[] _children, String _name) {
		// PApplet.println("insertChildren(" + _children + ", " + _name + ")");
		for (int i = 0; i < children.size(); i++)
			if (children.get(i).getName().equals(_name))
				((KeyFrame) children.get(i)).addChildren(_children);

		// Collections.sort(children);
	}

	public void addKeyFrame(KeyFrame _keyFrame) {
		addChild(_keyFrame);
	}

	public void addKeyFrame(float _time) {
		addChild(new KeyFrame(PApplet.str(_time), _time));
	}

	public void addKeyFrame(float _time, Motion[] _children) {
		addChild(new KeyFrame(PApplet.str(_time), _time, _children));
	}

	public void addKeyFrame(String _name, float _time) {
		addChild(new KeyFrame(_name, _time));
	}

	public void addKeyFrame(String _name, float _time, Motion[] _children) {
		addChild(new KeyFrame(_name, _time, _children));
	}

	public void removeKeyFrame(int _time) {
		for (int i = 0; i < children.size(); i++)
			if (children.get(i).getPlayTime() == _time)
				children.remove(i);
	}

	public void removeKeyFrame(String _name) {
		for (int i = 0; i < children.size(); i++)
			if (children.get(i).getName().equals(_name))
				children.remove(i);
	}

	public int getKeyFrameCount() {
		return keyFrames.size();
	}

	public KeyFrame[] getCurrentKeyFrames() {
		ArrayList<KeyFrame> currentKeyFrames = new ArrayList<KeyFrame>();

		for (int i = 0; i < children.size(); i++)
			if (children.get(i).isTimeInside(getTime()))
				currentKeyFrames.add((KeyFrame) children.get(i));

		return currentKeyFrames.toArray(new KeyFrame[currentKeyFrames.size()]);
	}

	public int[] getCurrentKeyFrameIndices() {
		ArrayList<Integer> oindicesList = new ArrayList<Integer>();
		int[] indices;

		for (int i = 0; i < children.size(); i++)
			if (children.get(i).isTimeInside(getTime()))
				oindicesList.add(i);

		indices = new int[oindicesList.size()];

		for (int i = 0; i < oindicesList.size(); i++)
			indices[i] = oindicesList.get(i);

		return indices;
	}

	public KeyFrame getKeyFrame(float _time) {
		KeyFrame keyFrame = null;

		for (int i = 0; i < children.size(); i++)
			if (children.get(i).getTime() == _time)
				keyFrame = (KeyFrame) children.get(i);

		return keyFrame;
	}

	public KeyFrame getKeyFrame(String _name) {
		KeyFrame keyFrame = null;

		for (int i = 0; i < children.size(); i++)
			if ((children.get(i).getName()).equals(_name))
				keyFrame = (KeyFrame) children.get(i);

		return keyFrame;
	}

	public KeyFrame[] getKeyFrames() {
		return children.toArray(new KeyFrame[children.size()]);
	}

	public float getKeyFrameTime(String _name) {
		float frameTime = 0;

		for (int i = 0; i < children.size(); i++)
			if (children.get(i).getName().equals(_name))
				frameTime = children.get(i).getTime();

		return frameTime;
	}

	public Motion[] getKeyFrameChildren(String _name) {
		Motion[] keyFrameChildren = null;

		for (int i = 0; i < children.size(); i++)
			if (children.get(i).getName().equals(_name))
				keyFrameChildren = ((KeyFrame) children.get(i)).getChildren();

		return keyFrameChildren;
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

	public void gotoAndStop(float _time) {
		seek(_time / duration);
		pause();
	}

	public void gotoAndStop(String _name) {
		play();

		KeyFrame kf = getKeyFrame(_name);
		seek(kf.getPlayTime() / duration);

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

		for (int i = 0; i < children.size(); i++)
			keyFrameNames += children.get(i).getName()
					+ ((i < children.size() - 1) ? ", " : "");

		return ("Timeline[children: {" + keyFrameNames + "} duration: "
				+ duration + "]");
	}

	public void printKeyFrames() {
		String childrenAsString = "";

		for (int i = 0; i < children.size(); i++)
			childrenAsString += children.get(i).toString()
					+ ((i < children.size() - 1) ? ", " : "");

		PApplet.println(childrenAsString);
	}

	public void onMotionEvent(MotionEvent te) {
		// TODO Auto-generated method stub

	}
}
