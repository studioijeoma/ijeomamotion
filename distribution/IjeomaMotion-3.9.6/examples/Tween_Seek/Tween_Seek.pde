import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont f;

Tween t;

void setup() {
  size(400, 100);

  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  t = new Tween("t1", 0, width, 100);
  t.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(0, 0, t.getPosition(), height);

  String time = t.getTime() + " / " + t.getDuration();

  fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
}

void mousePressed() {
  t.pause();
}

void mouseReleased() {
  t.resume();
}

void mouseDragged() {
  t.seek(norm(mouseX, 0, width));
}

