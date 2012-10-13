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

	public Timeline(String _name) {
		super(_name);
	}

	public Timeline(String _name, Motion[] _children) {
		super(_name, _children);
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

	@Override
	protected Timeline insert(Motion _child, float _time) {
		super.insert(_child, _time);

		if (_child.isKeyFrame()) {
			keyFrames.add((KeyFrame) _child);
			if (_child.getName() != null)
				keyFrameMap.put(_child.getName(), (KeyFrame) _child);
		}

		return this;
	}

	@Override
	public Timeline add(Motion _child) {
		KeyFrame c = (KeyFrame) childrenMap.get(_child.getName());
		c.add(_child);

		children.set(children.indexOf(c), c);

		return this;
	}

	public Timeline add(Motion _child, float _time) {
		KeyFrame kf = getKeyFrame(PApplet.str(_time));

		if (kf == null) {
			kf = new KeyFrame(PApplet.str(_time), _time);
			kf.add(_child);

			insert(kf, _time);
		} else
			kf.add(_child);

		PApplet.println(kf.getDuration());

		return this;
	}

	public Timeline addAll(Motion[] _children, float _time) {
		KeyFrame kf = getKeyFrame(_time);

		if (kf == null) {
			kf = new KeyFrame(PApplet.str(_time), _time);

			for (int j = 0; j < _children.length; j++)
				kf.add(_children[j]);

			insert(kf, _time);
		} else
			for (int j = 0; j < _children.length; j++)
				kf.add(_children[j]);

		return this;
	}

	public Timeline addAll(Motion[] _children, String _name) { 
		KeyFrame c = (KeyFrame) childrenMap.get(_name);
		c.addAll(_children);

		children.set(children.indexOf(c), c);

		return this;
	}

	public void removeKeyFrame(int _time) {
		for (Motion c : children) 
			if (c.getDelay() == _time) {
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
 
		seek(kf.getDelay() / duration);
		resume();
	}

	public void gotoAndPlay(KeyFrame _kf) { 
		seek(_kf.getDelay() / duration);
		resume();
	}

	public void gotoAndStop(float _time) {
		seek(_time / duration);
		pause();
	}

	public void gotoAndStop(String _name) {
		KeyFrame kf = getKeyFrame(_name);
		
		seek(kf.getDelay() / duration);
		pause();
	}

	public void gotoAndStop(KeyFrame _kf) { 
		seek(_kf.getDelay() / duration);
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
