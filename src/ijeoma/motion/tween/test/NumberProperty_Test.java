package ijeoma.motion.tween.test;

import java.lang.reflect.Field;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.NumberProperty;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class NumberProperty_Test extends PApplet {
	Tween t;

	float w = 0;

	NumberProperty np1, np2;

	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		np1 = new NumberProperty(this, "w", 100);
		np2 = new NumberProperty(this, "w", 200);

		println(np1);

		np1.setPosition(1f);
		println(w);
		np2.setBegin();
		println(np2);
		np2.setPosition(1f);
		println(w);

		exit();
	}

	public void draw() {
		background(255);

	}
}
