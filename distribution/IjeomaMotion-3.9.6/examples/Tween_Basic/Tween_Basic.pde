import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont font;

Tween t;

void setup() {
  size(400, 100);
  smooth();

  font = createFont("Arial", 12);
  textFont(font);

  Motion.setup(this);

  // Tween(String _name, float _begin, float _end, float _duration);

  // This creates a tween that begins at 0, ends at 400 and plays for 100
  // frames
  t = new Tween("t", 0f, width, 100f);

  t.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(0, 0, t.getPosition(), height);

  fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));

  String time = t.getTime() + " / " + t.getDuration();
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
}

