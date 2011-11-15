/**
 	  Title: complex 3d tween
 	  
 	  Author: Ekene Ijeoma / Bloom
 	  Date: February 2010  
 	  Description: An object follows a complex path 
 */

import ijeoma.geom.path.*;
import ijeoma.motion.*;
import ijeoma.motion.tween.path.*;

PVector[] points;

Path3D p;
TweenPath3D tp;

int HALF_SIZE = 100;

float[][] path = { { HALF_SIZE, HALF_SIZE, HALF_SIZE },
			{ HALF_SIZE * 1.5f, 0, 0 }, { HALF_SIZE, -HALF_SIZE, -HALF_SIZE },
			{ 0, -HALF_SIZE * 1.5f, 0 }, { -HALF_SIZE, -HALF_SIZE, HALF_SIZE },
			{ -HALF_SIZE * 1.5f, 0, 0 }, { -HALF_SIZE, HALF_SIZE, -HALF_SIZE },
			{ 0, HALF_SIZE * 1.5f, 0 } };

void setup() {
  size(400, 400, P3D);
  smooth();

  points = new PVector[path.length + 1];

  for (int i = 0; i < path.length; i++)
    points[i] = new PVector(path[i][0], path[i][1], path[i][2]);

  points[path.length] = new PVector(path[0][0], path[0][1], path[0][2]);

  p = new Path3D(this, points);

  Motion.setup(this);

  tp = new TweenPath3D("tp", p, 0f, 1f, 300f);
  tp.repeat();
  tp.play();
}

void draw() {
  background(255);

  translate(width / 2, height / 2, 0);
  rotateY(frameCount / 100.0f);
  rotateX(2.0f);
  rotateZ(frameCount / 200.0f);

  // This draws the large box
  noFill();
  stroke(200);
  box(HALF_SIZE * 2);

  // This draws the path
  noFill();
  stroke(100);
  beginShape();
  p.draw();
  endShape();

  // This draws the small black box
  fill(0);
  translate(tp.getX(), tp.getY(), tp.getZ());

  // The above can also be written as
  // PVector pathPoint = tp.getPoint();
  // translate(pathPoint.x, pathPoint.y, pathPoint.z);

  box(20);
}

void keyPressed() {
  // Path3D(PApplet _parent, PVector[] _points, String _pathMode)
  // _pathMode is set to CUBIC by default but can also be set to LINEAR,
  // COSINE, HERMITE

  if (key == '1')
    p.setMode(Path3D.LINEAR);
  else if (key == '2')
    p.setMode(Path3D.COSINE);
  else if (key == '3')
    p.setMode(Path3D.CUBIC);
  else if (key == '4')
    p.setMode(Path3D.HERMITE);
  else
    tp.play();
}

