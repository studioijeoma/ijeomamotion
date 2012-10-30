import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.*;

Tween t;
PFont f;

int c1, c2;

void setup() {
  size(400, 400);
  smooth();

  f = createFont("Arial", 12);

  c1 = color(255, 0, 0);
  c2 = color(255, 255, 0);

  Motion.setup(this);

  t = new Tween(100)
    .addColor(this, "c1", color(random(255), random(255), random(255)))
      .addColor(this, "c2", color(random(255), random(255), random(255)))
        .play(); 
}

void draw() {
  background(255);

  noStroke();
  fill(c1);
  rect(0, 0, width / 2, height);
  fill(c2);
  rect(width / 2, 0, width / 2, height);

  String time = (int) t.getTime() + " / " + (int) t.getDuration();
  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
}

void tweenEnded(Tween t) {
  t.getColor("c1").setEnd(color(random(255), random(255), random(255)));
  t.getColor("c2").setEnd(color(random(255), random(255), random(255)));
  t.play();
}

