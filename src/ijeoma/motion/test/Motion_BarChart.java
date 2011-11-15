//A MotionController is an object which controls other Motion objects.
//TweenParallel, TweenSequence and Timeline inherit MotionController and Tween inherits Motion.

package ijeoma.motion.test;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

import processing.core.PApplet;
import processing.core.PFont;

public class Motion_BarChart extends PApplet {
	PFont f;

	TweenParallel tp;

	int barCount = 10;
	float barWidth = 0;
	float barHeightMin, barHeightMax;
	int barTopMargin = 24;

	public void setup() {
		size(400, 200);
		smooth();

		f = createFont("Arial", 12);
		textFont(f, 12);

		setupBarChart();
		setupBarChartMotion();
	}

	public void setupBarChart() {
		barWidth = width / barCount;
		barHeightMax = height;
		barHeightMin = height / 5;
	}

	public void setupBarChartMotion() {
		Motion.setup(this);

		tp = new TweenParallel();

		for (int i = 0; i < barCount; i++) {
			float randomHeight = random(barHeightMin, barHeightMax);
			tp.addChild(new Tween("bar"+i, 0f, randomHeight, randomHeight));
		}

		tp.repeat(Tween.REVERSE);
		tp.setTimeScale(1.5f);
		tp.play();
	}

	public void draw() {
		background(255);

		// This animates the 1st pie chart a TweenParallel object
		for (int i = 0; i < tp.getChildCount(); i++) {
			Tween t = tp.getTween(i);

			float barHeight = t.getPosition();

			stroke(0);
			fill(lerpColor(0xFFFFFFFF, 0xFF000000, t.getSeekPosition() * t.getEnd() / height));
			rect(i * barWidth, height, barWidth, -barHeight);

			noStroke();
			fill(255);
			text((int) barHeight + "", i * barWidth + (barWidth - textWidth((int) barHeight + "")) / 2, height - (barHeight - barTopMargin));
		}

		String time = (int) tp.getTime() + " / " + (int) tp.getDuration();

		noStroke();
		fill(lerpColor(0xFF00FF00, 0xFFFF0000, tp.getSeekPosition()));
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		setupBarChartMotion();
	}
}
