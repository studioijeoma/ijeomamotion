import ijeoma.motion.*;
import ijeoma.motion.timeline.*;
import ijeoma.motion.tween.*;

PFont font;

Timeline t;

void setup() {
  size(400, 200);

  ellipseMode(CORNER);
  smooth();

  font = createFont("Arial", 36);

  Motion.setup(this);

  t = new Timeline();

  KeyFrame kf1 = new KeyFrame("ONE", 0);
  kf1.addChild(new Tween("t1", -height, height, 100));

  KeyFrame kf2 = new KeyFrame("TWO", 100);
  kf2.addChild(new Tween("t1", -height, height, 100));
  kf2.addChild(new Tween("t2", height, -height, 100));

  KeyFrame kf3 = new KeyFrame("THREE", 200);
  kf3.addChild(new Tween("t1", -height, height, 100));
  kf3.addChild(new Tween("t2", height, -height, 100));
  kf3.addChild(new Tween("t3", -height, height, 100));

  KeyFrame kf4 = new KeyFrame("FOUR", 300);
  kf4.addChild(new Tween("t1", -height, height, 100));
  kf4.addChild(new Tween("t2", height, -height, 100));
  kf4.addChild(new Tween("t3", -height, height, 100));
  kf4.addChild(new Tween("t4", height, -height, 100));

  KeyFrame kf5 = new KeyFrame("FIVE", 400);
  kf5.addChild(new Tween("t1", -height, height, 100));
  kf5.addChild(new Tween("t2", height, -height, 100));
  kf5.addChild(new Tween("t3", -height, height, 100));
  kf5.addChild(new Tween("t4", height, -height, 100));
  kf5.addChild(new Tween("t5", -height, height, 100));

  t.addKeyFrame(kf1);
  t.addKeyFrame(kf2);
  t.addKeyFrame(kf3);
  t.addKeyFrame(kf4);
  t.addKeyFrame(kf5);

  t.repeat();
  t.play();
}

void draw() {
  background(255);

  KeyFrame[] currentKfs = t.getCurrentKeyFrames();

  for (int i = 0; i < currentKfs.length; i++) {
    for (int k = 0; k < currentKfs[i].getTweenCount(); k++) {
      float rectWidth = width / currentKfs[i].getTweenCount();
      float rectY = currentKfs[i].getTween(k).getPosition();

      noStroke();
      fill(0);
      rect(k * rectWidth, rectY, rectWidth, height);
    }

    String kfName = currentKfs[0].getName();

    noStroke();
    fill(255);
    textFont(font, 36);
    text(kfName, width / 2 - textWidth(kfName) / 2, height / 2 + 36 / 2);
  }

  drawGUI();
}

void drawGUI() {
  int seekColor = lerpColor(color(0, 255, 0), color(255, 0, 0),
  t.getSeekPosition());

  stroke(seekColor);
  line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width,
  height);

  String timeAsString = t.getTime() + " / " + t.getDuration();

  fill(seekColor);
  textFont(font, 12);
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

void keyPressed() {
  if (key == '1')
    t.gotoAndPlay("ONE");
  else if (key == '2')
    t.gotoAndPlay("TWO");
  else if (key == '3')
    t.gotoAndPlay("THREE");
  else if (key == '4')
    t.gotoAndPlay("FOUR");
  else if (key == '5')
    t.gotoAndPlay("FIVE");
  else
    t.play();
}

void mousePressed() {
  t.pause();
}

void mouseReleased() {
  t.resume();
}

void mouseDragged() {
  t.seek(norm(mouseX, 0, width));
}
