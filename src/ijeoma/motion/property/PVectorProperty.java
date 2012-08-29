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

package ijeoma.motion.property;

import processing.core.PApplet;
import processing.core.PVector;

public class PVectorProperty implements Property {
	protected String name = "";
	protected PVector vector;
	protected PVector begin, end, change;
	protected float position;

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

	@Override
	public void setName(String _name) {
		name = _name;
	}

	public PVector getBegin() {
		return begin;
	}

	public void setBegin(Object _begin) {
		begin = (PVector) _begin;

		setChange(PVector.sub(end, begin));
	}

	public PVector getEnd() {
		return end;
	}

	public void setEnd(Object _end) {
		begin = vector.get();
		end = (PVector) _end;

		setChange(PVector.sub(end, begin));
	}

	public PVector getChange() {
		return change;
	}

	public void setChange(Object _change) {
		change = (PVector) _change;
	}

	public Float getPosition() {
		return position;
	}

	@Override
	public void setPosition(Object _position) {
		position = (Float) _position;

		updateValue();
	}

	public void updateValue() {
		// vector.lerp(end, position);

		float x = PApplet.lerp(vector.x, end.x, position);
		float y = PApplet.lerp(vector.y, end.y, position);
		float z = PApplet.lerp(vector.z, end.z, position);

		vector.set(x, y, z);
	}

	// public void resetValue() {
	// vector = begin.get();
	// }

	public String toString() {
		return "Parameter[name: " + getName() + ", begin: " + getBegin()
				+ ", end: " + getEnd() + ", change: " + getChange()
				+ ", position: " + getPosition() + "]";
	}
}
