import ijeoma.motion.*;
import ijeoma.motion.tween.*;

Tween t;

float w = 0;

void setup() {
  size(400, 100);
  smooth();

  Motion.setup(this);

  t = new Tween(100).call(this, "test", 50).play();
}

void test() {
  println("test");
}

void draw() {
  background(255);

  noStroke();

  fill(255 / 2f);
  rect(0, 0, w, height);

  String time = (int) t.getTime() + " / " + (int) t.getDuration();

  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
}

