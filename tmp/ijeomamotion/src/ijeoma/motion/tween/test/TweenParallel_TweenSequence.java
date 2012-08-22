/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class TweenParallel_TweenSequence extends PApplet {
	float x1, x2;

	TweenParallel tp;
	TweenSequence ts1, ts2;

	public void setup() {
		size(400, 200);

		ellipseMode(CORNER);
		smooth();

		Motion.setup(this);

		x1 = x2 = -width;

		ts1 = new TweenSequence();
		ts1.addTween(this, "x1", width, 100).addTween(this, "x1", width, 75);

		ts2 = new TweenSequence();
		ts2.addTween(this, "x2", width, 50).addTween(this, "x2", width, 25);

		tp = new TweenParallel();
		tp.add(ts1).add(ts2).repeat().play();
	}

	public void draw() {
		background(255);

		stroke(255);
		fill(255 / 2f);

		rect(x1, 0, width, height / 2);
		rect(x2, height / 2, width, height / 2);

		String time;

		time = (int) ts1.getCurrentChild().getTime() + " / "
				+ (int) ts1.getCurrentChild().getDuration();

		fill(0);
		text(time, 10, 10 + 12);

		time = (int) ts2.getCurrentChild().getTime() + " / "
				+ (int) ts2.getCurrentChild().getDuration();

		fill(0);
		text(time, 10, 100 + 10 + 12);

		time = (int) tp.getTime() + " / " + (int) tp.getDuration();

		fill(0);
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
