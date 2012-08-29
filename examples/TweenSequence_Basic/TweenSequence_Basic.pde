/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

import ijeoma.motion.*;
import ijeoma.motion.tween.*; 

PFont font;

int c1, c2, c3, c4;
float x1, x2, x3, x4;
TweenSequence ts;

void setup() {
  size(400, 400);

  smooth();

  font = createFont("Arial", 12);
  textFont(font);

  Motion.setup(this);

  c1 = c2 = c3 = c4 = color(255);
  x1 = x2 = x3 = x4 = -width;

  ts = new TweenSequence();
  ts.add(new Tween(100).add(this, "x1", (float)width).add(this, "c1", 
  color(0)), "x1");
  ts.add(new Tween(75).add(this, "x2", (float)width).add(this, "c2", 
  color(0)), "x2");
  ts.add(new Tween(50).add(this, "x3", (float)width).add(this, "c3", 
  color(0)), "x3");
  ts.add(new Tween(25).add(this, "x4", (float)width).add(this, "c4", 
  color(0)), "x4");
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

  fill(0);

  String time = (int) ts.getTween("x1").getTime() + " / "
    + (int) ts.getTween("x1").getDuration();
  text(time, 10, 10 + 12);

  time = (int) ts.getTween("x2").getTime() + " / "
    + (int) ts.getTween("x2").getDuration();
  text(time, 10, 100 + 10 + 12);

  time = (int) ts.getTween("x3").getTime() + " / "
    + (int) ts.getTween("x3").getDuration();
  text(time, 10, 200 + 10 + 12);

  time = (int) ts.getTween("x4").getTime() + " / "
    + (int) ts.getTween("x4").getDuration();
  text(time, 10, 300 + 10 + 12);

  time = (int) ts.getTime() + " / " + (int) ts.getDuration();
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  ts.play();
}

void mousePressed() {
  ts.pause();
}

void mouseReleased() {
  ts.resume();
}

void mouseDragged() {
  ts.seek((float) mouseX / width);
} 

