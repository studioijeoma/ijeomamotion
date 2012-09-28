import ijeoma.motion.*;
import ijeoma.motion.event.*;
import ijeoma.motion.tween.*;

PFont f;

Tween t;

float w = 0;

void setup() {
  size(400, 100);
  smooth();

  Motion.setup(this);

  t = new Tween(this, "w", width, 100).addEventListener(
  new TweenEventListener()).play();
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

public class TweenEventListener implements MotionEventListener {
  void onMotionEvent(MotionEvent te) {
    if (te.type == MotionEvent.TWEEN_STARTED)
      println(((Tween) te.getSource()) + " started");
    else if (te.type == MotionEvent.TWEEN_ENDED)
      println(((Tween) te.getSource()) + " ended");
    // else if (te.type == MotionEvent.TWEEN_CHANGED)
    // println(((Tween) te.getSource()).getName() + " changed");
    else if (te.type == MotionEvent.TWEEN_REPEATED)
      println(((Tween) te.getSource()) + " repeated");
  }
} 

