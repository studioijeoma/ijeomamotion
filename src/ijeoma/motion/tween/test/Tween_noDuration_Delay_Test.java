package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_noDuration_Delay_Test extends PApplet {
	Tween t;

	public void setup() {
		Motion.setup(this);
		t = new Tween().onEnd(this, "test");
		frameRate(30);
	} // end setup

	public void draw() {
		background(255);

		String time = "";
		time += t.getTime() + " / " + t.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	} // end draw

	public void test() {
		println("test played at frame: " + frameCount);
		// println("duration: " + t.getDuration() + " time: " + t.getTime());
	} // end test

	public void keyReleased() {
		println("pressed key at frame: " + frameCount);

		t = new Tween().onEnd(this, "test");
		t.delay(100).setDuration(100).play();
	} // end keyReleased
}
