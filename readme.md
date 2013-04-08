#Ijeomamotion
 
A cross-mode Processing library for sketching animations with numbers, colors, vectors, beziers, curves and more! Cross-mode means that it runs and exports in both Processing's Java and Javascript modes. You can also use it in Javascript without Processing.js. At the moment ijeomamotion.js doesnt include the Callback class, ijeomamotion.geom and ijeomamotion.math packages! Please post issues on [Github](github.com/ekeneijeoma/ijeomamotion/issues) or [Processing's forums](forum.processing.org) under "Contributed Libraries". 

#Download and Install
In Processing 2.0 you can do an auto download and install by going Sketch->Add Library...->Animation->ijeomamotion. Otherwise download the latest zip and check out INSTALL.txt.

#Javadocs
http://ekeneijeoma.com/processing/ijeomamotion/reference/index.html

#Example
![alt text](http://goo.gl/NZjG8 "Arcs")

#Getting Started 
First, import the library into your Sketch via Sketch->Import Library->ijeomamotion. 
Then call

`Motion.setup(this);`

in setup.

##How to create Tweens

###Numbers (floats)
There are 2 ways to Tween a number using variables. Say you want to tween a `float x = 0` to `x = 100` in 100 frames. (To tween a number it must be a float and not an int. Ints are used to tween colors.)
```java
Tween t = new Tween(this, "x", 100f, 100).play();
```
or
```java
Tween t = new Tween(100).add(this, "x", 100).play();
```

The 2nd way lets you chain/add more properties to the Tween. Say we want to tween a `float x = 0` and `loat y = 0` to `x = 100` and `y = 100` in 100 frames.
```java
Tween t = new Tween(100).add(this, "x", 100).add(this, "y", 100).play();
```
 
###Colors (ints)
There are also 2 ways to Tween a color using variables. Say we want to tween a color `int c = color(0)` to `c = color(255)` in 100 frames. (To tween a color it must be an int.)
```java
Tween t = new Tween(this, "c", color(255), 100).play();
```
or
```java
Tween t = new Tween(100).add(this, "c", color(255)).play();
```

In the same way as with numbers you can also chain/add more color properties
```java
Tween t = new Tween(100).addColor(this, "c1", color(255)).addColor(this, "c2", color(200)).play();
```

###PVectors
You can also tween PVectors. Say we want to tween PVectors `v1 = PVector(0,0)` and `v2 = PVector(0,0)` to `v1 = PVector(50, 50)` and `v2 = PVector(100, 100)`.
```java
Tween t = new Tween(100).addVector(v1, new PVector(50, 50)).addVector(v2, new PVector(100, 100)).play();
```

###All in 1!
You can also tween multiples properties of any type in 1 Tween!
```java
Tween t = new Tween(100).add(this, "x", 100).addColor(this,"c", color(255)).addVector(v1, new PVector(100, 100)).play();
```

###Callbacks
```java
Tween = new Tween(100).add(this, "x", 100).onStart("onStart").onEnd("onEnd").play();

void onStart(){};
void onEnd(){};
```

##How to playback Tweens 
###Delaying
```java
t = new Tween(this, "w", width, 50, 50).play();
```
```java
t = new Tween(50).add(this, "w", width).delay(50).play();
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
Tween t = new Tween(this, "w", width, 100).repeat().play();
```
###Reversing
```java 
Tween t = new Tween(this, "w", width, 100).repeat().reverse().play();
```
##How to use Processing-style events with Tweens 
This runs in PDE Java-mode only but can also be used in JS-mode with Javascript-only Processing.js.
```java
void tweenStarted(Tween _t) {
  println(_t + " started");
}

void tweenEnded(Tween _t) {
  println(_t + " ended");
}  
```
##How to use Java-style events with Tweens (PDE Java-mode only)
```java 
t = new Tween(this, "w", width, 100).addEventListener(new TweenEventListener()).play();

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
t = new Tween(100).call(this, "test", 25).play();

public void test() {
  println("test");
}
```

##How to playback tweens in parallel
```java
Parallel tp = new Parallel()
  .add(new Tween(this, "x1", width, 100), "x1")
  .add(new Tween(this, "x2", -width, 200), "x2").play(); 
```

##How to playback tweens in a sequence
```java
Sequence ts = new Sequence();
  ts.add(new Tween(100).add(this, "x1", width).add(this, "c1", color(0)), "x1");
  ts.add(new Tween(75).add(this,  "x2", width).add(this, "c2", color(0)), "x2");
  ts.add(new Tween(50).add(this,  "x3", width).add(this, "c3", color(0)), "x3");
  ts.add(new Tween(25).add(this,  "x4", width).add(this, "c4", color(0)), "x4");
  ts.repeat().play();
```

##How to playback tweens in a timeline
```java
Timeline tl = new Timeline();
  tl.add(new Tween(50).add(this, "y1",  height).add(this, "c1", color(0)), 0);
  tl.add(new Tween(50).add(this, "y2", -height).add(this, "c2", color(0)), 50);
  tl.add(new Tween(50).add(this, "y3",  height).add(this, "c3", color(0)), 100);
  tl.add(new Tween(50).add(this, "y4", -height).add(this, "c4", color(0)), 150);
  tl.add(new Tween(50).add(this, "y5",  height).add(this, "c5", color(0)), 200);
  tl.repeat().play();
```

###Cross-mode
To create a cross-mode tween using variables they must not be defined in the global scope but rather in a custom class.
```java 
Rect r; 
Tween t; 

public void setup() {
  size(400, 400);
  smooth();

  rectMode(CENTER);

  Motion.setup(this);

  r = new Rect(width / 2, height / 2, 0, 0, 255);
  t = new Tween(100).add(r, "w", width).add(r, "h", height).addColor(r, "c", 0).play();
}

public void draw() {
  background(255);
  r.draw();
} 

class Rect {
  int c;
  float x, y, w, h; 

  Rect(float x, float y, float w, float h, int c) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.c = c;
  } 

  void draw() { 
    noStroke();
    fill(c);
    rect(x, y, w, h);
  }
}

void keyPressed() {
  t.play();
}
```

You can also create cross-mode tweens without variables.
```java 
Tween t;

public void setup() {
  size(400, 400);
  smooth();

  rectMode(CENTER);

  Motion.setup(this);

  t = new Tween(100).add("w", 0, width).add("h", 0, height).addColor("c", 255, 0).play();
}

public void draw() {
  background(255);

  noStroke();
  fill(t.getColor("c").getValue());
  rect(width / 2, height / 2, t.get("w").getValue(), t.get("h").getValue());
}

void keyPressed() {
  t.play();
}

```