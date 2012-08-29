/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.TweenSequence;
import processing.core.PApplet;
import processing.core.PFont;

public class TweenSequence_Events_2 extends PApplet {

	PFont font;

	int c1, c2, c3, c4;
	float x1, x2, x3, x4;

	TweenSequence ts;

	@Override
	public void setup() {
		size(400, 400);

		smooth();

		font = createFont("Arial", 12);
		textFont(font);

		Motion.setup(this);

		c1 = c2 = c3 = c4 = color(255);
		x1 = x2 = x3 = x4 = -width;

		ts = new TweenSequence();
		ts.add(new Tween(100).add(this, "x1", width).add(this, "c1",
				color(0)), "x1");
		ts.add(new Tween(75).add(this, "x2", width).add(this, "c2",
				color(0)), "x2");
		ts.add(new Tween(50).add(this, "x3", width).add(this, "c3",
				color(0)), "x3");
		ts.add(new Tween(25).add(this, "x4", width).add(this, "c4",
				color(0)), "x4");
		ts.addEventListener(new TweenSequenceEventListener());
		ts.repeat().play();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();

		fill(c1);
		rect(x1, 0, width, 100);
		fill(c2);
		rect(x2, 100, width, 100);
		fill(c3);
		rect(x3, 200, width, 100);
		fill(c4);
		rect(x4, 300, width, 100);

		fill(0);

		String time = (int) ts.getTween("x1").getTime() + " / "
				+ (int) ts.getTween("x1").getDuration();
		text(time, 10, 10 + 12);

		time = (int) ts.getTween("x2").getTime() + " / "
				+ (int) ts.getTween("x2").getDuration();
		text(time, 10, 100 + 10 + 12);

		time = (int) ts.getTween("x3").getTime() + " / "
				+ (int) ts.getTween("x3").getDuration();
		text(time, 10, 200 + 10 + 12);

		time = (int) ts.getTween("x4").getTime() + " / "
				+ (int) ts.getTween("x4").getDuration();
		text(time, 10, 300 + 10 + 12);

		time = (int) ts.getTime() + " / " + (int) ts.getDuration();
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		ts.play();
	}

	class TweenSequenceEventListener implements MotionEventListener {
		@Override
		public void onMotionEvent(MotionEvent te) {
			if (te.type == MotionEvent.TWEEN_SEQUENCE_STARTED)
				println(te.getSource() + " started");
			else if (te.type == MotionEvent.TWEEN_SEQUENCE_ENDED)
				println(te.getSource() + " ended");
			// else if (te.type == MotionEvent.TWEEN_SEQUENCE_CHANGED)
			// println(((TweenSequence) te.getSource()).getName() + " changed");
			else if (te.type == MotionEvent.TWEEN_SEQUENCE_REPEATED)
				println(te.getSource() + " repeated");
		}
	}
}
