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
import ijeoma.motion.property.*;
import ijeoma.motion.tween.*; 

Tween t;
PFont f;

PVector v1, v2;

public void setup() {
  size(400, 400);
  smooth();

  rectMode(CENTER);

  f = createFont("Arial", 12);

  v1 = new PVector(0, 0);
  v2 = new PVector(width, 0);

  Motion.setup(this);

  t = new Tween(100).add(v1, new PVector(width, height))
    .add(v2, new PVector(0, height)).play();

  // The above could also be written as
  // t = new Tween(100)
  // .add(new PVectorProperty(v1, new PVector(width, height)))
  // .add(new PVectorProperty(v1, new PVector(0, height))).play();
}

public void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(v1.x, v1.y, 25, 25);
  rect(v2.x, v2.y, 25, 25);

  fill(0);
  String time = t.getTime() + " / " + t.getDuration();
  text(time, width - textWidth(time) - 10, height - 10);
}

public void keyPressed() {
  t.play();
} 

