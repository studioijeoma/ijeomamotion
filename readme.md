#Ijeomamotion
 
A Processing library for sketching flash-like animations. Read more at ekeneijeoma.com/processing/ijeomamotion.

#Install
Unzip and put the extracted ijeomamotion folder into the libraries folder of your Processing Sketches. Reference and examples are included in the ijeomamotion folder.

#Getting Started
First import the library into your Sketch via Sketch->Import Library->ijeomamotion.
Then call

`Motion.setup(this);`

in setup. After you can try out the examples below. Please also check the examples in the download.

#Examples

Sketch a basic tween animation:

```java
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
}
```

Sketch a basic parallel animation:

```java
Parallel tp;

float x1, x2;

public void setup() {
  size(400, 200);
 
  Motion.setup(this);

  x1 = -width;
  x2 = width;
  
  tp = new Parallel();
  tp.add(new Tween(this, "x1", width, 100), "x1")
    .add(new Tween(this, "x2", -width, 200), "x2").play();

  // The above could also be written as...
  // tp.addTween(this, "x1", width, 100).addTween(this, "x2", -width,
  // 200).play(); 
}

public void draw() {
  background(255);

  stroke(255);
  fill(255 / 2f);
  rect(x1, 0, width, height / 2);
  rect(x2, height / 2, width, height / 2);
}
```

Sketch a basic sequence animation:

```java
int c1, c2, c3, c4;
float x1, x2, x3, x4;
Sequence ts;

void setup() {
  size(400, 400);

  Motion.setup(this);

  c1 = c2 = c3 = c4 = color(255);
  x1 = x2 = x3 = x4 = -width;

  ts = new Sequence();
  ts.add(new Tween(100).add(this, "x1", (float)width).
  add(this, "c1", color(0)), "x1");
  ts.add(new Tween(75).add(this, "x2", (float)width).
  add(this, "c2", color(0)), "x2");
  ts.add(new Tween(50).add(this, "x3", (float)width).
  add(this, "c3", color(0)), "x3");
  ts.add(new Tween(25).add(this, "x4", (float)width).
  add(this, "c4", color(0)), "x4");
  ts.repeat().play();
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
}
```

Sketch a basic timeline animation:

```java
int c1, c2, c3, c4, c5;
float y1, y2, y3, y4, y5;

Timeline tl;

void setup() {
  size(400, 200);
 
  Motion.setup(this);

  c1 = c2 = c3 = c4 = c5 = color(255);

  y1 = y3 = y5 = -height;
  y2 = y4 = height;

  tl = new Timeline();
  tl.add(new Tween(50).add(this, "y1", (float)height).
  add(this, "c1", color(0)), 0);
  tl.add(new Tween(50).add(this, "y2", (float)-height).
  add(this, "c2", color(0)), 50);
  tl.add(new Tween(50).add(this, "y3", (float)height).
  add(this, "c3", color(0)), 100);
  tl.add(new Tween(50).add(this, "y4", (float)-height).
  add(this, "c4", color(0)), 150);
  tl.add(new Tween(50).add(this, "y5", (float)height).
  add(this, "c5", color(0)), 200);
  tl.repeat().play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);

  noStroke();
  fill(c1);
  rect(0, y1, 80, height);
  fill(c2);
  rect(80, y2, 80, height);
  fill(c3);
  rect(160, y3, 80, height);
  fill(c4);
  rect(240, y4, 80, height);
  fill(c5);
  rect(320, y5, 80, height); 
}
```
