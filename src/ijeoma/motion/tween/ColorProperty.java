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

import ijeoma.motion.Motion;

import java.lang.reflect.Field;

import processing.core.PApplet;

public class ColorProperty implements IProperty {
	protected Object object;
	protected Class<? extends Object> objectType;
	protected Field field;
	protected Class<?> fieldType;

	String name = "";

	protected int begin, end, change;
	protected float position;

	protected int value;

	boolean hasVariable = false;

	public ColorProperty() {

	}

	public ColorProperty(Object _object, String _name, int _end) {
		hasVariable = true;
		setupObjectField(_object, _name);
		setup(_name, _end);
	}

	public ColorProperty(String _name, int _begin, int _end) {
		setup(_name, _begin, _end);
	}

	private void setup(String _name, int _end) {
		name = _name;

		setEnd(_end);

		position = 0;
	}

	private void setup(String _name, int _begin, int _end) {
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

	public Integer getBegin() {
		return begin;
	}

	public void setBegin() {
		if (hasVariable) {
			if (field != null)
				try {
					begin = field.getInt(object);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			change = end - begin;
		}
	}

	public void setBegin(Object _begin) {
		begin = (Integer) _begin;
		change = end - begin;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Object _end) {
		if (hasVariable) {
			if (field != null)
				try {
					begin = field.getInt(object);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		} else
			begin = value;

		end = (Integer) _end;
		change = end - begin;
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

	public Integer getValue() {
		if (hasVariable) {
			if (field != null)
				try {
					return field.getInt(object);
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
		value = Motion.getParent().lerpColor(begin, end, position);

		if (hasVariable)
			if (field != null)
				try {
					field.setInt(object, value);
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
		return "ColorParameter[name: " + name + ", begin: " + begin + ", end: "
				+ end + ", change: " + change + ", position: " + position + "]";
	}
}
