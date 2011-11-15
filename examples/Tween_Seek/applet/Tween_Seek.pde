import ijeoma.motion.*;
import ijeoma.motion.tween.*;

int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

PFont f;

Tween t;

void setup() {
  size(400, 100);

  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  // Tween(begin, end, duration);
  // This creates a tween that begins at 0, ends at 400, and plays for 100
  // frames
  t = new Tween(0, width, 100);
  t.repeat();
  t.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(0, 0, t.getPosition(), height);

  String timeAsString = t.getTime() + " / " + t.getDuration();

  fill(lerpColor(GREEN, RED, t.getSeekPosition()));
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

void keyPressed() {
  if (key == ' ')
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

