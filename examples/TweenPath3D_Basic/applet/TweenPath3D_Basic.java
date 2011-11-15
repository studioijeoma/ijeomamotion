import processing.core.*; 
import processing.xml.*; 

import ijeoma.geom.path.*; 
import ijeoma.motion.*; 
import ijeoma.motion.tween.path.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class TweenPath3D_Basic extends PApplet {

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





int pointCount = 5;
float pathBegin, pathEnd, pathLength, pathSegmentLength;

PVector[] points = new PVector[pointCount];

Path3D path;

TweenPath3D tweenPath;

public void setup() {
  size(400, 400, P3D);

  pathBegin = 0;
  pathEnd = width;
  pathLength = pathEnd - pathBegin;
  pathSegmentLength = pathLength / (pointCount - 1);

  setupPath();
  setupPathMotion();
}

public void setupPath() {
  // This creates a 3d path with random points
  for (int i = 0; i < pointCount - 1; i++) {
    float x = pathBegin + pathSegmentLength * i;
    float y = random(-100, 100);
    float z = (i == 0 || i == pointCount - 1) ? 0 : random(-100, 100);

    points[i] = new PVector(x, y, z);
  }

  points[pointCount - 1] = new PVector(pathEnd, random(100, 200),
  random(0, 10));

  // Path3D(PApplet _parent, PVector[] _vertices)
  path = new Path3D(this, points);
}

public void setupPathMotion() {
  Motion.setup(this);

  // TweenPath3D(Path3D _path, float _begin,
  // float _end, float _duration)
  tweenPath = new TweenPath3D(path, 0f, 1f, 300f);
  tweenPath.repeat(MotionConstant.REVERSE);
  tweenPath.play();
}

public void draw() {
  background(255);

  pushMatrix();
  translate(0, height / 2, 0);
  path.draw();

  pushMatrix();
  translate(tweenPath.getX(), tweenPath.getY(), tweenPath.getZ());

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

  if (key == ' ') {
    setupPath();
    tweenPath.setPath(path);
  } 
  else if (key == '1')
    path.setMode(Path2D.LINEAR);
  else if (key == '2')
    path.setMode(Path2D.COSINE);
  else if (key == '3')
    path.setMode(Path2D.CUBIC);
  else if (key == '4')
    path.setMode(Path2D.HERMITE);
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "TweenPath3D_Basic" });
  }
}
