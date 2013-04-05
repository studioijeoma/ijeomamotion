import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween; 

Tween t;

float w = 0;

public void setup() {
  size(400, 100);
  smooth();

  Motion.setup(this);

  t = new Tween(100).add(this, "w", width).onStart("onStart")
    .onEnd("onEnd").play();
}

public void onStart(Tween t) {
  println(t+" started");
}

public void onEnd(Tween t) {
  println(t+" ended");
}
 
public void draw() {
  background(255);

  noStroke();
  fill(255 / 2f);
  rect(0, 0, w, height);

  String time = (int) t.getTime() + " / " + (int) t.getDuration();

  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}
 
public void keyPressed() {
  t.play();
}

