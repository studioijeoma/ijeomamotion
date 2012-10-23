package ijeoma.motion.tween.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.PApplet;

//based on Tween.js 01_bars.html example: http://sole.github.com/tween.js/examples/01_bars.html
public class Tween_BarChart2 extends PApplet {
	public float x1, x2, x3;

	Sequence ts;

	public void setup() {
		size(800, 600);
		smooth();

		Motion.setup(this);

		setupBars();
	}

	void setupBars() {
		x1 = x2 = x3 = -width;

		ts = new Sequence();

		// Parallel tp1 = new Parallel();
		// tp1.add(new Tween(100).add(this, "x1",
		// width).setEasing(Tween.EXPO_OUT));
		//
		// Parallel tp2 = new Parallel("tp2");
		// tp2.add((new Tween(100)).add(this, "x1", -width).setEasing(
		// Tween.EXPO_OUT));
		//
		// Parallel tp3 = new Parallel("tp2");
		// tp3.add((new Tween(100)).add(this, "x1", width).setEasing(
		// Tween.EXPO_OUT));
		//
		// ts.add(tp1);
		// ts.add(tp2);
		// ts.add(tp3);

		int d = 50;
		ts.add(new Tween(d).add(this, "x1", width).setEasing(Tween.EXPO_OUT));
		ts.add(new Tween(d).add(this, "x2", width).setEasing(Tween.EXPO_OUT));
		// ts.add(new Tween(3).add(this, "x1",
		// width).setEasing(Tween.EXPO_OUT));

		ts.play();
	}

	public void tweenSequenceEnded(Sequence ts) {
		// println("tweenSequenceEnded");
		// setupBars();
	}

	public void draw() {
		background(255);

		noStroke();

		rect(x1, 0, width, height);
		rect(x2, 0, width, height);
		// rect(x3, 0, width, height);

		String time = "";

		time = (int) ts.getTween(0).getTime() + " / "
				+ (int) ts.getTween(0).getDuration();

		if (ts.getTween(0).isPlaying())
			fill(0, 255, 0);
		else
			fill(255, 0, 0);
		text(time, width - textWidth(time) - 10, height - 70);

		time = (int) ts.getTween(1).getTime() + " / "
				+ (int) ts.getTween(1).getDuration();

		if (ts.getTween(1).isPlaying())
			fill(0, 255, 0);
		else
			fill(255, 0, 0);
		text(time, width - textWidth(time) - 10, height - 50);

		// time = (int) ts.getTween(2).getTime() + " / "
		// + (int) ts.getTween(2).getDuration();
		//
		// if (!ts.getTween(2).isPlaying())
		// fill(255, 0, 0);
		// text(time, width - textWidth(time) - 10, height - 30);

		time = (int) ts.getTime() + " / " + (int) ts.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		if (key == ' ')
			ts.play();
		else
			setupBars();
	}

	@Override
	public void mousePressed() {
		ts.pause();
	}

	@Override
	public void mouseReleased() {
		ts.resume();
	}

	public void mouseDragged() {
		ts.seek((float) mouseX / width);
	}
}
