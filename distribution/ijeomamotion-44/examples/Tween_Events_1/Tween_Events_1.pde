import ijeoma.motion.*;
import ijeoma.motion.tween.*;

Tween t;

float w = 0;

void setup() {
  size(400, 100);
  smooth();

  Motion.setup(this);

  t = new Tween(this, "w", width, 100).play();
}

void draw() {
  background(255);

  noStroke();

  fill(255 / 2f);
  rect(0, 0, w, height);

  String time = t.getTime() + " / " + t.getDuration();

  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
}

void tweenStarted(Tween _t) {
  println(_t + " started");
}

void tweenEnded(Tween _t) {
  println(_t + " ended");
}

// void tweenChanged(Tween _t) {
// println(_t + " changed");
// }

void tweenRepeated(Tween _t) {
  println(_t + " repeated");
} 

