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

import java.lang.reflect.Field;

import processing.core.PApplet;

public interface IProperty {
	// Object object = new Object();
	// Class objectType;
	// Field field;
	// String fieldName;
	// Class<?> fieldType;
	//
	// Object begin, end, change;
	// Object position;
	//
	// String name = "";

	public String getName();

	public void setName(String _name);

	public Object getBegin();

	public void setBegin(Object _begin);

	public void setEnd(Object _end);

	public Object getEnd();

	public Object getChange();

	public void setChange(Object _change);

	public Object getPosition();

	public void setPosition(Object _position);

	public void updateValue();

	@Override
	public String toString();
}
