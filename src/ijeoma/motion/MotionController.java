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
import ijeoma.motion.tween.IProperty;
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
		for (Motion c : children)
			if (c.isPlaying)
				c.stop();

		super.stop();

		return this;
	}

	@Override
	public MotionController pause() {
		return (MotionController) super.pause();
	}

	/**
	 * Resumes the tween
	 */
	@Override
	public MotionController resume() {
		return (MotionController) super.resume();
	}

	/**
	 * Changes the time of the tween to a time percentange between 0 and 1
	 */
	@Override
	public MotionController seek(float _value) {
		super.seek(_value);

		for (Motion c : children) {
			if (c.isInsidePlayingTime(getTime()))
				c.seek(getTime() / (c.getDelay() + c.getDuration()));
			else if (c.isAbovePlayingTime(getTime())) {
				c.seek(1);
			} else {
				c.seek(0);
			}
		}

		return this;
	}

	public void update() {
		super.update();

		if (isPlaying)
			updateChildren();
	}

	public void update(float _time) {
		super.update(_time);

		if (isPlaying)
			updateChildren();
	}

	protected void updateChildren() {
		for (Motion c : children)
			c.update(getTime());
	}

	protected void updateDuration() {
		for (Motion c : children)
			duration = PApplet.max(duration, c.getDelay() + c.getDuration());

		for (Callback c : calls)
			duration = PApplet.max(duration, c.getTime() - getDelay());
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
	 * Returns all Tweens for a property as a list
	 */
	public Tween[] getTweens(String propertyName) {
		List<Tween> tweenList = getTweenList(propertyName);
		return tweenList.toArray(new Tween[tweenList.size()]);
	}

	/**
	 * Returns all Tweens for a property as a list
	 */
	public List<Tween> getTweenList(String propertyName) {
		ArrayList<Tween> propertyTweens = new ArrayList<Tween>();
		for (Tween t : tweens)
			if (t.getPropertyNamesList().contains(propertyName))
				propertyTweens.add(t);

		return propertyTweens;
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

	public Motion get(String _name) {
		return childrenMap.get(_name);
	}

	public Motion getChild(int _index) {
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

	private void computeTweens() {
		HashMap<String, Integer> propertyOrderMap = new HashMap<String, Integer>();
		HashMap<String, IProperty> ppropertyMap = new HashMap<String, IProperty>();

		for (Tween t : tweens)
			for (IProperty p : t.getProperties()) {
				String name = p.getObject().getClass().getSimpleName() + "."
						+ p.getName();
				if (propertyOrderMap.containsKey(name)) {
					IProperty pp = ppropertyMap.get(name);

					int order = propertyOrderMap.get(name);
					order++;

					p.setBegin(pp.getEnd());
					p.setOrder(order);

					propertyOrderMap.put(name, order);
					ppropertyMap.put(name, p);
				} else {
					ArrayList<Tween> tweens = new ArrayList<Tween>();
					tweens.add(t);

					p.setBegin();
					p.setOrder(0);

					propertyOrderMap.put(name, 0);
					ppropertyMap.put(name, p);
				}
			}
	}

	protected Motion insert(Motion child, float time) {
		child.delay(time);
		// _child.seek(1);
		child.setTimeMode(timeMode);
		child.noAutoUpdate();
		child.addEventListener(this);

		if (child.isTween()) {
			tweens.add((Tween) child);
			if (child.getName() != null)
				tweenMap.put(child.getName(), (Tween) child);

			computeTweens();
		} else if (child.isParallel()) {
			parallels.add((Parallel) child);
			if (child.getName() != null)
				parallelMap.put(child.getName(), (Parallel) child);
		} else if (child.isSequence()) {
			sequences.add((Sequence) child);
			if (child.getName() != null)
				sequenceMap.put(child.getName(), (Sequence) child);
		}

		children.add(child);
		if (child.getName() != null)
			childrenMap.put(child.getName(), child);

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
