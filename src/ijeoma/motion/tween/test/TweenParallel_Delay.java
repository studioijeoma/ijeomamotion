/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class TweenParallel_Delay extends PApplet {
	PFont f;

	TweenParallel tp;

	public void setup() {
		size(400, 200);
		smooth();

		f = createFont("Arial", 12);
		textFont(f);

		Motion.setup(this);

		tp = new TweenParallel();
		tp.addChild(new Tween("t1", 0, width, 100, 0));
		tp.addChild(new Tween("t2", 0, width, 100, 50));
		tp.play();
	}

	public void draw() {
		background(255);

		stroke(255);

		fill(0);
		rect(0, 0, tp.getTween("t1").getPosition(), height / 2);
		rect(0, height / 2, tp.getTween("t2").getPosition(), height / 2);

		drawUI();
	}

	public void drawUI() {
		int red = color(255, 0, 0);
		int green = color(0, 255, 0);

		String time;

		// This draws the seek for the tp TweenParallel object
		stroke(lerpColor(green, red, tp.getSeekPosition()));
		line(tp.getSeekPosition() * width, 0, tp.getSeekPosition() * width,
				height);

		// This draws the time for every Tween object
		for (int i = 0; i < tp.getChildCount(); i++) {
			Tween t = tp.getTween(i);
			time = (int) t.getTime() + " / " + (int) t.getDuration();

			fill(lerpColor(green, red, t.getSeekPosition()));
			text(time, 10, i * 100 + 10 + 12);
		}

		// This draws the time for the tp TweenParallel object
		time = (int) tp.getTime() + " / " + (int) tp.getDuration();

		fill(lerpColor(green, red, tp.getSeekPosition()));
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		tp.play();
	}

	public void mousePressed() {
		tp.pause();
	}

	public void mouseReleased() {
		tp.resume();
	}

	public void mouseDragged() {
		tp.seek((float) mouseX / width);
	}
}
