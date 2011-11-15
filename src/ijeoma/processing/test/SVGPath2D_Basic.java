package ijeoma.processing.test;

import ijeoma.processing.geom.SVGPath2D;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;


public class SVGPath2D_Basic extends PApplet {

	PShape s;
	SVGPath2D p;
//	SVGPath2DTween t;

	public void setup() {
		size(400, 400);
		smooth();

		// s = loadShape("http://www.w3schools.com/svg/path1.svg");
		s = loadShape("http://www.w3schools.com/svg/path2.svg");

		p = new SVGPath2D(g, s.getChild(0));
		// t = new PShapePath2DTween("t", g, s, 0, 1, 100);
	}

	public void draw() {
		background(255);

		noFill();

		fill(0);
		p.draw();

		PVector v = p.getPoint((float) mouseX / width);

		fill(255, 0, 0);
		ellipse(v.x, v.y, 10, 10);
		println();
		// b1.draw();
		// s.shape(s);
	}
}
