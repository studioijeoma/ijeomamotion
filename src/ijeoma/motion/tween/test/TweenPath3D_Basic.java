package ijeoma.motion.tween.test;

/**
 Title: Tween 3D Path 

 Author: Ekene Ijeoma
 Date: Febuary 2010    
 Description: Creates a random 3D path 
 Controls:
 SPACE: create random path
 1: linear mode
 2: cosine mode
 3: cubic mode
 4: hermite mode
 */

import ijeoma.geom.Path2D;
import ijeoma.geom.Path3D;
import ijeoma.geom.path.*;
import ijeoma.motion.*;
import ijeoma.motion.tween.Path3DTween;
import ijeoma.motion.tween.path.*;
import processing.core.*;

public class TweenPath3D_Basic extends PApplet {
	int pointCount = 5;
	float pBegin, pEnd, pLength, pSegmentLength;

	PVector[] points = new PVector[pointCount];

	Path3D p;

	Path3DTween tp;

	public void setup() {
		size(400, 400, P3D);

		pBegin = 0;
		pEnd = width;
		pLength = pEnd - pBegin;
		pSegmentLength = pLength / (pointCount - 1);

		setupPath();
		setupPathMotion();
	}

	public void setupPath() {
		// This creates a 3d path with random points
		for (int i = 0; i < pointCount - 1; i++) {
			float x = pBegin + pSegmentLength * i;
			float y = random(-100, 100);
			float z = (i == 0 || i == pointCount - 1) ? 0 : random(-100, 100);

			points[i] = new PVector(x, y, z);
		}

		points[pointCount - 1] = new PVector(pEnd, random(100, 200), random(0,
				10));

		// Path3D(PApplet _parent, PVector[] _vertices)
		p = new Path3D(this, points);
	}

	public void setupPathMotion() {
		Motion.setup(this);

		// TweenPath3D(String _name, Path3D _path, float _begin,
		// float _end, float _duration)
		tp = new Path3DTween("tp", p, 0f, 1f, 300f);
		tp.repeat(MotionConstant.REVERSE);
		tp.play();
	}

	public void draw() {
		background(255);

		pushMatrix();
		translate(0, height / 2, 0);
		p.draw();

		pushMatrix();
		translate(tp.getX(), tp.getY(), tp.getZ());

		// The above can also be written as
		// PVector pathPoint = tweenPath.getPoint();
		// translate(pathPoint.x, pathPoint.y, pathPoint.z);

		noStroke();
		fill(0, 0, 0);
		sphere(5);
		popMatrix();

		popMatrix();
	}

	public void keyPressed() {
		// Path3D(PApplet _parent, PVector[] _vertices, String _pathMode)
		// _pathMode is set to CUBIC by default but can also be set to LINEAR,
		// COSINE, HERMITE

		if (key == '1')
			p.setMode(Path2D.LINEAR);
		else if (key == '2')
			p.setMode(Path2D.COSINE);
		else if (key == '3')
			p.setMode(Path2D.CUBIC);
		else if (key == '4')
			p.setMode(Path2D.HERMITE);
		else {
			setupPath();
			setupPathMotion();
		}
	}
}