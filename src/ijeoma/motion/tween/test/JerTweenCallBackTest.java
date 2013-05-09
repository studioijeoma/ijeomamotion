package ijeoma.motion.tween.test;

import java.util.ArrayList;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class JerTweenCallBackTest extends PApplet {
	ArrayList<Cube> cubes = new ArrayList<Cube>();
	Tween t;

	public void setup() {
		size(1000, 1000, P3D);
		Motion.setup(this);
		// Works
		// addCubes();

		// Weird
		// t = new Tween(100).onEnd(this, "addCubes").play();
		t = new Tween(25, 25).onEnd(this, "addCubes").play();
	}

	public void draw() {
		background(0);
		text(t.getTime() + "/" + t.getDuration(), 25, 25);

		translate(width / 2, height / 2);

		int i = 0;
		for (Cube c : cubes) {
			c.render();

			// fill(255);
			// text(c.t.getTime() + "/" + c.t.getDuration(), 25, 25 * i);

			i++;
		}

	}

	public void keyPressed() {
		t = new Tween(25, 25).onEnd(this, "addCubes").play();
		// if (key == '1')
		// t = new Tween(100).onEnd(this, "addCubes").play();
		// else if (key == '2')
		// addCubes();

	}

	void addCubes() {
		println("addCubes");
		cubes.clear();
		for (int i = 0; i < 100; i++) {
			Cube c = new Cube();
			PVector t = new PVector((i % 10 * 20) - 100,
					(floor(i / 10) * 20) - 100);

			// String name = t.x + "-" + t.y;
			c.t = new Tween("t" + i, 100).addVector(c, "p", t).play();

			cubes.add(c);
		}
	}

	class Cube {
		PVector p = new PVector();
		Tween t = new Tween();

		void render() {
			pushMatrix();
			noStroke();
			translate(p.x, p.y, p.z);
			box(10);
			popMatrix();
		}
	}
}
