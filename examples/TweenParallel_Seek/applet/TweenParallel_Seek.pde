/**
 	  Author: Ekene Ijeoma
 	  Date: October 2010   
 */

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont f;

TweenParallel tp;

void setup() {
  size(400, 200);

  ellipseMode(CORNER);
  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  tp = new TweenParallel();
  tp.addChild(new Tween("t1", -width, width, 100));
  tp.addChild(new Tween("t2", width, -width, 100));
  tp.play();
}

void draw() {
  background(255);

  stroke(255);
  fill(0);
  rect(tp.getTween("t1").getPosition(), 0, width, height / 2);
  rect(tp.getTween("t2").getPosition(), height / 2, width, height / 2);

  drawUI();
}

void drawUI() {
  int red = color(255, 0, 0);
  int green = color(0, 255, 0);

  String time;

  stroke(lerpColor(green, red, tp.getSeekPosition()));
  line(tp.getSeekPosition() * width, 0, tp.getSeekPosition() * width,
  height);

  for (int i = 0; i < tp.getChildCount(); i++) {
    Tween t = tp.getTween(i);
    time = (int) t.getTime() + " / " + (int) t.getDuration();

    fill(lerpColor(green, red, t.getSeekPosition()));
    text(time, 10, i * 100 + 10 + 12);
  }

  time = (int) tp.getTime() + " / " + (int) tp.getDuration();

  fill(lerpColor(green, red, tp.getSeekPosition()));
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  tp.play();
}

void mousePressed() {
  tp.pause();
}

void mouseReleased() {
  tp.resume();
}

void mouseDragged() {
  tp.seek((float) mouseX / width);
}

