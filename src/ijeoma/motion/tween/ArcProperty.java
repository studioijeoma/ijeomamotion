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

import processing.core.PApplet;
import processing.core.PVector;

public class ArcProperty implements IProperty {
	protected PVector object;

	protected String name = "";
	protected PVector value;
	protected PVector begin, end, change;

	protected float position;

	public ArcProperty(String _name, PVector _vector, PVector _end) {
		object = _vector;

		name = _name;
		begin = _vector.get();
		end = _end;
		position = 0;
		value = _vector.get();
	}

	public ArcProperty(PVector _vector, PVector _end) {
		object = _vector;

		name = "";
		begin = _vector.get();
		end = _end;
		position = 0;
		value = _vector.get();
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

	public void setBegin() {

	}

	public void setBegin(Object _begin) {
		begin = ((PVector) _begin).get();
		change = PVector.sub(end, begin);
	}

	public PVector getEnd() {
		return end;
	}

	public void setEnd(Object _end) {
		begin = value.get();
		end = ((PVector) _end).get();
		change = PVector.sub(end, begin);
	}

	public PVector getChange() {
		return change.get();
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
		return value.get();
	}

	public void updateValue() {
		PVector mid = new PVector((begin.x + end.x) / 2, (begin.y + end.y) / 2);

		int r = (int) Math.sqrt((begin.x - mid.x) * (begin.x - mid.x)
				+ (begin.y - mid.y) * (begin.y - mid.y));
		float a1 = PApplet.atan2(begin.y - mid.y, begin.x - mid.x);
		float a2 = PApplet.atan2(end.y - mid.y, end.x - mid.x);

		float a = lerpRadians(a1, a2, position);
		float x = (float) (mid.x + Math.cos(a) * r);
		float y = (float) (mid.y + Math.sin(a) * r);

		value.set(x, y, 0);
		object.set(x, y, 0);
	}

	float lerpRadians(float begin, float end, float position) {
		float change = end - begin;
		while (change > Math.PI)
			change -= Math.PI * 2;
		while (change < -Math.PI)
			change += Math.PI * 2;

		return begin + change * position;
	}

	public Object getObject() {
		return value;
	}

	public String toString() {
		return "PVectorParameter[name: " + getName() + ", begin: " + getBegin()
				+ ", end: " + getEnd() + ", change: " + getChange()
				+ ", position: " + getPosition() + "]";
	}
}
