/**
 * ijeomamotion
 * A collection of utilities creating flash-like animations.
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
 * @modified    08/21/2012
 * @version     4 (26)
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
	public ArrayList<Motion> children = new ArrayList<Motion>();
	public ArrayList<Tween> tweens = new ArrayList<Tween>();
	public ArrayList<TweenParallel> tweenParallels = new ArrayList<TweenParallel>();
	public ArrayList<TweenSequence> tweenSequences = new ArrayList<TweenSequence>();
	public ArrayList<Callback> callbacks = new ArrayList<Callback>();

	public HashMap<String, Motion> childrenMap = new HashMap<String, Motion>();
	public HashMap<String, Tween> tweenMap = new HashMap<String, Tween>();
	public HashMap<String, TweenParallel> tweenParallelMap = new HashMap<String, TweenParallel>();
	public HashMap<String, TweenSequence> tweenSequenceMap = new HashMap<String, TweenSequence>();
	public HashMap<String, Callback> callbackMap = new HashMap<String, Callback>();

	protected ArrayList<MotionEventListener> listeners;

	public MotionController() {
		super();
	}

	public MotionController(Motion[] _children) {
		super();
		this.addAll(_children);
	}

	@Override
	public MotionController play() {
		return (MotionController) super.play();
	}

	@Override
	public MotionController stop() {
		super.stop();

		for (Motion c : children)
			if (c.isPlaying)
				c.stop();

		return this;
	}

	@Override
	public MotionController pause() {
		super.pause();

		for (Motion c : children)
			c.pause();

		return this;
	}

	/**
	 * Resumes the tween
	 */
	@Override
	public MotionController resume() {
		super.resume();

		for (Motion c : children)
			c.resume();

		return this;
	}

	/**
	 * Changes the time of the tween to a time percentange between 0 and 1
	 */
	@Override
	public MotionController seek(float _value) {
		super.seek(_value);

		for (Motion c : children) {
			if (c.isAbovePlayTime(time))
				if (c.isBelowStopTime(time))
					c.seek((time - c.getPlayTime()) / c.getDuration());
				else
					c.seek(1);
			else
				c.seek(0);
		}

		return this;
	}

	@Override
	public void update() {
		if (isPlaying()) {
			updateTime();

			if (isAbovePlayTime(time))
				if (isBelowStopTime(time)) {
					updateCallbacks();
					updateChildren();
				} else
					stop();
		}
	}

	@Override
	public void update(float _time) {
		super.update();

		if (isPlaying()) {
			setTime(_time);

			if (isAbovePlayTime(_time))
				if (isBelowStopTime(_time)) {
					updateCallbacks();
					updateChildren();
				} else
					stop();
		}
	}

	protected void updateChildren() {
		for (Motion c : children) {
			if (c.isInsidePlayingTime(time))
				if (c.isPlaying())
					c.update(time);
				else
					c.play();
		}
	}

	protected void updateDuration() {
		for (Motion c : children)
			duration = PApplet.max(duration, (c.getPlayTime() - getPlayTime())
					+ c.getDelayedDuration());
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
		return tweenMap.get(_name);
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
		return tweenParallelMap.get(_name);
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
		return tweenSequenceMap.get(_name);
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
		return callbackMap.get(_name);
	}

	// public float getChildPosition(int _index) {
	// return getChild(_index).getPosition();
	// }
	//
	// public float getChildPosition(String _name) {
	// return getChild(_name).getPosition();
	// }

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
	public Motion get(int _index) {
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
		return childrenMap.get(_name);
	}

	/**
	 * Returns child Motion object count
	 */
	public int getCount() {
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
	public MotionController setTimeScale(float _timeScale) {
		super.setTimeScale(_timeScale);

		for (Motion child : children)
			child.setTimeScale(timeScale);

		return this;
	}

	@Override
	public MotionController setTimeMode(String _durationMode) {
		super.setTimeMode(_durationMode);

		for (Motion child : children)
			child.setTimeMode(_durationMode);

		return this;
	}

	public MotionController add(Motion _child) {
		return add(_child, null);
	}

	public MotionController add(Motion _child, String _name) {
		return insert(_child, _name, 0);
	}

	protected MotionController insert(Motion _child, String _name, float _time) {
		_child.setPlayTime(getPlayTime() + _time);

		_child.seek(1);

		// _child.setDelay(0);
		_child.setTimeMode(timeMode);

		_child.noAutoUpdate();

		_child.addEventListener(this);

		if (_child.isTween()) {
			tweens.add((Tween) _child);
			if (_name != null)
				tweenMap.put(_name, (Tween) _child);
		} else if (_child.isTweenParallel()) {
			tweenParallels.add((TweenParallel) _child);
			if (_name != null)
				tweenParallelMap.put(_name, (TweenParallel) _child);
		} else if (_child.isTweenSequence()) {
			tweenSequences.add((TweenSequence) _child);
			if (_name != null)
				tweenSequenceMap.put(_name, (TweenSequence) _child);
		} else if (_child.isCallback()) {
			callbacks.add((Callback) _child);
			if (_name != null)
				callbackMap.put(_name, (Callback) _child);
		}

		children.add(_child);
		if (_name != null)
			childrenMap.put(_name, _child);

		updateDuration();

		return this;
	}

	/**
	 * Removes Motion object
	 */
	public MotionController removeChild(Motion _child) {
		if (_child.isTween()) {
			tweens.remove(_child);
			// tweenLUT.remove(_child.name);
		} else if (_child.isTweenParallel()) {
			tweenParallels.remove(_child);
			// tweenParallelLUT.remove(_child.name);
		} else if (_child.isTweenSequence()) {
			tweenSequences.remove(_child);
			// tweenSequenceLUT.remove(_child.name);
		} else if (_child.isCallback()) {
			callbacks.remove(_child);
			// callbackLUT.remove(_child.name);
		}

		children.remove(_child);
		// childrenLUT.remove(_child.name);

		return this;
	}

	public MotionController addTween(Object _tweenObject,
			String _tweenObjectProperty, float _end, float _duration,
			float _delay, String _easing) {
		return add(new Tween(_tweenObject, _tweenObjectProperty, _end,
				_duration, _delay, _easing), _tweenObjectProperty);
	}

	public MotionController addTween(Object _tweenObject,
			String _tweenObjectProperty, float _end, float _duration,
			float _delay) {
		return add(new Tween(_tweenObject, _tweenObjectProperty, _end,
				_duration, _delay), _tweenObjectProperty);
	}

	public MotionController addTween(Object _tweenObject,
			String _tweenObjectProperty, float _end, float _duration) {
		return add(new Tween(_tweenObject, _tweenObjectProperty, _end,
				_duration), _tweenObjectProperty);
	}

	/**
	 * adds multiple Motion objects
	 */
	public MotionController addAll(Motion[] _children) {
		for (int i = 0; i < _children.length; i++)
			add(_children[i]);

		return this;
	}

	/**
	 * Removes all Motion objects
	 */
	public MotionController removeAll() {
		tweens.clear();
		tweenMap.clear();

		tweenParallels.clear();
		tweenParallelMap.clear();

		tweenSequences.clear();
		tweenSequenceMap.clear();

		callbacks.clear();
		callbackMap.clear();

		children.clear();
		childrenMap.clear();

		return this;
	}

	public void printChildren() {
		String childrenAsString = "";

		int i = 0;

		for (Motion child : children) {
			childrenAsString += child.toString();
			childrenAsString += ((i < children.size() - 1) ? ", " : "");
			i++;
		}

		// logger.println(childrenAssString);
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
