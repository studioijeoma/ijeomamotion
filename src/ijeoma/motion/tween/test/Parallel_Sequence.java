/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Parallel;
import ijeoma.motion.tween.Sequence;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Parallel_Sequence extends PApplet {
	float x1, x2;

	Parallel tp;
	Sequence ts1, ts2;

	@Override
	public void setup() {
		size(400, 200);

		ellipseMode(CORNER);
		smooth();

		Motion.setup(this);

		x1 = x2 = -width;

		ts1 = new Sequence();
		ts1.add(new Tween(this, "x1", width, 100)).add(
				new Tween(this, "x1", width, 75));

		ts2 = new Sequence();
		ts2.add(new Tween(this, "x2", width, 50)).add(
				new Tween(this, "x2", width, 25));

		tp = new Parallel();
		tp.add(ts1).add(ts2).repeat().play();
	}

	@Override
	public void draw() {
		background(255);

		stroke(255);
		fill(255 / 2f);

		rect(x1, 0, width, height / 2);
		rect(x2, height / 2, width, height / 2);

		String time;

		time = (int) ts1.getChild().getTime() + " / "
				+ (int) ts1.getChild().getDuration();

		fill(0);
		text(time, 10, 10 + 12);

		time = (int) ts2.getChild().getTime() + " / "
				+ (int) ts2.getChild().getDuration();

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
