package ijeoma.motion.tween.test;

import java.util.ArrayList;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.IProperty;
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.Parallel;
import processing.core.*;
import processing.opengl.*;

public class Tween_Delays extends PApplet {
	float w = 0;
	float h = 100;

	int boxCount = 10;
	ArrayList<Box> boxes = new ArrayList();

	Parallel tp;

	@Override
	public void setup() {
		size(600, 480, P3D);
		smooth();

		Motion.setup(this);

		setupBoxes();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();
		lights();

		translate(width / 2 - (w * boxCount) / 2, height / 2 - h / 2);

		for (Box b : boxes)
			b.draw();

		String time = tp.getTime() + " / " + tp.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		// tp.play();
		setupBoxes();
	}

	public void setupBoxes() {
		w = (float) width / boxCount;

		boxes = new ArrayList<Box>();

		w = 50;
		h = 100;

		for (int i = 0; i < boxCount; i++)
			boxes.add(new Box("", i * w, 0, w, h));

		tp = new Parallel();

		for (Box b : boxes)
			tp.add(new Tween(100).add(b, "a", random(255)).add(b, "d",
					random(200)));

		tp.play();
	}

	public void tweenParallelEnded(Parallel tp) {
		for (Tween t : tp.getTweens())
			t.get("d").setEnd(random(200));

		tp.play();
	}

	class Box {  
		String name;

		float x, y, z, w, h, d;
		int c;
		float a = 0;

		Box(String name, float x, float y, float w, float h) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.z = 0;
			this.w = w;
			this.h = h;
			this.d = w;
			c = color(random(255), random(255), random(255));
		}

		void draw() {
			noStroke();
			fill(c, a);
			pushMatrix();
			translate(x + w / 2, y + h / 2, z);
			box(w, h, d);
			popMatrix();
		}
	}

}
