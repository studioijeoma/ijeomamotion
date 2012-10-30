import ijeoma.motion.*;
import ijeoma.motion.tween.*;

Tween t;
PFont f;

public float x = 0;
public float y = 400;

void setup() {
  size(400, 400);
  smooth();

  rectMode(CENTER);

  f = createFont("Arial", 12);

  Motion.setup(this);
 
  t = new Tween(100).add(this, "x", 400f).add(this, "y", 0f).repeat()
    .play();

  // The above could also be written as
  // t = new Tween(this, "x", 400f, 100).add(this, "y", 0f).repeat().play();
}

void draw() {
  background(255);

  noStroke();

  fill(255 / 2f);
  rect(x, y, 50, 50);

  String time = t.getTime() + " / " + t.getDuration();
  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
} 

