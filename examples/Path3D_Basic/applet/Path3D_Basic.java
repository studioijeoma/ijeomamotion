import processing.core.*; 
import processing.xml.*; 

import ijeoma.geom.path.Path3D; 

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

public class Path3D_Basic extends PApplet {

/**
 * Title: 3D Path 
 * 
 * Author: Ekene Ijeoma
 * Date: Febuary 2010    
 * Description: Creates a random 3D path 
 * Controls:
 * SPACE: create random path
 * 1: linear mode
 * 2: cosine mode
 * 3: cubic mode
 * 4: hermite mode
 */



int pointCount = 5;
float pathBegin, pathEnd, pathLength, pathSegmentLength;

PVector[] points = new PVector[pointCount];

Path3D path;

public void setup() {
  size(300, 300, P3D);

  pathBegin = 0;
  pathEnd = width;
  pathLength = pathEnd - pathBegin;
  pathSegmentLength = pathLength / (pointCount - 1);

  setupPath();
}

public void setupPath() {
  for (int i = 0; i < pointCount - 1; i++) {
    float x = pathBegin + pathSegmentLength * i;
    float y = random(-75, 75);

    float z;

    if (i == 0 || i == pointCount - 1)
      z = 0;
    else
      z = random(-100, 100);

    points[i] = new PVector(x, y, z);
  }

  points[pointCount - 1] = new PVector(pathEnd, random(100, 200), random(0, 10));

  path = new Path3D(this, points);
}

public void draw() {
  background(255);

  pushMatrix();
  translate(0, height / 2, 0);
  path.draw();

  PVector pathPoint = path.getPoint((float) mouseX / width);

  pushMatrix();
  translate(pathPoint.x, pathPoint.y, pathPoint.z);
  noStroke();
  fill(0, 0, 0);
  sphere(5);
  popMatrix();

  popMatrix();
}

public void keyPressed() {
  if (key == ' ')
    setupPath();
  else if (key == '1')
    path.setMode(Path3D.LINEAR);
  else if (key == '2')
    path.setMode(Path3D.COSINE);
  else if (key == '3')
    path.setMode(Path3D.CUBIC);
  else if (key == '4')
    path.setMode(Path3D.HERMITE);
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "Path3D_Basic" });
  }
}
