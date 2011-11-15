import processing.core.*; 
import processing.xml.*; 

import ijeoma.motion.*; 
import ijeoma.motion.tween.*; 

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

public class TweenParallel_Seek extends PApplet {

/**
 	  Author: Ekene Ijeoma
 	  Date: October 2010   
 */




PFont f;

TweenParallel tp;

public void setup() {
  size(400, 200);

  ellipseMode(CORNER);
  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  tp = new TweenParallel();
  tp.addChild(new Tween("t1", -width, width, 100));
  tp.addChild(new Tween("t2", width, -width, 100));
  tp.play();
}

public void draw() {
  background(255);

  stroke(255);
  fill(0);
  rect(tp.getTween("t1").getPosition(), 0, width, height / 2);
  rect(tp.getTween("t2").getPosition(), height / 2, width, height / 2);

  drawUI();
}

public void drawUI() {
  int red = color(255, 0, 0);
  int green = color(0, 255, 0);

  String time;

  stroke(lerpColor(green, red, tp.getSeekPosition()));
  line(tp.getSeekPosition() * width, 0, tp.getSeekPosition() * width,
  height);

  for (int i = 0; i < tp.getChildCount(); i++) {
    Tween t = tp.getTween(i);
    time = (int) t.getTime() + " / " + (int) t.getDuration();

    fill(lerpColor(green, red, t.getSeekPosition()));
    text(time, 10, i * 100 + 10 + 12);
  }

  time = (int) tp.getTime() + " / " + (int) tp.getDuration();

  fill(lerpColor(green, red, tp.getSeekPosition()));
  text(time, width - textWidth(time) - 10, height - 10);
}

public void keyPressed() {
  tp.play();
}

public void mousePressed() {
  tp.pause();
}

public void mouseReleased() {
  tp.resume();
}

public void mouseDragged() {
  tp.seek((float) mouseX / width);
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "TweenParallel_Seek" });
  }
}
