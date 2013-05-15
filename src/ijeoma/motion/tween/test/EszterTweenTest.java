package ijeoma.motion.tween.test;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.PApplet;
import processing.core.PVector;

public class EszterTweenTest extends PApplet {
	Sequence ts;

	PVector pos = new PVector(100, 100);
	PVector pos2 = new PVector(200, 200);

	public void setup() {
		size(500, 500);
		Motion.setup(this);
		makeTween();
	}

	public void draw() {
		background(0);
		fill(255);
		// println(pos);
		rect(pos.x, pos.y, 100, 100);
		rect(pos2.x, pos2.y, 50, 50);
	}

	public void keyPressed() { 
		ts.play();
	}

	void makeTween() {
		ts = new Sequence();
		ts.add(new Tween(50).addVector(this, "pos", new PVector(width / 2,
				height / 2)));
		ts.add(new Tween(50).addVector(this, "pos", new PVector(0, 0)));
		ts.add(new Tween(100).addVector(this, "pos2", new PVector(0, 0)));

		VectorProperty p1 = ts.getTween(0).getVector(0);
		VectorProperty p2 = ts.getTween(1).getVector(0);
		VectorProperty p3 = ts.getTween(2).getVector(0);

		println(p1);
		println(p2);
		println(p3);

	}

}
