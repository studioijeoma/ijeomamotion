package ijeoma.motion.test;

import java.util.Arrays;
import java.util.Collections;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.PApplet;
import processing.core.PFont;

public class Motion_PieChart extends PApplet {
	PFont f;

	TweenParallel tp;
	TweenSequence ts;

	float pieDiameter = 150;

	int wedgeCount = 5;
	int[] wedgeAngles;

	public void setup() {
		size(400, 200);
		smooth();

		setupPieChart();
		setupPieChartMotion();
	}

	void setupPieChart() {
		wedgeAngles = randomIntsInSum(360, wedgeCount);
	}

	void setupPieChartMotion() {
		Motion.setup(this);

		tp = new TweenParallel();
		ts = new TweenSequence();

		float lastWedgeAngle = 0;

		for (int i = 0; i < wedgeAngles.length; i++) {
			stroke(0);
			fill(wedgeAngles[i] * 3);

			tp.addChild(new Tween("pie1_wedge_"+i, lastWedgeAngle, lastWedgeAngle
					+ radians(wedgeAngles[i]), 50));

			ts.appendChild(new Tween("pie2_wedge"+i, lastWedgeAngle, lastWedgeAngle
					+ radians(wedgeAngles[i]), 50));

			lastWedgeAngle += radians(wedgeAngles[i]);
		}

		tp.play();
		ts.play();
	}

	public void draw() {
		background(255);

		// This animates the 1st pie chart a TweenParallel object
		for (int i = 0; i < tp.getChildCount(); i++) {
			Tween t = tp.getTween(i);

			stroke(255);
			fill(255 * t.getChange() / TWO_PI);
			arc(100, height / 2, pieDiameter, pieDiameter, t.getBegin(),
					t.getPosition());
		}

		String time = tp.getTime() + " / " + tp.getDuration(); 

		fill(lerpColor(0xFF00FF00, 0xFFFF0000, tp.getSeekPosition()));
		text(time, width / 2 - textWidth(time) - 10,
				height - 10);

		// This animates the 2nd pie chart using a TweenSequence object
		for (int i = 0; i < ts.getChildCount(); i++) {
			Tween t = ts.getTween(i);

			stroke(255);
			fill(255 * t.getChange() / TWO_PI);
			arc(300, height / 2, pieDiameter, pieDiameter, t.getBegin(),
					t.getPosition());
		}

		time = ts.getTime() + " / " + ts.getDuration();

		fill(lerpColor(0xFF00FF00, 0xFFFF0000, ts.getSeekPosition()));
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		setupPieChart();
		setupPieChartMotion();
	}

	int[] randomIntsInSum(int _sum, int count) {
		int[] randomNumbers = new int[count];

		for (int i = 0; i < randomNumbers.length - 1; i++) {
			int randomNumber = (int) random(0, _sum);
			randomNumbers[i] = randomNumber;
			_sum -= randomNumber;
		}

		randomNumbers[randomNumbers.length - 1] = _sum;

		Collections.shuffle(Arrays.asList(randomNumbers));

		return randomNumbers;
	}
}
