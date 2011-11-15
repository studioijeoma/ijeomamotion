import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween; 

PFont f;

Tween t;

void setup() {
  size(400, 100);

  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  t = new Tween("t", 0, width, 100);
  t.repeat();
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

void tweenStarted(Tween _t) {
  println(_t + " started");
}

void tweenEnded(Tween _t) {
  println(_t + " ended");
}

//  tweenChanged(Tween _t) {
// println(_t + " changed");
// }

void tweenRepeated(Tween _t) {
  println(_t + " repeated");
}
