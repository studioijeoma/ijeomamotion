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

Tween t;
PFont f;

public float x = 0;
public float y = 400;

void setup() {
  size(400, 400);
  smooth();

  rectMode(CENTER);

  f = createFont("Arial", 12);

  Motion.setup(this);

  t = new Tween(100).add(this, "x", 400).add(this, "y", 0).repeat()
    .play();

  // The above could also be written as
  // t = new Tween(this, "x", 400, 100).add(this, "y", 0).repeat().play();
}

void draw() {
  background(255);

  noStroke();

  fill(255 / 2f);
  rect(x, y, 50, 50);

  String time = t.getTime() + " / " + t.getDuration();
  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
} 
