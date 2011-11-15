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

public class Tween_Basic extends PApplet {




int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

PFont font;

Tween t;

public void setup() {
  size(400, 100);
  smooth();

  font = createFont("Arial", 12);
  textFont(font);

  Motion.setup(this);

  // Tween(float _begin, float _end, float _duration);

  // This creates a tween that begins at 0, ends at 400 and plays for 100
  // frames
  t = new Tween(0f, width, 100f);

  t.play();
}

public void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(0, 0, t.getPosition(), height);

  int seekColor = lerpColor(GREEN, RED, t.getSeekPosition());

  stroke(seekColor);
  line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width, height);

  String timeAsString = t.getTime() + " / " + t.getDuration();

  fill(seekColor);
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

public void keyPressed() {
  t.play();
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "Tween_Basic" });
  }
}
