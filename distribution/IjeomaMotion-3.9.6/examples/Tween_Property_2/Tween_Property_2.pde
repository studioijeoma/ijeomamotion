import ijeoma.motion.Motion;
import ijeoma.motion.Property;
import ijeoma.motion.tween.Tween;

Tween t;
PFont f;

float x, y;

void setup() {
  size(400, 400);
  smooth();

  rectMode(CENTER);

  f = createFont("Arial", 12);

  Motion.setup(this);

  t = new Tween();

  // Tween.addParamter(Object _paramterObject, String _parameterName, float _begin, float _end)

  // This adds a parameter using the x variable inside "this" PApplet
  // which will tween from 0 to 400
  t.addProperty(new Property(this, "x", 0, 400));
  // This adds a parameter using the y variable inside "this" PApplet
  // which will tween from 0 to 400
  t.addProperty(new Property(this, "y", 0, 400));

  t.setDuration(100);
  t.repeat();
  t.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(x, y, 50, 50);

  int timeColor = lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition());

  stroke(timeColor);
  fill(timeColor);

  line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width, height);

  String time = t.getTime() + " / " + t.getDuration();
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
}

