import ijeoma.motion.*;
import ijeoma.motion.tween.*;

Tween t;

float w = 0;

void setup() {
  size(400, 100);
  smooth();

  Motion.setup(this);

  t = new Tween(this, "w", width, 100).play();

  // The above could also be written as
  // t = new Tween(100).add(this, "w", width).play();
}

void draw() {
  background(255);
 
  noStroke();

  fill(255 / 2f);
  rect(0, 0, w, height);

  String time = (int)t.getTime() + " / " + (int)t.getDuration();
//
//  fill(0);
//  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
}

void mousePressed() {
  t.pause();
}

void mouseReleased() {
  t.resume();
}

void mouseDragged() {
  t.seek((float) mouseX / width);
} 



