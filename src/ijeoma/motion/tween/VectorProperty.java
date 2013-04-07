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

import processing.core.PVector;

public class VectorProperty implements IProperty {
	protected String name = "";
	protected PVector vector;
	protected PVector begin, end, change;
	protected float position;

	protected int order = 0;

	// public Property(String _name, float _end) {
	// setup(_name, _end);
	// }

	public VectorProperty(PVector vector, PVector end) {
		this.vector = vector;

		this.begin = vector.get();
		this.end = end;
		this.position = 0;
	}

	public void updateValue() {
		vector.set(PVector.lerp(begin, end, position));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public PVector getBegin() {
		return begin;
	}

	public void setBegin() { 
		begin = vector.get();
		change = PVector.sub(end, begin);
	}

	public void setBegin(Object begin) {
		this.begin = (PVector) begin;
		change = PVector.sub(end, this.begin);
	}

	public PVector getEnd() {
		return end;
	}

	public void setEnd(Object end) {
		begin = vector.get();
		this.end = (PVector) end;

		setChange(PVector.sub(this.end, begin));
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

	public PVector getValue() {
		return vector;
	}

	public Object getObject() {
		return vector;
	}

	public void setOrder(int index) {
		order = index;
	}

	public int getOrder() {
		return order;
	}

	public String toString() {
		return "PVectorParameter[name: " + getName() + ", begin: " + getBegin()
				+ ", end: " + getEnd() + ", change: " + getChange()
				+ ", position: " + getPosition() + "]";
	}
}
