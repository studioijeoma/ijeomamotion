import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont f;

Tween t;
Rect r;


void setup() {
  size(400, 400);
  smooth();

  f = createFont("Arial", 12);

  Motion.setup(this);

  r = new Rect();

  t = new Tween(100).add(r, "x", 400f).add(r, "y", 400f).repeat().play();

  // The above could also be written as
  // t = new Tween(r, "x", 400, 100).add(r, "y", 400).play();
}


void draw() {
  background(255);

  r.draw();

  String time = (int)t.getTime() + " / " + (int)t.getDuration();
  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}


void keyPressed() {
  t.play();
}

class Rect {
  float x, y;

  void draw() {
    noStroke();
    fill(255 / 2f);
    rect(x, y, 50, 50);
  }
}

