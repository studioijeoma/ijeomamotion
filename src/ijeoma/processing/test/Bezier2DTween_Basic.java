package ijeoma.processing.test;

import processing.core.*;

import ijeoma.motion.*; 
import ijeoma.processing.geom.Bezier2D;
import ijeoma.processing.tween.Bezier2DTween;

public class Bezier2DTween_Basic extends PApplet {

	Bezier2D b1, b2;
	Bezier2DTween bt;

	public void setup() {
		size(100, 100);
		smooth();

		b1 = new Bezier2D(this, 85, 20, 10, 10, 90, 90, 15, 80);
		b2 = new Bezier2D(this, 30, 20, 80, 5, 80, 75, 30, 75);

		Motion.setup(this);
		
		bt = new Bezier2DTween("b1", b1, 0f, 1f, 100f);
		bt.play();
	}

	public void draw() {
		background(255);

		noFill();

		bt.getBezier().draw();

		fill(0);
		ellipse(bt.getX(), bt.getY(), 10, 10);
	}
	
	public void keyPressed() {
		bt.play();
	}

	public void tweenBezierEnded(Bezier2DTween _tb) {
		if (b1 == _tb.getBezier())
			_tb.setBezier(b2);
		else
			_tb.setBezier(b1);

		_tb.play();
	}
}
