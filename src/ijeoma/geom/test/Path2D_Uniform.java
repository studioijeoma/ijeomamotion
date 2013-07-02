package ijeoma.geom.test;

import java.util.List;

import ijeoma.geom.Path;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class Path2D_Uniform extends PApplet {
	Path p;

	// Tween t;

	@Override
	public void setup() {
		size(800, 600, P3D);

		Motion.setup(this);

		setupPath();
	}

	public void setupPath() {
		p = new Path();
		p.setMode(Path.HERMITE);

		float x = 0;

		while (x < width) {
			p.add(x, random(200, 400), 0);
			x += 10;
		}

		p.add(x, random(200, 400), 0);
		// p.toUniform(10);

		// t = new Tween(200).addPath(p, 1).play();
	}

	@Override
	public void draw() {
		background(255);
		smooth();

		stroke(0);
		strokeWeight(3);
		noFill();
		// p.draw(g, (float) mouseX / width);
		// p.draw(g, LINES, 1);
		// p.setBias(0.1f);
		// p.setTension(.01f);

		// uniform points
		// noStroke();
		fill(0, 0, 255);
		int c1 = color(255, 0, 0);
		int c2 = color(0, 255, 0);

		if (p.getPointCount() > 2) {
			// // p.simplify(5);
			List<PVector> points = p.getPointList();
			// List<PVector> points = p.getUniformPointList(10);

			noFill();
			beginShape(POINTS);
			for (int i = 0; i < points.size(); i++) {
				PVector p = points.get(i);

				float t = (float) i / points.size();
				int c = lerpColor(c1, c2, t);

				strokeWeight(3);
				stroke(c);
				vertex(p.x, p.y);
			}
			endShape();
		}

		// PVector v = p.getPointAt((float) mouseX / width);
		// noStroke();
		// fill(255, 0, 0);
		// ellipse(v.x, v.y, 10, 10);

		// fill(0);
		// text(t.getPosition() + " " + p.getPosition(), 25, 25);
	}

	public void mousePressed() {
		p.removeAll();
	}

	public void mouseDragged() {
		p.add(mouseX, mouseY, 0);
	}

	public void mouseReleased() {
		// p.toUniform(50);
		p.setPoints(p.getUniformPointList(75));
	}

	@Override
	public void keyPressed() {
		if (key == ' ')
			setupPath();
		else if (key == '1')
			p.setMode(Path.LINEAR);
		else if (key == '2')
			p.setMode(Path.COSINE);
		else if (key == '3')
			p.setMode(Path.CUBIC);
		else if (key == '4')
			p.setMode(Path.HERMITE);
	}
}