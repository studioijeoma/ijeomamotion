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

	Sequence s;

	public void setup() {
		size(400, 400);
		smooth();

		Motion.setup(this);

		setupBars();
	}

	void setupBars() {
		x1 = -width;
		int d = 100;

		s = new Sequence();

		Parallel tp1 = new Parallel();
		Tween t = new Tween("1st", d).add(this, "x1", width).setEasing(
				Tween.EXPO_OUT);
		tp1.add(t);
		s.add(tp1);

		Parallel tp2 = new Parallel("tp2");
		t = new Tween("2nd", d).add(this, "x1", -width).setEasing(
				Tween.EXPO_OUT);
		tp2.add(t);
		s.add(tp2);

		// Parallel tp3 = new Parallel("tp2");
		// tp3.add(new Tween(d).add(this, "x1",
		// width).setEasing(Tween.EXPO_OUT));
		// s.add(tp3);

		// s.add(new Tween(d).add(this, "x1", width));
		// s.add(new Tween(d).add(this, "x1", -width));

		s.play();
	}

	public void tweenSequenceEnded(Sequence ts) {
		// println("tweenSequenceEnded");
		// setupBars();
	}

	public void draw() {
		background(255);

		noStroke();

		rect(x1, 0, width, height);

		String time = "";

		time = (int) s.get(0).getTime() + " / " + (int) s.get(0).getDuration();

		if (s.get(0).isPlaying())
			fill(0, 255, 0);
		else
			fill(255, 0, 0);
		text(time, width - textWidth(time) - 10, height - 70);

		if (s.get(1) != null) {
			time = (int) s.get(1).getTime() + " / "
					+ (int) s.get(1).getDuration();

			if (s.get(1).isPlaying())
				fill(0, 255, 0);
			else
				fill(255, 0, 0);
			text(time, width - textWidth(time) - 10, height - 50);
		}

		if (s.get(2) != null) {
			time = (int) s.get(2).getTime() + " / "
					+ (int) s.get(2).getDuration();

			if (s.get(2).isPlaying())
				fill(0, 255, 0);
			else
				fill(255, 0, 0);
			text(time, width - textWidth(time) - 10, height - 30);
		}

		time = (int) s.getTime() + " / " + (int) s.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		if (key == ' ')
			s.play();
		else
			setupBars();
	}

	@Override
	public void mousePressed() {
		// s.pause();
		s.play();
	}

	@Override
	public void mouseReleased() {
		// s.resume();
	}

	public void mouseDragged() {
		// s.seek((float) mouseX / width);
	}
}
