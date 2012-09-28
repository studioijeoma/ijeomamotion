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

package ijeoma.motion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Callback {
	protected Class<? extends Object> objectClass;
	private Object object;
	private Method objectMethod;
	protected Method callbackStartedMethod, callbackEndedMethod;

	float time = 0;
	boolean invoked = false;

	public Callback(Object _object, String _callbackObjectMethodName,
			float _time) {
		setupObject(_object, _callbackObjectMethodName, null);
		time = _time;
	}

	private void setupObject(Object _object, String _objectMethodName,
			String _paramters) {
		object = _object;
		objectClass = _object.getClass();

		boolean found = false;

		for (int i = 0; i < objectClass.getDeclaredMethods().length; i++) {
			if (objectClass.getDeclaredMethods()[i].getName().equals(
					_objectMethodName)) {
				if (objectClass.getDeclaredMethods()[i].getParameterTypes().length == 0) {
					found = true;
					break;
				}
			}
		}

		if (found) {
			try {
				Class<?>[] args = new Class[] {};
				objectMethod = objectClass.getDeclaredMethod(_objectMethodName,
						args);
				objectMethod.setAccessible(true);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}  
	}

	protected void invoke() {
		try {
			objectMethod.invoke(object, null);
			invoked = true;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void noInvoke() {
		invoked = false;
	}

	public boolean hasInvoked() {
		return invoked;
	}

	public float getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "Callback[object: \"" + object.getClass().getSimpleName()
				+ "\", method: \"" + objectMethod.getName() + "\", time: "
				+ time + "]";
	}
}
