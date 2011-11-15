import ijeoma.geom.bezier.*;

Bezier2D b1;

void setup() {
  size(100, 100);
  smooth();

  b1 = new Bezier2D(this, 85, 20, 10, 10, 90, 90, 15, 80);
}

void draw() {
  background(255);

  noFill();
  b1.draw();
}

