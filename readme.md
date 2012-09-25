#Ijeomamotion
 
A library for sketching animations with numbers, colors vectors, beziers, curves and more! It can play tweens in parallel, in a sequence or in a timeline/keyframes. Parallels can playback Sequences and vice versa. Timelines can also playback Parallels and Sequences too! You can also use all of aforementioed to call functions!
Please post issues on [Github](github.com/ekeneijeoma/ijeomamotion/issues) or [Processing's forums](forum.processing.org) under "Contributed Libraries".

#Download and Install
In Processing 2.0 you can do an auto download and install by going Sketch->Add Library...->Animation->ijeomamotion. Otherwise download the latest zip and check out INSTALL.txt.

#Javadocs
http://ekeneijeoma.com/processing/ijeomamotion/reference/index.html

#Getting Started
First, import the library into your Sketch via Sketch->Import Library->ijeomamotion. 
Then call

`Motion.setup(this);`

in setup.

##How to create Tweens

###Numbers (floats)
There are 2 ways to Tween a number. Say you want to tween a `float x = 0` to `x = 100` in 100 frames. To tween a number it MUST BE a float. So if you give it an end value of '100' it must be written as 100.0, 100f or (float)100.
```java
Tween t = new Tween(this, "x", 100f, 100).play();
```
or
```java
Tween t = new Tween(100).add(this, "x", 100f).play();
```

The 2nd way lets you chain/add more properties to the Tween. Say we want to tween a `float x = 0` and `loat y = 0` to `x = 100` and `y = 100` in 100 frames.
```java
Tween t = new Tween(100).add(this, "x", 100f).add(this, "y", 100f).play();
```
 
###Colors (ints)
There are also 2 ways to Tween a color the end value must be an int. Say we want to tween a color `int c = color(0)` to `c = color(255)` in 100 frames.
```java
Tween t = new Tween(this, "c", color(255), 100).play();
```
or
```java
Tween t = new Tween(100).add(this, "c", color(255)).play();
```

In the same was as with numbers you can also chain/add more color properties
```java
Tween t = new Tween(100).add(this, "c1", color(255)).add(this, "c2", color(200)).play();
```

###PVectors
You can also tween PVectors. Say we want to tween PVectors `v1 = PVector(0,0)` and `v2 = PVector(0,0)` to `v1 = PVector(50, 50)` and `v2 = PVector(100, 100)`.
```java
Tween t = new Tween(100).add(v1, new PVector(50, 50)).add(v2, new PVector(100, 100)).play();
```

###All in 1!
You can also tween multiples properties of any type in 1 Tween!
```java
Tween t = new Tween(100).add(this, "x", 100).add(this,"c", color(255)).add(v1, new PVector(100, 100)).play();
```

##How to playback Tweens 
###Delaying
```java
Tween t;
float w = 0;

public void setup() {
  size(400, 100);
  
  Motion.setup(this);

  t = new Tween(this, "w", width, 50, 50).play();
}

public void draw() {
  background(255);

  noStroke();
  fill(255 / 2f);
  rect(0, 0, w, height);
}

public void keyPressed() {
  t.play();
}
```
###Pausing, Resuming "Scrubbing"
```java
Tween t;

float w = 0;

void setup() {
  size(400, 100);
  smooth();

  Motion.setup(this);

  t = new Tween(this, "w", width, 100).play(); 
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(0, 0, w, height); 
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
```
###Repeating
```java
Tween t;

float w = 0;

public void setup() {
  size(400, 100);
  
  Motion.setup(this);

  t = new Tween(this, "w", width, 100f).repeat().play();
}

public void draw() {
  background(255);

  noStroke();
  fill(255 / 2f);
  rect(0, 0, w, height);
}

public void keyPressed() {
  t.play();
}
```

##How to use Processing-style events with Tweens
```java
Tween t;

float w = 0;

void setup() {
  size(400, 100);
  smooth();

  Motion.setup(this);

  t = new Tween(this, "w", width, 100).play();
}

void draw() {
  background(255);

  noStroke();
  fill(255 / 2f);
  rect(0, 0, w, height); 
}

void keyPressed() {
  t.play();
}

void tweenStarted(Tween _t) {
  println(_t + " started");
}

void tweenEnded(Tween _t) {
  println(_t + " ended");
}  
```

##How to use Java-style events with Tweens
```java 
Tween t;

float w = 0;

void setup() {
  size(400, 100);
  smooth();

  Motion.setup(this);

  t = new Tween(this, "w", width, 100).addEventListener(new TweenEventListener()).play();
}

void draw() {
  background(255);

  noStroke();
  fill(255 / 2f);
  rect(0, 0, w, height); 
}

void keyPressed() {
  t.play();
}

public class TweenEventListener implements MotionEventListener {
  void onMotionEvent(MotionEvent te) {
    if (te.type == MotionEvent.TWEEN_STARTED)
      println(((Tween) te.getSource()) + " started");
    else if (te.type == MotionEvent.TWEEN_ENDED)
      println(((Tween) te.getSource()) + " ended"); 
  }
} 

```

##How to call functions with Tweens
Say you want to call a function named `test` at 25 frames in a tween with a duration of a 100 frames.
```java
Tween t;

float w = 0;

public void setup() {
  size(400, 100); 

  Motion.setup(this);

  t = new Tween(100).call(this, "test", 25).play();
}

public void test() {
  println("test");
}

public void draw() {
  background(255);

  String time = t.getTime() + " / " + t.getDuration();

  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}

public void keyPressed() {
  t.play();
}
```

##How to playback tweens in parallel
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
}

public void draw() {
  background(255);

  stroke(255);
  fill(255 / 2f);
  rect(x1, 0, width, height / 2);
  rect(x2, height / 2, width, height / 2);
}
```

##How to playback tweens in a sequence
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

##How to playback tweens in a timeline
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