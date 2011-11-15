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
 
package ijeoma.motion;

import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.TweenParallel;
import ijeoma.motion.tween.TweenSequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import processing.core.PApplet;

public abstract class MotionController extends Motion implements
		MotionConstant, MotionEventListener {
	public ArrayList<Motion> children;// = new ArrayList<Motion>();
	public ArrayList<Tween> tweens = new ArrayList<Tween>();
	public ArrayList<TweenParallel> tweenParallels = new ArrayList<TweenParallel>();
	public ArrayList<TweenSequence> tweenSequences = new ArrayList<TweenSequence>();
	public ArrayList<Callback> callbacks = new ArrayList<Callback>();

	public HashMap<String, Tween> tweenLUT = new HashMap<String, Tween>();
	public HashMap<String, TweenParallel> tweenParallelLUT = new HashMap<String, TweenParallel>();
	public HashMap<String, TweenSequence> tweenSequenceLUT = new HashMap<String, TweenSequence>();
	public HashMap<String, Callback> callbackLUT = new HashMap<String, Callback>();
	public HashMap<String, Motion> childrenLUT = new HashMap<String, Motion>();

	protected ArrayList<MotionEventListener> listeners;

	public MotionController() {
		super();
		children = new ArrayList<Motion>();
	}

	public void stop() {
		for (Motion child : children)
			if (child.isPlaying)
				child.stop();

		super.stop();
	}

	@Override
	public void pause() {
		for (Motion child : children)
			child.pause();

		super.pause();
	}

	/**
	 * Resumes the tween
	 */
	@Override
	public void resume() {
		for (Motion child : children)
			child.resume();

		super.resume();
	}

	/**
	 * Changes the time of the tween to a time percentange between 0 and 1
	 */
	@Override
	public void seek(float _value) {
		super.seek(_value);

		for (Motion child : children) {
			if (child.isTimeInside(getTime()))
				child.seek((getTime() - child.getPlayTime())
						/ child.getDuration());
			else if (child.isTimeAbove(getTime()))
				child.seek(1f);
			else
				child.seek(0f);
		}
	}

	public void update() {
		if (isPlaying()) {
			updateTime();

			if (time > delay)
				if (time > 0 && time < getDelayDuration()) {
					updatePosition();
					updateChildren();
				} else
					stop();
			else
				stop();
		}
	}

	public void update(float _time) {
		if (isPlaying()) {
			setTime(_time);

			if (time > delay)
				if (time > 0 && time < getDelayDuration()) {
					updatePosition();
					updateChildren();
				} else
					stop();
			else
				stop();
		}
	}

	protected void updateChildren() {
		for (Motion child : children) {
			if (child.isTimeInside(getTime())) {
				if (child.isPlaying())
					child.update(getTime() - child.getPlayTime());
				else
					child.play();
			}
			// else
			// m.pause();
		}
	}

	protected void updateDuration() {
		for (Motion child : children)
			duration = PApplet.max(duration,
					child.getPlayTime() + child.getDelayDuration());

		setEnd(duration);
		setChange(duration);
	}

	/**
	 * Returns a Tween by id/index (useful if you're controlling Tweens only)
	 */
	public Tween getTween(int _index) {
		if (_index < tweens.size())
			return tweens.get(_index);
		else
			return null;
	}

	/**
	 * Returns a Tween by name
	 */
	public Tween getTween(String _name) {
		return tweenLUT.get(_name);
	}

	/**
	 * Returns a TweenParallel by id/index
	 */
	public TweenParallel getTweenParallel(int _index) {
		if (_index < tweenParallels.size())
			return tweenParallels.get(_index);
		else
			return null;
	}

	/**
	 * Returns a TweenParallel by name (useful if you're only controlling
	 * TweenParallels)
	 */
	public TweenParallel getTweenParallel(String _name) {
		return tweenParallelLUT.get(_name);
	}

	/**
	 * Returns a Tween by id/index (useful if you're only controlling
	 * TweenSequences)
	 */
	public TweenSequence getTweenSequence(int _index) {
		if (_index < tweenSequences.size())
			return tweenSequences.get(_index);
		else
			return null;
	}

	/**
	 * Returns a Tween by id/index (useful if you're only controlling
	 * TweenSequences)
	 */
	public TweenSequence getTweenSequence(String _name) {
		return tweenSequenceLUT.get(_name);
	}

	/**
	 * Returns a Callback by id/index (useful if you're only controlling
	 * Callbacks)
	 */
	public Callback getCallback(int _index) {
		if (_index < callbacks.size())
			return callbacks.get(_index);
		else
			return null;
	}

	/**
	 * Returns a Callback by id/index (useful if you're only controlling
	 * Callbacks)
	 */
	public Callback getCallback(String _name) {
		return callbackLUT.get(_name);
	}

	public float getChildPosition(int _index) {
		return getChild(_index).getPosition();
	}

	public float getChildPosition(String _name) {
		return getChild(_name).getPosition();
	}

	/**
	 * Returns all motion objects (Callback, Tween, TweenParallel,
	 * TweenSequence)
	 */
	public Motion[] getChildren() {
		return children.toArray(new Motion[children.size()]);
	}

	/**
	 * Returns all motion objects as a list(Callback, Tween, TweenParallel,
	 * TweenSequence)
	 */
	public List<Motion> getChildrenList() {
		return children;
	}

	/**
	 * Returns all Tweens
	 */
	public Tween[] getTweens() {
		return tweens.toArray(new Tween[tweens.size()]);
	}

	/**
	 * Returns all Tweens as list
	 */
	public List<Tween> getTweenList() {
		return tweens;
	}

	/**
	 * Returns all TweenParallels
	 */
	public TweenParallel[] getTweenParallels() {
		return tweenParallels.toArray(new TweenParallel[tweenParallels.size()]);
	}

	/**
	 * Returns all TweenParallels as a list
	 */
	public List<TweenParallel> getTweenParallelList() {
		return tweenParallels;
	}

	/**
	 * Returns all TweenSequences
	 */
	public TweenSequence[] getTweenSequences() {
		return tweenSequences.toArray(new TweenSequence[tweenSequences.size()]);
	}

	/**
	 * Returns all TweenSequences as a list
	 */
	public List<TweenSequence> getTweenSequenceList() {
		return tweenSequences;
	}

	/**
	 * Returns all Callbacks
	 */
	public Callback[] getCallbacks() {
		return callbacks.toArray(new Callback[callbacks.size()]);
	}

	/**
	 * Returns all Callbacks
	 */
	public List<Callback> getCallbackList() {
		return callbacks;
	}

	/**
	 * Returns the Motion object (Tween, TweenParallel, TweenSequence, Callback)
	 * by id/index
	 */
	public Motion getChild(int _index) {
		if (_index < children.size())
			return children.get(_index);
		else
			return null;
	}

	/**
	 * Returns a motion object (Tween, TweenParallel, TweenSequence, Callback)
	 * by name
	 */
	public Motion getChild(String _name) {
		return childrenLUT.get(_name);
	}

	/**
	 * Returns child Motion object count
	 */
	public int getChildCount() {
		return children.size();
	}

	/**
	 * Returns child Tween object count
	 */
	public int getTweenCount() {
		return tweens.size();
	}

	/**
	 * Returns child TweenParallel object count
	 */
	public int getTweenParallelCount() {
		return tweenParallels.size();
	}

	/**
	 * Returns child TweenSequeunce object count
	 */
	public int getTweenSequenceCount() {
		return tweenSequences.size();
	}

	/**
	 * Returns child Callback object count
	 */
	public int getCallbackCount() {
		return callbacks.size();
	}

	@Override
	public void setTimeScale(float _timeScale) {
		super.setTimeScale(_timeScale);

		for (Motion child : children)
			child.setTimeScale(timeScale);
	}

	@Override
	public void setTimeMode(String _durationMode) {
		super.setTimeMode(_durationMode);

		for (Motion child : children)
			child.setTimeMode(_durationMode);
	}

	/**
	 * Removes Motion object
	 */
	public void removeChild(Motion _child) {
		if (_child.isTween()) {
			tweens.remove(_child);
			tweenLUT.remove(_child.name);
		} else if (_child.isTweenParallel()) {
			tweenParallels.remove(_child);
			tweenParallelLUT.remove(_child.name);
		} else if (_child.isTweenSequence()) {
			tweenSequences.remove(_child);
			tweenSequenceLUT.remove(_child.name);
		} else if (_child.isCallback()) {
			callbacks.remove(_child);
			callbackLUT.remove(_child.name);
		}

		children.remove(_child);
		childrenLUT.remove(_child.name);
	}

	/**
	 * Removes all Motion objects
	 */
	public void removeChildren() {
		tweens.clear();
		tweenLUT.clear();
		
		tweenParallels.clear();
		tweenParallelLUT.clear();
		
		tweenSequences.clear();
		tweenSequenceLUT.clear();
		
		callbacks.clear();
		callbackLUT.clear();

		children.clear();
		childrenLUT.clear();
	}

	public void printChildren() {
		String childrenAsString = "";

		int i = 0;

		for (Motion child : children) {
			childrenAsString += child.toString();
			childrenAsString += ((i < children.size() - 1) ? ", " : "");
			i++;
		}

		logger.println(childrenAsString);
	}

	@Override
	public MotionController addEventListener(MotionEventListener listener) {
		super.addEventListener(listener);

		for (Motion child : children)
			child.addEventListener(listener);

		return this;
	}

	@Override
	public void removeEventListener(MotionEventListener listener) {
		super.removeEventListener(listener);

		for (Motion child : children)
			child.removeEventListener(listener);
	}
}
