package ijeoma.processing.test;

import processing.core.*;

import ijeoma.processing.*;
import ijeoma.processing.geom.Bezier2D;

public class Bezier2D_Basic extends PApplet {

	Bezier2D b1;

	public void setup() {
		size(100, 100);
		smooth();

		b1 = new Bezier2D(this, 85, 20, 10, 10, 90, 90, 15, 80);
	}

	public void draw() {
		background(255);

		noFill();
		b1.draw();
	}
}
