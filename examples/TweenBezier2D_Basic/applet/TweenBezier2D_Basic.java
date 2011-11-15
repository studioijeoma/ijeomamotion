import processing.core.*; 
import processing.xml.*; 

import processing.core.*; 
import ijeoma.geom.bezier.*; 
import ijeoma.motion.*; 
import ijeoma.motion.tween.bezier.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class TweenBezier2D_Basic extends PApplet {








Bezier2D b1, b2;
TweenBezier2D bt;

public void setup() {
  size(100, 100);
  smooth();

  b1 = new Bezier2D(this, 85, 20, 10, 10, 90, 90, 15, 80);
  b2 = new Bezier2D(this, 30, 20, 80, 5, 80, 75, 30, 75);

  Motion.setup(this);
  bt = new TweenBezier2D(b1, 0f, 1f, 100f);
  bt.play();
}

public void draw() {
  background(255);

  noFill();
  
  bt.getBezier().draw();

  fill(0);
  ellipse(bt.getX(), bt.getY(), 10, 10);
}

public void tweenBezierEnded(TweenBezier2D _tb) {
  if(b1 == _tb.getBezier())
    _tb.setBezier(b2);
  else
    _tb.setBezier(b1);

  _tb.play();
}

public void keyPressed() {
  bt.play();
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "TweenBezier2D_Basic" });
  }
}
