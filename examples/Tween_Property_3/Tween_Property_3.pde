import ijeoma.motion.*;
import ijeoma.motion.tween.*;

Tween t;
PFont f;

@Override
void setup() {
  size(400, 400);
  smooth();

  rectMode(CENTER);

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  t = new Tween();

  // Tween.addParamter(String _parameterName, float _begin, float _end)

  // This adds a new MotionParameter with a parameter named rect1X which
  // will tween from 0 to 400
  t.addProperty(new Property("x", 0, 400));
  // This adds a new MotionParameter with a parameter named rect2X which
  // will tween from 0 to 400
  t.addProperty(new Property("y", 0, 400));

  t.setDuration(100);
  t.repeat();
  t.play();
}

@Override
void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(t.getProperty("x").getPosition(), t.getProperty("y").getPosition(), 50, 50);

  // The above line could also be written as
  // rect(t.getPosition("x"), t.getPosition("y"), 0, 50, 50);

  int timeColor = lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition());

  stroke(timeColor);
  fill(timeColor);

  line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width, height);

  String time = t.getTime() + " / " + t.getDuration();
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  if (key == ' ')
    t.play();
}

