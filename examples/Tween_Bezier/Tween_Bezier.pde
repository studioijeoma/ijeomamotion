import ijeoma.geom.bezier.*;
import ijeoma.motion.*;
import ijeoma.motion.tween.bezier.*;

Bezier2D b1, b2;
TweenBezier2D bt;

void setup() {
  size(100, 100);
  smooth();

  b1 = new Bezier2D(this, 85, 20, 10, 10, 90, 90, 15, 80);
  b2 = new Bezier2D(this, 30, 20, 80, 5, 80, 75, 30, 75);

  Motion.setup(this);

  bt = new TweenBezier2D("b1", b1, 0f, 1f, 100f);
  bt.play();
}

void draw() {
  background(255);

  noFill();

  bt.getBezier().draw();

  fill(0);
  ellipse(bt.getX(), bt.getY(), 10, 10);
}

void keyPressed() {
  bt.play();
}

void tweenBezierEnded(TweenBezier2D _tb) {
  if (b1 == _tb.getBezier())
    _tb.setBezier(b2);
  else
    _tb.setBezier(b1);

  _tb.play();
}

