/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class TweenParallel_Delay extends PApplet {
	TweenParallel tp;

	float w1 = 0, w2 = 0;

	public void setup() {
		size(400, 200);
		smooth();

		Motion.setup(this);

		tp = new TweenParallel();
		tp.addTween(this, "w1", width, 100, 0)
				.addTween(this, "w2", width, 100, 50).play();

		// tp = new TweenParallel();
		// tp.add(new Tween(this, "w1", width, 100, 0))
		// .add(new Tween(this, "w2", width, 100, 50)).play();
	}

	public void draw() {
		background(255);

		stroke(255);

		fill(255 / 2f);
		rect(0, 0, w1, height / 2);
		rect(0, height / 2, w2, height / 2);

		String time;

		time = (int) tp.getTween("w1").getTime() + " / "
				+ (int) tp.getTween("w1").getDuration();

		fill(0);
		text(time, 10, 10 + 12);

		time = (int) tp.getTween("w2").getTime() + " / "
				+ (int) tp.getTween("w2").getDuration();

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
