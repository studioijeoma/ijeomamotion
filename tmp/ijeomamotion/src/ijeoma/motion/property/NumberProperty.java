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

import java.lang.reflect.Field;

import processing.core.PApplet;

public class NumberProperty extends Property {
	protected Object object;
	protected Class<? extends Object> objectType;
	protected Field field;
	protected String fieldName;
	protected Class<?> fieldType;

	protected float begin, end, change;

	// public Property(String _name, float _end) {
	// setup(_name, _end);
	// }

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

	public float getBegin() {
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

	public void setBegin(float _begin) {
		begin = _begin;

		setChange(end - begin);
	}

	public void setEnd(float _end) {
		end = _end;

		setChange(end - begin);
	}

	public float getEnd() {
		return end;
	}

	public float getChange() {
		return change;
	}

	public void setChange(float _change) {
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
	public void resetValue() {
		// field.setFloat(object, PApplet.lerp(begin, end, position));
	}

	@Override
	public String toString() {
		return "Parameter[name: " + getName() + ", begin: " + getBegin()
				+ ", end: " + getEnd() + ", change: " + getChange()
				+ ", position: " + getPosition() + "]";
	}
}
