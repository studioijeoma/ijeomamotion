package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class Tween_Basic extends PApplet {
	PFont font;

	Tween t;

	public void setup() {
	  size(400, 100);
	  smooth();

	  font = createFont("Arial", 12);
	  textFont(font);

	  Motion.setup(this);

	  // Tween(String _name, float _begin, float _end, float _duration);

	  // This creates a tween that begins at 0, ends at 400 and plays for 100
	  // frames
	  t = new Tween("t", 0f, width, 100f);
	  t.setAutoUpdate(false);

	  t.play();
	}

	public void draw() {
	  background(255);
t.update();
	  noStroke();
	  fill(0);
	  rect(0, 0, t.getPosition(), height);

	  fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));

	  String time = t.getTime() + " / " + t.getDuration();
	  text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
	  t.play();
	}

}
