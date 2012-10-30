import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont f;

Parallel tp;

float x1 = -width;
float x2 = width;

public void setup() {
  size(400, 200);

  ellipseMode(CORNER);
  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  tp = new Parallel();
  tp.add(new Tween("x1", this, "x1", width, 50))
    .add(new Tween("x2", this, "x2", -width, 100)).play();
}

public void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(x1, 0, width, height / 2);
  rect(x2, height / 2, width, height / 2);

  //  int count = 10;
  //  float h = (float) height / count; 
  //
  //  for (int i = 0; i < count; i++) {
  //    fill((float) ((i%2==0)?:255)-/count * 255);
  //    rect((i%2==0)?x1:x2, h*i, width, h);
  //  }   

  String time;

  time = (int) tp.get("x1").getTime() + " / "
    + (int) tp.get("x1").getDuration();

  fill(tp.get("x1").isPlaying() ? color(0, 255, 0) : color(255, 0, 0));
  text(time, 10, 10 + 12);

  time = (int) tp.getTween("x2").getTime() + " / "
    + (int) tp.getTween("x2").getDuration();

  fill(tp.get("x2").isPlaying() ? color(0, 255, 0) : color(255, 0, 0));
  text(time, 10, 100 + 10 + 12);

  time = (int) tp.getTime() + " / " + (int) tp.getDuration();

  fill(tp.isPlaying() ? color(0, 255, 0) : color(255, 0, 0));
  text(time, width - textWidth(time) - 10, height - 10);
}

public void keyPressed() {
  tp.play();
}

public void mousePressed() {
  tp.pause();
}

public void mouseReleased() {
  tp.resume();
}

public void mouseDragged() {
  tp.seek((float) mouseX / width);
} 

