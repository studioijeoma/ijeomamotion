package ijeoma.motion.tween.test;

import java.lang.reflect.Field;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Property_Test extends PApplet {
	Tween t;

	float w = 0;

	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		String fieldName = "w";

		Object object1 = this;
		Class objectType1 = object1.getClass();
		Field field1 = null;

		Object object2 = this;
		Class objectType2 = object1.getClass();
		Field field2 = null;

		try {
			field1 = objectType1.getDeclaredField(fieldName);
			field2 = objectType2.getDeclaredField(fieldName);
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}

		// try {
		// println(field1 + " = " + field1.get(object1));
		// field1.setFloat(object1, 100);
		// } catch (IllegalArgumentException e) {
		// } catch (IllegalAccessException e) {
		// }
		//
		// println("w = " + w);
		//
		// try {
		// println(field2 + " = " + field2.get(object2));
		// field2.setFloat(object2, 200);
		// } catch (IllegalArgumentException e) {
		// } catch (IllegalAccessException e) {
		// }
		//

		// println("w = " + w);
		w = 200;
		
		try {
			println(field1 + " = " + field1.get(object1));
			println(field2 + " = " + field2.get(object2));
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}

	}

	public void draw() {
		background(255);

	}
}
