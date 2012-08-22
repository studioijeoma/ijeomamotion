/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class TweenParallel_Events_1 extends PApplet {
	PFont f;

	TweenParallel tp;

	float x1 = -width;
	float x2 = width;

	public void setup() {
		size(400, 200);

		ellipseMode(CORNER);
		smooth();

		f = createFont("Arial", 12);
		textFont(f);

		Motion.setup(this);

		tp = new TweenParallel();
		tp.add(new Tween(this, "x1", width, 100), "x1").add(
				new Tween(this, "x2", -width, 200), "x2");
		tp.repeat().play();
	}

	public void draw() {
		background(255);

		stroke(255);
		fill(255 / 2f);
		rect(x1, 0, width, height / 2);
		rect(x2, height / 2, width, height / 2);

		String time;

		time = (int) tp.getTween("x1").getTime() + " / "
				+ (int) tp.getTween("x1").getDuration();

		fill(0);
		text(time, 10, 10 + 12);

		time = (int) tp.getTween("x2").getTime() + " / "
				+ (int) tp.getTween("x2").getDuration();

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

	public void tweenParallelStarted(TweenParallel _tp) {
		println(_tp + " started");
	}

	public void tweenParallelEnded(TweenParallel _tp) {
		println(_tp + " ended");
	}

	// public void tweenParallelChanged(TweenParallel _tp) {
	// println(_tp.getName() + " resumed");
	// }

	public void tweenParallelRepeated(TweenParallel _tp) {
		println(_tp + " repeated");
	}
}
