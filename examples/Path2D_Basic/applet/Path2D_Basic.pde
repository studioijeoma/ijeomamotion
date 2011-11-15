/**
 Title: 2D Path 
 
 Author: Ekene Ijeoma
 Date: Febuary 2010    
 Description: Creates a random 2D path 
 Controls:
 SPACE: create random path
 1: linear mode
 2: cosine mode
 3: cubic mode
 4: hermite mode
 */

import ijeoma.geom.path.Path2D;

int pointCount = 5;
float pathBegin, pathEnd, pathLength, pathSegmentLength;

PVector[] points = new PVector[pointCount];

Path2D path;

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

    float y = random(100, 200);
    points[i] = new PVector(x, y, 0);
  }

  points[pointCount - 1] = new PVector(pathEnd, random(100, 200), 0);

  path = new Path2D(this, points);
}

public void draw() {
  background(255);

  path.draw();

  PVector pathPoint = path.getPoint((float) mouseX / width);

  ellipse(pathPoint.x, pathPoint.y, 10, 10);
}

public void keyPressed() {
  if (key == ' ')
    setupPath();
  else if (key == '1')
    path.setMode(Path2D.LINEAR);
  else if (key == '2')
    path.setMode(Path2D.COSINE);
  else if (key == '3')
    path.setMode(Path2D.CUBIC);
  else if (key == '4')
    path.setMode(Path2D.HERMITE);
}

