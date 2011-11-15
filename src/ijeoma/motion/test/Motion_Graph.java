//A MotionController is an object which controls other Motion objects.
//TweenParallel, TweenSequence and Timeline inherit MotionController and Tween inherits Motion.

package ijeoma.motion.test;

//import toxi.color.*;

import ijeoma.motion.*;
import ijeoma.motion.timeline.KeyFrame;
import ijeoma.motion.timeline.Timeline;
import ijeoma.motion.tween.*;

import processing.core.PApplet;
import processing.core.PFont;

public class Motion_Graph extends PApplet {
	PFont f;

	TweenParallel tp;
	TweenSequence ts;
	Timeline tl;

	// int barTopMargin = 24;
	float pointSize = 5;
	float pointSpacing = 0;
	float pointMargin = 25;

	int dataCount = 10;
	float dataMin, dataMax;
	float[][][] data;
	int dataSetCount = 4;
	int dataSetIndex = 0;

	public void setup() {
		size(400, 200);
		smooth();

		f = createFont("Arial", 12);
		textFont(f, 12);

		// TColor col = TColor.newHSV(0, 1, 1);

		setupGraphData();
		setupGraphMotion();
	}

	public void setupGraphData() {
		data = new float[dataSetCount][dataCount][2];

		dataMin = pointMargin;
		dataMax = height - pointMargin;

		pointSpacing = (width - pointMargin * 2) / (dataCount - 1);

		for (int i = 0; i < dataCount; i++) {
			data[0][i][0] = random(dataMin, dataMax);// random(width / 4f);
			data[0][i][1] = random(dataMin, dataMax);// height - random(height /
														// 4f);

			data[1][i][0] = random(dataMin, dataMax);// random(width / 3f);
			data[1][i][1] = random(dataMin, dataMax);// height - random(height /
														// 3f);

			data[2][i][0] = random(dataMin, dataMax);// random(width / 2f);
			data[2][i][1] = random(dataMin, dataMax);// height - random(height /
														// 2f);

			data[3][i][0] = random(dataMin, dataMax);// width - random(width /
														// 4f);
			data[3][i][1] = random(dataMin, dataMax);// random(height / 4f);
		}
	}

	public void setupGraphMotion() {
		Motion.setup(this);

		tl = new Timeline();
		tl.setTimeMode(Timeline.SECONDS);

		KeyFrame kf1a = new KeyFrame("lines", 0);
		for (int i = 0; i < 4; i++)
			kf1a.addChild(new Tween("line_" + i, 0, width, 1));

		KeyFrame kf1 = new KeyFrame("data_1_to_2", 0);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[0][i][0], data[1][i][0]));
			t1.addProperty(new Property("y", data[0][i][1], data[1][i][1]));
			t1.setDuration(1);
			kf1.addChild(t1);
		}

		KeyFrame kf2 = new KeyFrame("data_2_to_3", 1);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[1][i][0], data[2][i][0]));
			t1.addProperty(new Property("y", data[1][i][1], data[2][i][1]));
			t1.setDuration(1);
			kf2.addChild(t1);
		}

		KeyFrame kf3 = new KeyFrame("data_3_to_4", 2);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[2][i][0], data[3][i][0]));
			t1.addProperty(new Property("y", data[2][i][1], data[3][i][1]));
			t1.setDuration(1);
			kf3.addChild(t1);
		}

		KeyFrame kf4 = new KeyFrame("data_4_to_1", 3);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[3][i][0], data[0][i][0]));
			t1.addProperty(new Property("y", data[3][i][1], data[0][i][1]));
			t1.setDuration(1);
			kf4.addChild(t1);
		}

		// tl.addKeyFrame(kf1a);
		tl.addKeyFrame(kf1);
		tl.addKeyFrame(kf2);
		tl.addKeyFrame(kf3);
		tl.addKeyFrame(kf4);

		tl.play();
	}

	public void draw() {
		background(255);

		int lineCount = 10;
		float lineSpacing = (float) height / lineCount;

		for (int i = 0; i < lineCount; i++) {
			stroke(200);
			line(0, i * lineSpacing, width, i * lineSpacing);
		}

		KeyFrame[] currentKfs = tl.getCurrentKeyFrames();

		for (int i = 0; i < currentKfs.length; i++) {

			// if (currentKfs[i].getName().equals("lines")) {
			// for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
			// float x1 = 0;
			// float y1 = height / (j + 1);
			// float x2 = currentKfs[i].getTween(j).getPosition();
			// float y2 = height / (j + 1);
			//
			// stroke(255 / 2);
			// line(x1, y1, x2, y2);
			// }
			// }

			if (currentKfs[i].getName().equals("data_1_to_2")
					|| currentKfs[i].getName().equals("data_2_to_3")
					|| currentKfs[i].getName().equals("data_3_to_4")
					|| currentKfs[i].getName().equals("data_4_to_1")) {
				beginShape();
				for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
					float x = j * pointSpacing + pointMargin;
					float y = ((Tween) currentKfs[i].getChild(j))
							.getPosition("y");

					stroke(0);
					noFill();
					vertex(x, y);
				}
				endShape();

				for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
					// float x = ((Tween)
					// currentKfs[i].getChild(j)).getPosition("x");
					float x = j * pointSpacing + pointMargin;
					float y = ((Tween) currentKfs[i].getChild(j))
							.getPosition("y");
					float size = pointSize;

					noStroke();
					fill(255, 0, 0);
					ellipse(x, y, size, size);

					String s = (int) (height - y) + "";

					noStroke();
					fill(255, 0, 0);
					textFont(f, 12);
					text(s, x - textWidth(s) + 10, y - 10);
				}
			}
		}

		String time = (int) tl.getTime() + " / " + (int) tl.getDuration();

		noStroke();
		fill(lerpColor(0xFF00FF00, 0xFFFF0000, tl.getSeekPosition()));
		textFont(f, 12);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		if (key == '1')
			tl.gotoAndPlay("ONE");
		else if (key == '2')
			tl.gotoAndPlay("TWO");
		else if (key == '3')
			tl.gotoAndPlay("THREE");
		else if (key == '4')
			tl.gotoAndPlay("FOUR");
		else
			tl.play();
	}

	public void mousePressed() {
		tl.pause();
	}

	public void mouseReleased() {
		tl.resume();
	}

	public void mouseDragged() {
		tl.seek(norm(mouseX, 0, width));
	}
}
