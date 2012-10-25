import ijeoma.motion.*;
import ijeoma.motion.tween.*; 

Tween t;
PFont f;

int c1, c2;

void setup() {
  size(400, 400);
  smooth();

  f = createFont("Arial", 12);

  c1 = color(255, 0, 0);
  c2 = color(255, 255, 0);

  Motion.setup(this);

  t = new Tween(100)
    .add(this, "c1", color(random(255), random(255), random(255)))
      .add(this, "c2", color(random(255), random(255), random(255)))
        .setEasing(Tween.SINE_BOTH)
          .play();
}


void draw() {
  background(255);

  for (int i = 0; i <= width; i++) {
    float j = map(i, 0, width, 0, 1);
    int c = lerpColor(c1, c2, j);
    stroke(c);
    line(i, 0, i, height);
  }

  String time = (int)t.getTime() + " / " + (int)t.getDuration();
  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}

void tweenEnded(Tween t) {
  t.get("c1").setEnd(color(random(255), random(255), random(255)));
  t.get("c2").setEnd(color(random(255), random(255), random(255)));
  t.play();
}

void keyPressed() { 
  t.play();
}


