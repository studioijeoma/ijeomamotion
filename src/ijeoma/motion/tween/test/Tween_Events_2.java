package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;

public class Tween_Events_2 extends PApplet {
	PFont f;

	Tween t;

	float w = 0;

	@Override
	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		t = new Tween(this, "w", width, 100).addEventListener(
				new TweenEventListener()).play();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();

		fill(255 / 2f);
		rect(0, 0, w, height);

		String time = t.getTime() + " / " + t.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		t.play();
	}

	public class TweenEventListener implements MotionEventListener {
		@Override
		public void onMotionEvent(MotionEvent te) {
			if (te.type == MotionEvent.TWEEN_STARTED)
				println(te.getSource() + " started");
			else if (te.type == MotionEvent.TWEEN_ENDED)
				println(te.getSource() + " ended");
			// else if (te.type == MotionEvent.TWEEN_CHANGED)
			// println(((Tween) te.getSource()).getName() + " changed");
			else if (te.type == MotionEvent.TWEEN_REPEATED)
				println(te.getSource() + " repeated");
		}
	}
}
