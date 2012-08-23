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

package ijeoma.motion.property;

import processing.core.PVector;

public class PVectorProperty extends Property {
	protected PVector vector, begin, end, change;

	// public Property(String _name, float _end) {
	// setup(_name, _end);
	// }

	public PVectorProperty(PVector _vector, PVector _end) {
		vector = _vector;
		begin = _vector.get();
		end = _end;
		// name = _name;
		position = 0;
	}

	@Override
	public String getName() {
		return name;
	}

	public PVector getBegin() {
		return begin;
	}

	public void setBegin(PVector _begin) {
		begin = _begin;

		setChange(PVector.sub(end, begin));
	}

	public void setEnd(PVector _end) {
		end = _end;

		setChange(PVector.sub(end, begin));
	}

	public PVector getEnd() {
		return end;
	}

	public PVector getChange() {
		return change;
	}

	public void setChange(PVector _change) {
		change = _change;
	}

	public float getPosition() {
		return position;
	}

	@Override
	public void setPosition(float _position) {
		position = _position;

		updateValue();
	}

	public void updateValue() {
		vector.lerp(end, position);
	}

	@Override
	public void resetValue() {
		vector = begin.get();
	}

	// public String toString() {
	// return "Parameter[name: " + getName() + ", begin: " + getBegin()
	// + ", end: " + getEnd() + ", change: " + getChange()
	// + ", position: " + getPosition() + "]";
	// }
}
