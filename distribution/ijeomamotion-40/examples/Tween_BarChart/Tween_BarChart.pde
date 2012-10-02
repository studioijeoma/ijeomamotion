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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.PApplet;

String[] cities = { 
  "NYC", "LA", "Milan", "Venice", "London", "Amsterdam"
};

ArrayList<Bar> bars = new ArrayList<Bar>();

float w = 100;
float h = 100;
float low = 250;
float high = 1000;

Sequence ts;
float d = 75;

void setup() {
  size(800, 600);
  smooth();

  Motion.setup(this);

  setupBars();
}

void setupBars() {
  ts = new Sequence();

  bars.clear();

  for (int i = 0; i < cities.length; i++)
    bars.add(new Bar(cities[i], random(low, high), i * w, 0, w, 0));

  Parallel tp1 = new Parallel();
  for (int i = 0; i < bars.size(); i++) {
    float rh = bars.get(i).v / high * h;

    tp1.add(new Tween(d).add(bars.get(i), "h", -rh).setEasing(
    Tween.EXPO_OUT));
  }

  Collections.sort(bars, new ValueComparator());

  Parallel tp2 = new Parallel();
  for (int i = 0; i < bars.size(); i++)
    tp2.add((new Tween(d)).add(bars.get(i), "x", i * w).setEasing(
    Tween.EXPO_OUT));

  Collections.sort(bars, Collections.reverseOrder(new ValueComparator()));

  Parallel tp3 = new Parallel();
  for (int i = 0; i < bars.size(); i++)
    tp3.add((new Tween(d)).add(bars.get(i), "c", 
    color(random(255), random(255), random(255))).setEasing(
    Tween.EXPO_OUT));

  Parallel tp4 = new Parallel();
  for (int i = 0; i < bars.size(); i++)
    tp4.add(new Tween(d).add(bars.get(i), "h", 1f).setEasing(
    Tween.EXPO_OUT));

  ts.add(tp1);
  ts.add(new Tween(d));
  ts.add(tp2);
  ts.add(new Tween(d));
  ts.add(tp3);
  ts.add(new Tween(d));
  ts.add(tp4);

  ts.play();
}

public class ValueComparator implements Comparator<Bar> {
  public int compare(Bar o1, Bar o2) {
    return o1.v - o2.v;
  }
}

void draw() {
  background(255);

  noStroke();

  pushMatrix();
  translate(width / 2 - (bars.size() * w) / 2, height / 2 + h / 2);
  for (Bar b : bars)
    b.draw();
  popMatrix();

  String time = (int) ts.getTime() + " / " + (int) ts.getDuration();

  fill(0);
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  if (key == ' ')
    ts.play();
  else
    setupBars();
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

class Bar {
  String name;
  int v;

  float x, y, w, h;
  int c; 

  Bar(String name, float value, float x, float y, float w, float h) {
    this.name = name;
    this.v = (int) value;
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

    textAlign(CENTER);
    fill(255);
    text(v, x + w / 2, y - 10);
    fill(0);
    text(name, x + w / 2, y + 25);
  }
}


