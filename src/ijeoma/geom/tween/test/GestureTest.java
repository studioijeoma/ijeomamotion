package ijeoma.geom.tween.test;

import ijeoma.geom.*;
import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.*;

public class GestureTest extends PApplet {
	PShape gesturesSVGPaths;
	PShape gestureSVGPath;

	Tween gestureTween;
	Path gesturePath;

	public void setup() {
		size(500, 500);
		gesturesSVGPaths = loadShape("LETTERS2.svg");

		Motion.setup(this);

		gestureSVGPath = gesturesSVGPaths.getChild("a");

		gesturePath = new SVGPath(gestureSVGPath.getChild(0));
		gestureTween = new Tween(100).addPath(gesturePath, 1).repeat().play();
	}

	public void draw() {
		background(255);

		stroke(0);
		noFill();
		// shape(gestureSVGPath, width / 2, height / 2);

		translate(width / 2, height / 2);
		gesturePath.draw(g);
	}

	public void keyReleased() {
		PShape s = gesturesSVGPaths.getChild(str(key));

		if (s != null) {
			gestureSVGPath = s;
			gestureSVGPath.disableStyle();

			gesturePath = new SVGPath(gestureSVGPath.getChild(0));
			gestureTween = new Tween(100).addPath(gesturePath, 1).repeat()
					.play();
		}
	}
}
