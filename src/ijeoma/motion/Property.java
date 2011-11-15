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

package ijeoma.motion;

import java.lang.reflect.Field;

public class Property {
	protected Object object;
	protected Field objectParameter;

	protected String name = "";

	protected float begin, end, change;

	private float position;

	public boolean isVariable = true;

	public Property(String _name) {
		setup(_name, 0, 0);
	}

	public Property(String _name, float _end) {
		setup(_name, 0, _end);
	}

	public Property(String _name, float _begin, float _end) {
		setup(_name, _begin, _end);
	}

	public Property(Object _object, String _name) {
		setupObjectParameter(_object, _name);
		setup(_name, 0, 0);
	}

	public Property(Object _object, String _name, float _end) {
		setupObjectParameter(_object, _name);
		setup(_name, 0, _end);
	}

	public Property(Object _object, String _name, float _begin,
			float _end) {
		setupObjectParameter(_object, _name);
		setup(_name, _begin, _end);
	}

	private void setup(String _name, float _begin, float _end) {
		name = _name;

		setBegin(_begin);
		setEnd(_end);

		position = begin;
	}

	private void setupObjectParameter(Object _object,
			String _objectParameterName) {

		object = _object;

		try {
			objectParameter = _object.getClass().getField(
					_objectParameterName);

			try {
				// if the this.begin hasnt set, set it to
				// this.motionObjectParamters/variables value
				if (begin == 0)
					begin = objectParameter.getFloat(object);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			isVariable = false;

			e.printStackTrace();
		}
	} 

	public String getName() {
		return name;
	}

	public float getBegin() {
		return begin;
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

	public void setPosition(float _position) {
		position = _position;

		if (objectParameter != null)
			try {
				objectParameter.setFloat(object, position);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void setBegin(float _begin) {
		begin = _begin;

		if (objectParameter != null) {
			try {
				objectParameter.setFloat(object, begin);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		setChange(end - begin);
	}

	public void setEnd(float _end) {
		end = _end;

		if (objectParameter != null) {
			try {
				setBegin(objectParameter.getFloat(object));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		setChange(end - begin);
	}

	public void reverse() {
		float b = begin;
		float e = end;

		begin = e;
		end = b;

		change = b - e;
	}
}
