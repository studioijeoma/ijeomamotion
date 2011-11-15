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

public class Tween_Seek extends PApplet {




int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

PFont f;

Tween t;

public void setup() {
  size(400, 100);

  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  // Tween(begin, end, duration);
  // This creates a tween that begins at 0, ends at 400, and plays for 100
  // frames
  t = new Tween(0, width, 100);
  t.repeat();
  t.play();
}

public void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(0, 0, t.getPosition(), height);

  String timeAsString = t.getTime() + " / " + t.getDuration();

  fill(lerpColor(GREEN, RED, t.getSeekPosition()));
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

public void keyPressed() {
  if (key == ' ')
    t.play();
}

public void mousePressed() {
  t.pause();
}

public void mouseReleased() {
  t.resume();
}

public void mouseDragged() {
  t.seek(norm(mouseX, 0, width));
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "Tween_Seek" });
  }
}
