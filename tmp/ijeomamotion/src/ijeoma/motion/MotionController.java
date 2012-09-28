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

package ijeoma.motion;

import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.Parallel;
import ijeoma.motion.tween.Sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import processing.core.PApplet;

public abstract class MotionController extends Motion implements
		MotionConstant, MotionEventListener {
	public ArrayList<Motion> children = new ArrayList<Motion>();
	public ArrayList<Tween> tweens = new ArrayList<Tween>();
	public ArrayList<Parallel> parallels = new ArrayList<Parallel>();
	public ArrayList<Sequence> sequences = new ArrayList<Sequence>();

	public HashMap<String, Motion> childrenMap = new HashMap<String, Motion>();
	public HashMap<String, Tween> tweenMap = new HashMap<String, Tween>();
	public HashMap<String, Parallel> parallelMap = new HashMap<String, Parallel>();
	public HashMap<String, Sequence> sequenceMap = new HashMap<String, Sequence>();

	protected ArrayList<MotionEventListener> listeners;

	public MotionController() {
		super();
	}

	public MotionController(Motion[] _children) {
		super();
		this.addAll(_children);
	}

	public MotionController(String _name) {
		super(_name);
	}

	public MotionController(String _name, Motion[] _children) {
		super(_name);
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
			c.stop();
		// if (c.isPlaying())
		// c.stop();

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
			if (c.isInsidePlayingTime(getTime()))
				c.seek((getTime() - c.getPlayTime()) / c.getDuration());
			else if (c.isAbovePlayTime(getTime()))
				c.seek(1);
			else
				c.seek(0);

			// if (c.isAbovePlayTime(getTime()))
			// if (c.isBelowStopTime(getTime()))
			// c.seek((getTime() - c.getPlayTime()) / c.getDuration());
			// else
			// c.seek(1);
			// else
			// c.seek(0);
		}

		return this;
	}

	@Override
	public void update() {
		super.update();

		if (isPlaying())
			updateChildren();
	}

	@Override
	public void update(float _time) {
		super.update(_time);

		if (isPlaying())
			updateChildren();
	}

	protected void updateChildren() {
		for (Motion c : children) {
			if (c.isInsidePlayingTime(getTime()))
				if (c.isPlaying())
					c.update(getTime());
				else
					c.play();
		}
	}

	protected void updateDuration() { 
		for (Motion c : children)  
			duration = PApplet.max(duration,
					c.getPlayTime() + c.getDelayedDuration()); 

		for (Callback c : calls)
			duration = PApplet.max(duration, c.getTime() - getPlayTime());
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
	 * Returns a Parallel by id/index
	 */
	public Parallel getParallel(int _index) {
		if (_index < parallels.size())
			return parallels.get(_index);
		else
			return null;
	}

	/**
	 * Returns a Parallel by name (useful if you're only controlling Parallels)
	 */
	public Parallel getParallel(String _name) {
		return parallelMap.get(_name);
	}

	/**
	 * Returns a Tween by id/index (useful if you're only controlling Sequences)
	 */
	public Sequence getSequence(int _index) {
		if (_index < sequences.size())
			return sequences.get(_index);
		else
			return null;
	}

	/**
	 * Returns a Tween by id/index (useful if you're only controlling Sequences)
	 */
	public Sequence getSequence(String _name) {
		return sequenceMap.get(_name);
	}

	// public float getChildPosition(int _index) {
	// return getChild(_index).getPosition();
	// }
	//
	// public float getChildPosition(String _name) {
	// return getChild(_name).getPosition();
	// }

	/**
	 * Returns all motion objects (Callback, Tween, Parallel, Sequence)
	 */
	public Motion[] getChildren() {
		return children.toArray(new Motion[children.size()]);
	}

	/**
	 * Returns all motion objects as a list(Callback, Tween, Parallel, Sequence)
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
	 * Returns all Parallels
	 */
	public Parallel[] getParallels() {
		return parallels.toArray(new Parallel[parallels.size()]);
	}

	/**
	 * Returns all Parallels as a list
	 */
	public List<Parallel> getParallelList() {
		return parallels;
	}

	/**
	 * Returns all Sequences
	 */
	public Sequence[] getSequences() {
		return sequences.toArray(new Sequence[sequences.size()]);
	}

	/**
	 * Returns all Sequences as a list
	 */
	public List<Sequence> getSequenceList() {
		return sequences;
	}

	/**
	 * Returns the Motion object (Tween, Parallel, Sequence, Callback) by
	 * id/index
	 */
	public Motion get(int _index) {
		if (_index < children.size())
			return children.get(_index);
		else
			return null;
	}

	/**
	 * Returns a motion object (Tween, Parallel, Sequence, Callback) by name
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
	 * Returns child Parallel object count
	 */
	public int getParallelCount() {
		return parallels.size();
	}

	/**
	 * Returns child TweenSequeunce object count
	 */
	public int getSequenceCount() {
		return sequences.size();
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
		insert(_child, 0);
		return this;
	}

	protected Motion insert(Motion _child, float _time) {
		// _child.setDelay(0);
		// _child.setPlayTime(getPlayTime() + _time);
		_child.setPlayTime(_time);

		_child.seek(1);

		_child.setTimeMode(timeMode);

		_child.noAutoUpdate();

		_child.addEventListener(this);

		if (_child.isTween()) {
			tweens.add((Tween) _child);
			if (_child.getName() != null)
				tweenMap.put(_child.getName(), (Tween) _child);
		} else if (_child.isParallel()) {
			parallels.add((Parallel) _child);
			if (_child.getName() != null)
				parallelMap.put(_child.getName(), (Parallel) _child);
		} else if (_child.isSequence()) {
			sequences.add((Sequence) _child);
			if (_child.getName() != null)
				sequenceMap.put(_child.getName(), (Sequence) _child);
		}

		children.add(_child);
		if (_child.getName() != null)
			childrenMap.put(_child.getName(), _child);

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
		} else if (_child.isParallel()) {
			parallels.remove(_child);
			// ParallelLUT.remove(_child.name);
		} else if (_child.isSequence()) {
			sequences.remove(_child);
			// sequenceLUT.remove(_child.name);
		}

		children.remove(_child);
		// childrenLUT.remove(_child.name);

		return this;
	}

	public MotionController addTween(Object _tweenObject,
			String _tweenObjectProperty, float _end, float _duration,
			float _delay, String _easing) {
		return add(new Tween(_tweenObject, _tweenObjectProperty, _end,
				_duration, _delay, _easing));
	}

	public MotionController addTween(Object _tweenObject,
			String _tweenObjectProperty, float _end, float _duration,
			float _delay) {
		return add(new Tween(_tweenObject, _tweenObjectProperty, _end,
				_duration, _delay));
	}

	public MotionController addTween(Object _tweenObject,
			String _tweenObjectProperty, float _end, float _duration) {
		return add(new Tween(_tweenObject, _tweenObjectProperty, _end,
				_duration));
	}

	/**
	 * adds multiple Motion objects
	 */
	public MotionController addAll(Motion[] _children) {
		for (int i = 0; i < _children.length; i++)
			add(_children[i]);

		return this;
	}

	public MotionController addCall(Callback _call) {
		calls.add(_call);

		updateDuration();

		return this;
	}

	/**
	 * Removes all Motion objects
	 */
	public MotionController removeAll() {
		tweens.clear();
		tweenMap.clear();

		parallels.clear();
		parallelMap.clear();

		sequences.clear();
		sequenceMap.clear();

		calls.clear();
		callMap.clear();

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
