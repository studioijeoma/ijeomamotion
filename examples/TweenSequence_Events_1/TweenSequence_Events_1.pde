import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont font;

TweenSequence ts;

void setup() {
  size(400, 400);

  smooth();

  font = createFont("Arial", 12);
  textFont(font);

  Motion.setup(this);

  ts = new TweenSequence();
  ts.setName("ts");
  ts.appendChild(new Tween("t1", -width, width, 100));
  ts.appendChild(new Tween("t2", -width, width, 75));
  ts.appendChild(new Tween("t3", -width, width, 50));
  ts.appendChild(new Tween("t4", -width, width, 25));
  
  //if you set the tween sequence to repeat the end event is never sent unless you call stop()
  ts.repeat();
  ts.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);

  for (int i = 0; i < ts.getChildCount(); i++)
    rect(ts.getTween(i).getPosition(), i * 100, width, 100);

  drawUI();
}

void drawUI() {
  String time;

  // This draws the seek for the ts TweenParallel object
  stroke(lerpColor(0xFF00FF00, 0xFFFF0000, ts.getSeekPosition()));
  line(ts.getSeekPosition() * width, 0, ts.getSeekPosition() * width,
  height);

  // This draws the time for every Tween object
  for (int i = 0; i < ts.getChildCount(); i++) {
    Tween t = ts.getTween(i);
    time = (int) t.getTime() + " / " + (int) t.getDuration();

    fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));
    text(time, 10, i * 100 + 10 + 12);
  }

  // This draws the time for the ts TweenSequence object
  time = (int) ts.getTime() + " / " + (int) ts.getDuration();

  fill(lerpColor(0xFF00FF00, 0xFFFF0000, ts.getSeekPosition()));
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  ts.play();
}

void tweenSequenceStarted(TweenSequence _ts) {
  println(_ts + " started");
}

void tweenSequenceEnded(TweenSequence _ts) {
  println(_ts + " ended");
}

// void tweenSequenceChanged(TweenSequence _ts) {
// println(_ts + " changed");
// }

void tweenSequenceRepeated(TweenSequence _ts) {
  println(_ts + " repeated");
}

