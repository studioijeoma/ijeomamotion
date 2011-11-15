import ijeoma.motion.Motion;
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

  // Tween(Object propertyObject, String[] properties, float duration);
  // The propertyObject will either be "this" or a custom object
  // A property string can be formatted as
  // "propertyName/variableName: begin". ex "rect1X: 0" or
  // "propertyName/variableName: begin, end". ex "rect1X: 0, 400"

  // This creates a Tween with 2 properties using variables x and
  // y which are inside "this" which plays for 100 frames
  t = new Tween("t", new String[] { 
    "x: 0, 400", "y: 0, 400"
  }
  , 100);
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

