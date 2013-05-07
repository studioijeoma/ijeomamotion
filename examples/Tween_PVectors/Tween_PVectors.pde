import ijeoma.motion.*; 
import ijeoma.motion.tween.*; 

Tween t;
PFont f;

PVector v1, v2;

public void setup() {
  size(400, 400);
  smooth();

  rectMode(CENTER);

  f = createFont("Arial", 12);

  v1 = new PVector(0, 0);
  v2 = new PVector(width, 0);

  Motion.setup(this);

  t = new Tween(100).addPVector(this, "v1", new PVector(width, height))
    .addPVector(this, "v2", new PVector(0, height)).play(); 
}

public void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(v1.x, v1.y, 25, 25);
  rect(v2.x, v2.y, 25, 25);

  fill(0);
  String time = t.getTime() + " / " + t.getDuration();
  text(time, width - textWidth(time) - 10, height - 10);
}

public void keyPressed() {
  t.play();
} 

