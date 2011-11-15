import ijeoma.motion.*;
import ijeoma.motion.tween.*;

int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

PFont font;

Tween t;

public float x, y;

void setup() {
  size(400, 400);
  smooth();

  x = width/2;
  y = height/2;

  Motion.setup(this);

  t = new Tween();
  t.addParameter(new Parameter(this, "x"));
  t.addParameter(new Parameter(this, "y"));
  t.setDuration(50);
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  ellipse(x, y, 50, 50);

  int seekColor = lerpColor(GREEN, RED, t.getSeekPosition());

  stroke(seekColor);
  line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width, height);

  String timeAsString = t.getTime() + " / " + t.getDuration();

  noStroke();
  fill(seekColor);
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

void mousePressed() {
  t.getParameter("x").setEnd(mouseX);
  t.getParameter("y").setEnd(mouseY);
  t.play();
}

