import ijeoma.motion.*;
import ijeoma.motion.tween.*; 

PFont font;

int c1, c2, c3, c4;
float x1, x2, x3, x4;
Sequence ts;

void setup() {
  size(400, 400);

  smooth();

  font = createFont("Arial", 12);
  textFont(font);

  Motion.setup(this);

  c1 = c2 = c3 = c4 = color(255);
  x1 = x2 = x3 = x4 = -width;

  ts = new Sequence();
  ts.add(new Tween("x1", 100).add(this, "x1", (float)width).addColor(this, "c1", 
  color(0)));
  ts.add(new Tween("x2", 75).add(this, "x2", (float)width).addColor(this, "c2", 
  color(0)));
  ts.add(new Tween("x3", 50).add(this, "x3", (float)width).addColor(this, "c3", 
  color(0)));
  ts.add(new Tween("x4", 25).add(this, "x4", (float)width).addColor(this, "c4", 
  color(0)));
  ts.reverse().repeat().play();
}

void draw() {
  background(255);

  noStroke();

  fill(c1);
  rect(x1, 0, width, 100);
  fill(c2);
  rect(x2, 100, width, 100);
  fill(c3);
  rect(x3, 200, width, 100);
  fill(c4);
  rect(x4, 300, width, 100);

  fill(0);

  String time = (int) ts.get("x1").getTime() + " / "
    + (int) ts.get("x1").getDuration();
  fill(ts.get("x1").isPlaying() ? color(0, 255, 0) : color(255, 0, 0));
  text(time, 10, 10 + 12);

  time = (int) ts.get("x2").getTime() + " / "
    + (int) ts.get("x2").getDuration();
  fill(ts.get("x2").isPlaying() ? color(0, 255, 0) : color(255, 0, 0));
  text(time, 10, 100 + 10 + 12);

  time = (int) ts.get("x3").getTime() + " / "
    + (int) ts.get("x3").getDuration();
  fill(ts.get("x3").isPlaying() ? color(0, 255, 0) : color(255, 0, 0));
  text(time, 10, 200 + 10 + 12);

  time = (int) ts.get("x4").getTime() + " / "
    + (int) ts.get("x4").getDuration();
  fill(ts.get("x4").isPlaying() ? color(0, 255, 0) : color(255, 0, 0));

  text(time, 10, 300 + 10 + 12);

  time = (int) ts.getTime() + " / " + (int) ts.getDuration();
  fill(ts.isPlaying() ? color(0, 255, 0) : color(255, 0, 0));
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  ts.play();
}

void mousePressed() {
  ts.pause();
}

void mouseReleased() {
  ts.resume();
}

void mouseDragged() {
  ts.seek((float) mouseX / width);
} 

