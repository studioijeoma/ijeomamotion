/**
	  Author: Ekene Ijeoma
	  Date: October 2010   
 */

package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.Sequence;
import processing.core.PApplet;
import processing.core.PFont;

public class Sequence_Callback extends PApplet {

	PFont font;

	int c1, c2;
	float x1, x2;
	Sequence ts;

	@Override
	public void setup() {
		size(400, 400);

		smooth();

		font = createFont("Arial", 12);
		textFont(font);

		Motion.setup(this);

		c1 = c2 = color(255);
		x1 = x2 = -width;

		int d = 100;

		ts = new Sequence();
		ts.add(new Tween("x1", d).add(this, "x1", (float) width)
				.addColor(this, "c1", color(0)).onBegin("begin").onEnd("end"));
		ts.add(new Tween("x2", d).add(this, "x2", (float) width)
				.addColor(this, "c2", color(0)).onBegin("begin").onEnd("end"));
		ts.repeat().play();
	}

	public void begin(Tween t) {
		println(t + " began");
	}

	public void end(Tween t) {
		println(t + " ended");
	}

	@Override
	public void draw() {
		background(255);

		// ts.update();

		noStroke();

		fill(0);
		fill(c1);
		rect(x1, 0, width, height / 2);
		fill(c2);
		rect(x2, height / 2, width, height / 2);

		fill(0);

		String time = (int) ts.get("x1").getTime() + " / "
				+ (int) ts.get("x1").getDuration() + " / " + x1;
		fill((ts.get("x1").isPlaying() ? color(0, 255, 0) : color(255, 0, 0)));
		text(time, 10, 10 + 12);

		time = (int) ts.get("x2").getTime() + " / "
				+ (int) ts.get("x2").getDuration();
		fill((ts.get("x2").isPlaying() ? color(0, 255, 0) : color(255, 0, 0)));
		text(time, 10, height / 2 + 10 + 12);

		time = (int) ts.getTime() + " / " + (int) ts.getDuration();
		fill((ts.isPlaying() ? color(0, 255, 0) : color(255, 0, 0)));
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		ts.noRepeat().play();
	}

	@Override
	public void mousePressed() {
		ts.pause();
	}

	@Override
	public void mouseReleased() {
		ts.resume();
	}

	@Override
	public void mouseDragged() {
		ts.seek((float) mouseX / width);
	}
}
