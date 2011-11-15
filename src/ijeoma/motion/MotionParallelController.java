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

import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.TweenParallel;
import ijeoma.motion.tween.TweenSequence;

public abstract class MotionParallelController extends MotionController {

	public MotionParallelController() {
		super();
	}

	public MotionParallelController(String _name, Motion[] _children) {
		super();
		setName(_name);
		addChildren(_children);
	}

	/**
	 * adds a Motion object
	 */
	public void addChild(Motion _child) {
		// PApplet.println("addChild(" + _child + ")");

		float pt = _child.getPlayTime() + _child.getDelay();

		_child.setPlayTime(pt);
		_child.setDelay(0);
		_child.setTimeMode(timeMode);
		_child.setAutoUpdate(false);
		_child.addEventListener(this);

		if (_child.isTween()) {
			tweens.add((Tween) _child);
			tweenLUT.put(_child.name, (Tween) _child);
		} else if (_child.isTweenParallel()) {
			tweenParallels.add((TweenParallel) _child);
			tweenParallelLUT.put(_child.name, (TweenParallel) _child);
		} else if (_child.isTweenSequence()) {
			tweenSequences.add((TweenSequence) _child);
			tweenSequenceLUT.put(_child.name, (TweenSequence) _child);
		} else if (_child.isCallback()) {
			callbacks.add((Callback) _child);
			callbackLUT.put(_child.name, (Callback) _child);
		}

		children.add(_child);
		childrenLUT.put(_child.name, _child);

		updateDuration();
	}

	/**
	 * adds multiple Motion objects
	 */
	public void addChildren(Motion[] _children) {
		for (int i = 0; i < _children.length; i++)
			addChild(_children[i]);
	}
}
