/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Parallel;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Parallel_Delay extends PApplet {
	Parallel tp;

	float w1 = 0, w2 = 0;

	@Override
	public void setup() {
		size(400, 200);
		smooth();

		Motion.setup(this);

		tp = new Parallel(); 
		tp.add(new Tween(this, "w1", width, 100, 0))
				.add(new Tween(this, "w2", width, 100, 50)).play();
	}

	@Override
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

	@Override
	public void keyPressed() {
		tp.play();
	}

	@Override
	public void mousePressed() {
		tp.pause();
	}

	@Override
	public void mouseReleased() {
		tp.resume();
	}

	@Override
	public void mouseDragged() {
		tp.seek((float) mouseX / width);
	}
}
