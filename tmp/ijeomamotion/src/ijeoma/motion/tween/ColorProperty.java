/**
 * ijeomamotion
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
 * @modified    09/28/2012
 * @version     4 (26)
 */

package ijeoma.motion.tween;

import ijeoma.motion.Motion;

import java.lang.reflect.Field;

import processing.core.PApplet;

public class ColorProperty implements IProperty {
	protected Object object;
	protected Class<? extends Object> objectType;
	protected Field field;
	protected String fieldName;
	protected Class<?> fieldType;

	String name = "";

	protected int begin, end, change;
	protected float position;

	public ColorProperty() {

	}

	public ColorProperty(Object _object, String _name, int _end) {
		setupObjectField(_object, _name);
		setup(_name, _end);
	}

	private void setup(String _name, int _end) {
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

	public Integer getBegin() {
		return begin;
	}

	public void setBegin() {
		if (field != null) {
			try {
				begin = field.getInt(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		setChange(end - begin);
	}

	public void setBegin(Object _begin) {
		begin = (Integer) _begin;

		setChange(end - begin);
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Object _end) {
		if (field != null) {
			try {
				begin = field.getInt(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		end = (Integer) _end;

		setChange(end - begin);
	}

	public Integer getChange() {
		return change;
	}

	public void setChange(Object _change) {
		change = (Integer) _change;
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
				int c = Motion.getParent().lerpColor(begin, end, position);
				field.setInt(object, c);
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
