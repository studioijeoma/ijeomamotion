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
 * Lesser General License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * License along with this library; if not, write to the
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

int c1, c2;

void setup() {
  size(400, 400);
  smooth();

  f = createFont("Arial", 12);

  c1 = color(255, 0, 0);
  c2 = color(255, 255, 0);

  Motion.setup(this);

  t = new Tween(100)
    .add(this, "c1", color(random(255), random(255), random(255)))
      .add(this, "c2", color(random(255), random(255), random(255)))
        .setEasing(Tween.SINE_BOTH)
          .play();
}


void draw() {
  background(255);

  for (int i = 0; i <= width; i++) {
    float j = map(i, 0, width, 0, 1);
    int c = lerpColor(c1, c2, j);
    stroke(c);
    line(i, 0, i, height);
  }

  String time = t.getTime() + " / " + t.getDuration();
  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}

void tweenEnded(Tween t) {
  t.get("c1").setEnd(color(random(255), random(255), random(255)));
  t.get("c2").setEnd(color(random(255), random(255), random(255)));
  t.play();
}

void keyPressed() { 
  t.play();
}

