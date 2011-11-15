/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class TweenParallel_TweenSequence extends PApplet {
	PFont f;

	TweenParallel tp;

	public void setup() {
		size(400, 200);

		ellipseMode(CORNER);
		smooth();

		f = createFont("Arial", 12);
		textFont(f);

		Motion.setup(this);

		TweenSequence ts1 = new TweenSequence();
		ts1.appendChild(new Tween("t1", -width, width, 100));
		ts1.appendChild(new Tween("t2", -width, width, 75));

		TweenSequence ts2 = new TweenSequence();
		ts2.appendChild(new Tween("t3", -width, width, 50));
		ts2.appendChild(new Tween("t4", -width, width, 25));

		tp = new TweenParallel();
		tp.addChild(ts1);
		tp.addChild(ts2);

		tp.repeat();
		tp.play();
	}

	public void draw() {
		background(255);

		stroke(255);
		fill(0);
		
		tp.update();

		TweenSequence ts1 = tp.getTweenSequence(0);
		TweenSequence ts2 = tp.getTweenSequence(1);

		 rect(ts1.getCurrentChildPosition(), 0, width, height / 2);
		 rect(ts2.getCurrentChildPosition(), height / 2, width, height / 2);
		 
		 println(ts2.getCurrentChildPosition());

		 drawUI();
	}

	public void drawUI() {
		String time;

		// This draws the seek for the tp TweenParallel object
		stroke(lerpColor(0xFF00FF00, 0xFFFF0000, tp.getSeekPosition()));
		line(tp.getSeekPosition() * width, 0, tp.getSeekPosition() * width,
				height);

		// This draws the time for every Tween object
		for (int i = 0; i < tp.getChildCount(); i++) {
			TweenSequence t = tp.getTweenSequence(i);
			time = (int) t.getTime() + " / " + (int) t.getDuration();

			fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));
			text(time, 10, i * 100 + 10 + 12);
		}

		// This draws the time for the tp TweenParallel object
		time = (int) tp.getTime() + " / " + (int) tp.getDuration();

		fill(lerpColor(0xFF00FF00, 0xFFFF0000, tp.getSeekPosition()));
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
