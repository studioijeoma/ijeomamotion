import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont f;

Tween t;
Rect r;

void setup() {
  size(400, 400);
  smooth();

  f = createFont("Arial", 12);

  Motion.setup(this);

  r = new Rect();

  t = new Tween();

  // Tween.addParamter(Object _propertyObject, String _propertyName, float _begin, float _end)
  // A property can also be formatted as an object using the MotionProperty class
  // The propertyObject will either be "this" or a custom object
  // The propertyName/variableName will be name of the variable to be tweened inside the propertyObject

  // This creates a property using the x variable inside the r1 object
  t.addProperty(new Property(r, "x", 0, 400));
  // This creates a property using the x variable inside the r2 object
  t.addProperty(new Property(r, "y", 0, 400));

  t.setDuration(100);
  t.repeat();
  t.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(r.x, r.y, 50, 50);

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

class Rect {
  float x, y;
}

