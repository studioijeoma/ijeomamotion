import ijeoma.geom.Path; 

Path path1;
Path path2;

void setup() {
  size(800, 600, P3D);

  setupPath();
}

void setupPath() {
  path1 = new Path();

  float x = 0;

  while (x < width) {
    path1.add(x, random(250, 400));

    x += random(5, 10);
  }

  path1.add(width, random(200, 400));

  println("path = " + path1.getCount());

  path2 = new Path(path1.get());
  path2.simplify(5, true); 

  println("simplified path = " + path2.getCount());
}

@Override
void draw() {
  background(255);

  stroke(0);
  strokeWeight(3);
  noFill();
  // path1.drawLine(g);
  path1.drawPoints(g);

  noFill();
  stroke(255, 0, 0);
  strokeWeight(2);
  // path2.drawLine(g);
  path2.drawPoints(g);

  PVector p = path2.get((float) mouseX / width);
  noStroke();
  fill(255, 0, 0);
  ellipse(p.x, p.y, 10, 10);
}

void keyPressed() {
  if (key == ' ')
    setupPath();
  else if (key == '1')
    path2.setMode(Path.LINEAR);
  else if (key == '2')
    path2.setMode(Path.COSINE);
  else if (key == '3')
    path2.setMode(Path.CUBIC);
  else if (key == '4')
    path2.setMode(Path.HERMITE);
}

