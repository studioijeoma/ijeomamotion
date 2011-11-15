package ijeoma.motion.tween.test;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import ijeoma.motion.event.*;
import processing.core.PApplet;
import processing.core.PFont;

public class Tween_Events_2 extends PApplet {
	PFont f;

	Tween t;
	TweenEventListener tel;

	public void setup() {
		size(400, 100);

		smooth();

		f = createFont("Arial", 12);
		textFont(f);

		Motion.setup(this);

		tel = new TweenEventListener();

		t = new Tween("t", 0, width, 100);
		//t.repeat();
		t.addEventListener(tel);
		t.play();
	}

	public void draw() {
		background(255);

		noStroke();
		fill(0);
		rect(0, 0, t.getPosition(), height);

		String time = t.getTime() + " / " + t.getDuration();

		fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		if (key == ' ')
			t.play();
	}

	public class TweenEventListener implements MotionEventListener {
		public void onMotionEvent(MotionEvent te) {
			if (te.type == MotionEvent.TWEEN_STARTED)
				println(((Tween) te.getSource()).getName() + " started");
			else if (te.type == MotionEvent.TWEEN_ENDED)
				println(((Tween) te.getSource()).getName() + " ended");
//			else if (te.type == MotionEvent.TWEEN_CHANGED)
//				println(((Tween) te.getSource()).getName() + " changed");
			else if (te.type == MotionEvent.TWEEN_REPEATED)
				println(((Tween) te.getSource()).getName() + " repeated");
		}
	}
}
