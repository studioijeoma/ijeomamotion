/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import processing.core.PApplet;
import processing.core.PFont;

import ijeoma.motion.*;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.*;

public class TweenSequence_Events_3 extends PApplet {

	PFont font;

	TweenSequence ts;

	TweenEventListener tel;
	TweenSequenceEventListener tsel;

	public void setup() {
		size(400, 400);

		smooth();

		font = createFont("Arial", 12);
		textFont(font);

		Motion.setup(this);

		tel = new TweenEventListener();

		Tween t1 = new Tween("t1", -width, width, 100);
		Tween t2 = new Tween("t2", -width, width, 75);
		Tween t3 = new Tween("t3", -width, width, 50);
		Tween t4 = new Tween("t4", -width, width, 25);

		t1.addEventListener(tel);
		t2.addEventListener(tel);
		t3.addEventListener(tel);
		t4.addEventListener(tel);

		tsel = new TweenSequenceEventListener();

		ts = new TweenSequence();
		ts.setName("ts");

		ts.appendChild(t1);
		ts.appendChild(t2);
		ts.appendChild(t3);
		ts.appendChild(t4);

		// ts.repeat();
		// ts.addEventListener(tsel);
		ts.play();
	}

	public void draw() {
		background(255);

		noStroke();
		fill(0);

		for (int i = 0; i < ts.getChildCount(); i++)
			rect(ts.getTween(i).getPosition(), i * 100, width, 100);

		drawUI();
	}

	public void drawUI() {
		String time;

		// This draws the seek for the ts TweenParallel object
		stroke(lerpColor(0xFF00FF00, 0xFFFF0000, ts.getSeekPosition()));
		line(ts.getSeekPosition() * width, 0, ts.getSeekPosition() * width,
				height);

		// This draws the time for every Tween object
		for (int i = 0; i < ts.getChildCount(); i++) {
			Tween t = ts.getTween(i);
			time = (int) t.getTime() + " / " + (int) t.getDuration();

			fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));
			text(time, 10, i * 100 + 10 + 12);
		}

		// This draws the time for the ts TweenSequence object
		time = (int) ts.getTime() + " / " + (int) ts.getDuration();

		fill(lerpColor(0xFF00FF00, 0xFFFF0000, ts.getSeekPosition()));
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		ts.play();
	}
	
	public void mousePressed() {
		ts.pause();
	}

	public void mouseReleased() {
		ts.resume();
	}

	public void mouseDragged() {
		ts.seek(norm(mouseX, 0, width));
	}


	class TweenEventListener implements MotionEventListener {
		public void onMotionEvent(MotionEvent te) {
			if (te.type == MotionEvent.TWEEN_STARTED)
				println(((Tween) te.getSource()).getName() + " started");
			else if (te.type == MotionEvent.TWEEN_ENDED)
				println(((Tween) te.getSource()).getName() + " ended");
		}
	}

	class TweenSequenceEventListener implements MotionEventListener {
		public void onMotionEvent(MotionEvent te) {
			if (te.type == MotionEvent.TWEEN_SEQUENCE_STARTED)
				println(((TweenSequence) te.getSource()).getName() + " started");
			else if (te.type == MotionEvent.TWEEN_SEQUENCE_ENDED)
				println(((TweenSequence) te.getSource()).getName() + " ended");
			// else if (te.type == MotionEvent.TWEEN_SEQUENCE_CHANGED)
			// println(((TweenSequence) te.getSource()).getName() + " changed");
			else if (te.type == MotionEvent.TWEEN_SEQUENCE_REPEATED)
				println(((TweenSequence) te.getSource()).getName()
						+ " repeated");
		}
	}
}
