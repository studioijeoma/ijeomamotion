import processing.core.*; 
import processing.xml.*; 

import ijeoma.geom.bezier.*; 

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

public class Bezier2D_Basic extends PApplet {



Bezier2D b1;

public void setup() {
  size(100, 100);
  smooth();

  b1 = new Bezier2D(this, 85, 20, 10, 10, 90, 90, 15, 80);
}

public void draw() {
  background(255);

  noFill();
  b1.draw();
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "Bezier2D_Basic" });
  }
}
