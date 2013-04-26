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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import processing.core.PApplet;

public class Callback {
	Motion parent;

	protected Class<? extends Object> objectClass;
	private Object object;
	private Method objectMethod;
	private Class<?> parameterClass;

	float time = 0;
	boolean invoked = false;

	public Callback(Motion parent, Object methodObject, String methodName,
			float methodTime) {
		setup(parent, methodObject, methodName);
		time = methodTime;
	}

	public Callback(Motion parent, Object methodObject, String methodName) {
		setup(parent, methodObject, methodName);
		time = -1;
	}

	public Callback(Object methodObject, String methodName, float methodTime) {
		setup(null, methodObject, methodName);
		time = methodTime;
	}

	public Callback(Object methodObject, String methodName) {
		setup(null, methodObject, methodName);
		time = -1;
	}

	public void setup(Motion motionObject, Object methodObject,
			String methodName) {
		boolean foundMethod = false;

		parent = motionObject;

		object = methodObject;
		objectClass = methodObject.getClass();
		objectMethod = null;
		parameterClass = null;

		while (objectClass != null) {
			for (int i = 0; i < objectClass.getDeclaredMethods().length; i++) {
				if (objectClass.getDeclaredMethods()[i].getName().equals(
						methodName)) {
					if (objectClass.getDeclaredMethods()[i].getParameterTypes().length == 1) {
						if (objectClass.getDeclaredMethods()[i]
								.getParameterTypes()[0] == parent.getClass()) {
							parameterClass = parent.getClass();
							foundMethod = true;
							break;
						}
					} else if (objectClass.getDeclaredMethods()[i]
							.getParameterTypes().length == 0) {
						parameterClass = null;
						foundMethod = true;
						break;
					}
				}
			}
			if (foundMethod)
				break;
			else
				objectClass = objectClass.getSuperclass();
		}

		if (foundMethod) {
			try {
				Class<?>[] args = (parameterClass == null) ? new Class[] {}
						: new Class[] { parent.getClass() };
				objectMethod = objectClass.getDeclaredMethod(methodName, args);
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
			Object[] args = (parameterClass == null) ? new Object[] {}
					: new Object[] { parent };
			objectMethod.invoke(object, args);
			invoked = true;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
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
