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

public class NumberProperty implements IProperty {
	protected Object object;
	protected Class<? extends Object> objectType;
	protected Field field;
	protected String fieldName;
	protected Class<?> fieldType;

	String name = "";

	protected float begin, end, change;
	protected float position;

	public NumberProperty() {

	}

	public NumberProperty(Object _object, String _name, float _end) {
		setupObjectField(_object, _name);
		setup(_name, _end);
	}

	private void setup(String _name, float _end) {
		name = _name;

		setEnd(_end);

		position = 0;
	}

	private void setupObjectField(Object _object, String _objectFieldName) {
		object = _object;
		objectType = object.getClass();

		fieldName = _objectFieldName;

		boolean found = false;

		while (objectType != null) {
			for (Field f : objectType.getDeclaredFields())
				if (f.getName().equals(fieldName)) {
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
				field = objectType.getDeclaredField(fieldName);

				try {
					field.setAccessible(true);
				} catch (java.security.AccessControlException e) {
					e.printStackTrace();
				}

				setBegin();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
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

	public Float getBegin() {
		return begin;
	}

	public void setBegin() {
		if (field != null) {
			try {
				begin = field.getFloat(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		setChange(end - begin);
	}

	public void setBegin(Object _begin) {
		begin = (Float) _begin;

		setChange(end - begin);
	}

	public Float getEnd() {
		return end;
	}

	public void setEnd(Object _end) {
		if (field != null) {
			try {
				begin = field.getFloat(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		end = (Float) _end;

		setChange(end - begin);
	}

	public Float getChange() {
		return change;
	}

	public void setChange(Object _change) {
		change = (Float) _change;
	}

	public Float getPosition() {
		return position;
	}

	@Override
	public void setPosition(Object _position) {
		position = (Float) _position;

		updateValue();
	}

	@Override
	public void updateValue() {
		if (field != null)
			try {
				field.setFloat(object, PApplet.lerp(begin, end, position));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
	}

	@Override
	public String toString() {
		return "Parameter[name: " + getName() + ", begin: " + getBegin()
				+ ", end: " + getEnd() + ", change: " + getChange()
				+ ", position: " + getPosition() + "]";
	}
}
