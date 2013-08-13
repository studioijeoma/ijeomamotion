/**
 * ijeomamotion
 * A cross-mode Processing library for sketching animations with numbers, colors vectors, beziers, curves and more. 
 * http://ekeneijeoma.com/processing/ijeomamotion
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
 * @modified    05/13/2013
 * @version     5.4.1 (54)
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

	protected int value = 0;

	protected int order = 0;

	public ColorProperty() {

	}

	public ColorProperty(Object object, String name, int end) {
		setupObject(object, name);
		setup(name, end);
	}

	public ColorProperty(String name, int begin, int end) {
		setup(name, begin, end);
	}

	private void setup(String name, int end) {
		this.name = name;

		setEnd(end);

		position = 0;
	}

	private void setup(String name, int begin, int end) {
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

				// setupValue();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
	}
	
	public String getId() {
		if (field == null)
			return name;
		else
			return System.identityHashCode(object) + "_" + name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin() {
		try {
			begin = field.getInt(object);
			change = end - begin;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
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
		if (field != null) {
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
		if (field != null) {
			try {
				return field.getInt(object);
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

	public void updateValue() {
		if ((position > 0 && position <= 1) || (position == 0 && order == 0)) {
			value = Motion.getParent().lerpColor(begin, end, position);

			if (field != null)
				try {
					field.setInt(object, value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		}
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
		return "ColorParameter[name: " + name + ", begin: " + begin + ", end: "
				+ end + ", change: " + change + ", position: " + position + "]";
	}
}
