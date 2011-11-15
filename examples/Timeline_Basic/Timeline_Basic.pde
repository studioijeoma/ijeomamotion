import ijeoma.motion.*;
import ijeoma.motion.timeline.*;
import ijeoma.motion.tween.*;

PFont font;

Timeline tl;

void setup() {
  size(400, 200);
  smooth();

  frameRate(80);

  font = createFont("Arial", 12);
  textFont(font);

  Motion.setup(this);

  tl = new Timeline();

  tl.insertChild(new Tween("t1", -height, height, 100), 0);
  tl.insertChild(new Tween("t2", height, -height, 100), 50);
  tl.insertChild(new Tween("t3", -height, height, 100), 100);
  tl.insertChild(new Tween("t4", height, -height, 100), 150);
  tl.insertChild(new Tween("t5", -height, height, 100), 200);
  tl.repeat();

  tl.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);

  KeyFrame[] currentKfs = tl.getCurrentKeyFrames();
  int[] currentKfIs = tl.getCurrentKeyFrameIndices();

  float rectWidth = width / tl.getKeyFrameCount();

  for (int i = 0; i < currentKfs.length; i++) {
    for (int k = 0; k < currentKfs[i].getTweenCount(); k++) {
      float rectY = currentKfs[i].getTween(k).getPosition();

      noStroke();
      fill(0);
      rect(currentKfIs[i] * rectWidth, rectY, rectWidth, height);
    }
  }

  drawUI();
}

void drawUI() {
  int red = color(255, 0, 0);
  int green = color(0, 255, 0);

  String time;

  stroke(lerpColor(green, red, tl.getSeekPosition()));
  line(tl.getSeekPosition() * width, 0, tl.getSeekPosition() * width,
  height);

  time = (int) tl.getTime() + " / " + (int) tl.getDuration();

  fill(lerpColor(green, red, tl.getSeekPosition()));
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  tl.play();
}

void mousePressed() {
  tl.pause();
}

void mouseReleased() {
  tl.resume();
}

void mouseDragged() {
  tl.seek(norm(mouseX, 0, width));
}

