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

import java.lang.reflect.Field;

import processing.core.PApplet;
import processing.core.PVector;

public class VectorProperty implements IProperty {
	protected Object object;
	protected Class<? extends Object> objectType;
	protected Field field;
	protected Class<?> fieldType;

	String name = "";

	protected PVector begin, end, change;
	protected float position;

	protected PVector value = new PVector();

	protected int order = 0;

	public VectorProperty() {

	}

	public VectorProperty(Object object, String name, PVector end) {
		setupObject(object, name);
		setup(name, end);
	}

	public VectorProperty(Object object, String name, PVector begin, PVector end) {
		setupObject(object, name);
		setup(name, begin, end);
	}

	public VectorProperty(String name, PVector begin, PVector end) {
		setup(name, begin, end);
	}

	private void setup(String name, PVector end) {
		this.name = name;

		setEnd(end);

		position = 0;
	}

	private void setup(String name, PVector begin, PVector end) {
		this.name = name;

		setEnd(end);
		setBegin(begin);

		position = 0;
	}

	private void setupObject(Object propertyObject, String propertyName) {
		object = propertyObject;
		objectType = object.getClass();

		boolean found = false;

		while (objectType != null) {
			for (Field f : objectType.getDeclaredFields())
				if (f.getName().equals(propertyName)) {
					fieldType = f.getType();
					found = true;
					break;
				}

			if (found)
				break;
			else
				objectType = objectType.getSuperclass();
		}

		if (found)
			try {
				field = objectType.getDeclaredField(propertyName);

				try {
					field.setAccessible(true);
				} catch (java.security.AccessControlException e) {
					e.printStackTrace();
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
	}

	public void updateValue() {
		// if (name.equals("pos2") && (order == 0 && position == 0))
		// PApplet.println(position);

		if ((position > 0 && position <= 1) || (position == 0 && order == 0)) {
			value.set(PVector.lerp(begin, end, position));

			if (field != null)
				try {
					((PVector) field.get(object)).set(value);

					// if (name.equals("pos2")) {
					// PApplet.println(((PVector) field.get(object)));
					//
					// if (value.x == 0 && value.y == 0 && value.z == 0)
					// PApplet.println(((PVector) field.get(object)));
					// }
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
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
		try {
			begin = ((PVector) field.get(object)).get();
			change = PVector.sub(end, begin);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setBegin(Object begin) {
		this.begin = (PVector) begin;
		change = PVector.sub(end, this.begin);
	}

	public PVector getEnd() {
		return end;
	}

	public void setEnd(Object end) {
		if (field != null) {
			try {
				begin = ((PVector) field.get(object)).get();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else
			begin = value;

		this.end = (PVector) end;
		change = PVector.sub(this.end, begin);
		;
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
		if (field != null) {
			try {
				return ((PVector) field.get(object)).get();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		} else
			return value;
	}

	public Object getObject() {
		return object;
	}

	public void setOrder(int index) {
		order = index;
	}

	public int getOrder() {
		return order;
	}

	@Override
	public String toString() {
		return "NumberParameter[name: " + name + ", begin: " + begin
				+ ", end: " + end + ", change: " + change + ", position: "
				+ position + "]";
	}
}