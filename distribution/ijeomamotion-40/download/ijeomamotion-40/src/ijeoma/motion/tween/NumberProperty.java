/**
is * ijeomamotion
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
 * @modified    10/30/2012
 * @version     4 (40)
 */

package ijeoma.motion.tween;

import java.lang.reflect.Field;

import processing.core.PApplet;

public class NumberProperty implements IProperty {
	protected Object object;
	protected Class<? extends Object> objectType;
	protected Field field;
	protected Class<?> fieldType;

	String name = "";

	protected float begin, end, change;
	protected float position;

	protected float value;

	boolean hasVariable = false;

	public NumberProperty() {

	}

	public NumberProperty(Object _object, String _name, float _end) {
		hasVariable = true;
		setupObjectField(_object, _name);
		setup(_name, _end);
	}

	public NumberProperty(String _name, float _begin, float _end) {
		setup(_name, _begin, _end);
	}

	private void setup(String _name, float _end) {
		name = _name;

		setEnd(_end);

		position = 0;
	}

	private void setup(String _name, float _begin, float _end) {
		name = _name;

		setEnd(_end);
		setBegin(_begin);

		position = 0;
	}

	private void setupObjectField(Object _object, String _objectFieldName) {
		object = _object;
		objectType = object.getClass();

		boolean found = false;

		while (objectType != null) {
			for (Field f : objectType.getDeclaredFields())
				if (f.getName().equals(_objectFieldName)) {
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
				field = objectType.getDeclaredField(_objectFieldName);

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

	public void updateObjectField(Object _object) {
		object = _object;

		setBegin();
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
		if (hasVariable) {
			if (field != null)
				try {
					begin = field.getFloat(object);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			change = end - begin;
		}
	}

	public void setBegin(Object _begin) {
		begin = (Float) _begin;
		change = end - begin;
	}

	public Float getEnd() {
		return end;
	}

	public void setEnd(Object _end) {
		if (hasVariable) {
			if (field != null)
				try {
					begin = field.getFloat(object);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		} else
			begin = value;

		end = (_end instanceof Integer) ? new Float(_end.toString())
				: (Float) _end;
		change = end - begin;
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

	public Float getValue() {
		if (hasVariable) {
			if (field != null)
				try {
					return field.getFloat(object);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			else
				return null;
		} else
			return value;
	}

	@Override
	public void updateValue() {
		value = PApplet.lerp(begin, end, position);

		if (hasVariable)
			if (field != null)
				try {
					field.setFloat(object, value);
					// PApplet.println(name + ".updateValue: " + begin + " - "
					// + getValue() + " - " + end);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
	}

	public Object getObject() {
		return object;
	}

	@Override
	public String toString() {
		return "NumberParameter[name: " + name + ", begin: " + begin
				+ ", end: " + end + ", change: " + change + ", position: "
				+ position + "]";
	}
}
