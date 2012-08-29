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

//based on Tween.js 01_bars.html example: http://sole.github.com/tween.js/examples/01_bars.html

import java.util.ArrayList;

import ijeoma.motion.*;
import ijeoma.motion.tween.*; 

Tween t;

float w = 0;

ArrayList<Rect> rects = new ArrayList<Rect>();

void setup() {
  size(800, 600);
  smooth();

  Motion.setup(this);

  for (int i = 0; i < 1000; i++)
    rects.add(new Rect(random(width), random(height), 100, 1));

  for (Rect r : rects)
    t = new Tween(100)
      .add(r, "x", random(width))
        .add(r, "c", 
        (int) color(random(255), random(255), random(255)))
          .play();
}

void draw() {
  background(255);

  noStroke();

  for (Rect r : rects)
    r.draw();

  fill(0);
  text((int)frameRate, width - textWidth(str((int)frameRate)) - 25, height - 25);
}

void keyPressed() {
}

void tweenEnded(Tween t) {
  t.get("x").setEnd(random(width));
  t.get("c").setEnd(color(random(255), random(255), random(255)));
  t.play();
}

class Rect {
  float x, y, w, h;
  int c;

  Rect(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    c = color(random(255), random(255), random(255));
  }

  void draw() {
    noStroke();
    fill(c);
    rect(x, y, w, h);
  }
} 

