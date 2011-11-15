import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import ijeoma.motion.event.*;

PFont f;

Tween t;
TweenEventListener tel;

void setup() {
  size(400, 100);

  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  tel = new TweenEventListener();

  t = new Tween("t", 0, width, 100);
  t.repeat();
  t.addEventListener(tel);
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
  if (key == ' ')
    t.play();
}

class TweenEventListener implements MotionEventListener {
  void onMotionEvent(MotionEvent te) {
    if (te.type == MotionEvent.TWEEN_STARTED)
      println(((Tween) te.getSource()).getName() + " started");
    else if (te.type == MotionEvent.TWEEN_ENDED)
      println(((Tween) te.getSource()).getName() + " ended");
//    else if (te.type == MotionEvent.TWEEN_CHANGED)
//      println(((Tween) te.getSource()).getName() + " changed");
    else if (te.type == MotionEvent.TWEEN_REPEATED)
      println(((Tween) te.getSource()).getName() + " repeated");
  }
}

