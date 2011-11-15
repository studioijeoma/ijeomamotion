package ijeoma.motion.test;

import ijeoma.motion.*;
import ijeoma.motion.timeline.KeyFrame;
import ijeoma.motion.timeline.Timeline;
import ijeoma.motion.tween.*;

import processing.core.PApplet;
import processing.core.PFont;

//import processing.video.MovieMaker;

public class Motion_PointGraph extends PApplet {
	PFont f;

	Timeline tl;

	// MovieMaker mm;

	float pointSize = 10;
	float pointSpacing = 0;
	float pointMargin = 25;

	int lineCount = 10;
	float lineSpacing = (float) 200 / lineCount;

	int dataCount = 1000;
	float dataMin, dataMax;
	float[][][] data;
	int dataSetCount = 4;
	int dataSetIndex = 0;

	public void setup() {
		size(800, 600);
		frameRate(15);
		smooth();

		// mm = new MovieMaker(this, width, height, "drawing.mov");
		// mm = new MovieMaker(this, width, height, "drawing.mov", 15,
		// MovieMaker.ANIMATION, MovieMaker.HIGH);

		f = createFont("Arial", 12);
		textFont(f, 12);

		lineSpacing = (float) height / lineCount;

		setupGraphData();
		setupGraphMotion();
	}

	public void setupGraphData() {
		data = new float[dataSetCount][dataCount][2];

		dataMin = pointMargin;
		dataMax = height - pointMargin;

		pointSpacing = (width - pointMargin * 2) / (dataCount - 1);

		for (int i = 0; i < dataCount; i++) {
			data[0][i][0] = random(150) + pointMargin;
			data[0][i][1] = random(450, 600) - pointMargin;

			data[1][i][0] = random(150, 300);
			data[1][i][1] = random(300, 450);

			data[2][i][0] = random(300, 450);
			data[2][i][1] = random(150, 300);

			data[3][i][0] = random(450, 600) - pointMargin;
			data[3][i][1] = random(150) + pointMargin;

			// data[0][i][0] = random(100)+pointMargin;
			// data[0][i][1] = random(150, 200)-pointMargin;
			//
			// data[1][i][0] = random(100, 200);
			// data[1][i][1] = random(100, 150);
			//
			// data[2][i][0] = random(200, 300);
			// data[2][i][1] = random(50, 100);
			//
			// data[3][i][0] = random(300, 400)-pointMargin;
			// data[3][i][1] = random(50)+pointMargin;
		}
	}

	public void setupGraphMotion() {
		Motion.setup(this);

		tl = new Timeline();
		tl.setTimeMode(Timeline.SECONDS);

		KeyFrame linesin = new KeyFrame("lines_in", 0);
		for (int i = 0; i < lineCount; i++)
			linesin.addChild(new Tween("line_" + i, 0, width, 1f, 0,
					Tween.BOUNCE_OUT));

		linesin.setDuration(11f);

		KeyFrame data1in = new KeyFrame("dataset_1_in", .5f);

		for (int i = 0; i < dataCount; i++)
			data1in.addChild(new Tween("data_" + i, 0, 1, 1.0f, 0,
					Tween.BOUNCE_OUT));

		data1in.setDuration(1.5f);

		KeyFrame data1to2 = new KeyFrame("dataset_1_to_2", 2f);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[0][i][0], data[1][i][0]));
			t1.addProperty(new Property("y", data[0][i][1], data[1][i][1]));
			t1.setDuration(1.5f);
			t1.setEasing(Tween.CIRC_OUT);
			data1to2.addChild(t1);
		}

		data1to2.setDuration(3f);

		KeyFrame data2to3 = new KeyFrame("dataset_2_to_3", 5f);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[1][i][0], data[2][i][0]));
			t1.addProperty(new Property("y", data[1][i][1], data[2][i][1]));
			t1.setDuration(1.5f);
			t1.setEasing(Tween.CIRC_OUT);
			data2to3.addChild(t1);
		}

		data2to3.setDuration(3f);

		KeyFrame data3to4 = new KeyFrame("dataset_3_to_4", 8f);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[2][i][0], data[3][i][0]));
			t1.addProperty(new Property("y", data[2][i][1], data[3][i][1]));
			t1.setDuration(1.5f);
			t1.setEasing(Tween.CIRC_OUT);
			data3to4.addChild(t1);
		}

		data3to4.setDuration(3f);

		KeyFrame data4out = new KeyFrame("dataset_4_out", 11f);

		for (int i = 0; i < dataCount; i++)
			data4out.addChild(new Tween("data_" + i, 1, 0, .5f, 0,
					Tween.BOUNCE_IN));

		data4out.setDuration(1f);

		KeyFrame linesout = new KeyFrame("lines_out", 11f);
		for (int i = 0; i < lineCount; i++)
			linesout.addChild(new Tween("line_" + i, width, 0, 1, 0,
					Tween.BOUNCE_IN));

		tl.addKeyFrame(linesin);
		tl.addKeyFrame(data1in);
		tl.addKeyFrame(data1to2);
		tl.addKeyFrame(data2to3);
		tl.addKeyFrame(data3to4);
		tl.addKeyFrame(data4out);
		tl.addKeyFrame(linesout);

		// tl.play();
	}

	public void draw() {
		background(255);

		KeyFrame[] currentKfs = tl.getCurrentKeyFrames();

		for (int i = 0; i < currentKfs.length; i++) {
			if (currentKfs[i].getName().equals("lines_in")) {
				for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
					float x1 = 0;
					float y1 = j * lineSpacing;
					float x2 = currentKfs[i].getTween(j).getPosition();
					float y2 = j * lineSpacing;
					float a = 255 * currentKfs[i].getTween(j).getSeekPosition();

					stroke(200, a);
					line(x1, y1, x2, y2);
				}
			}

			if (currentKfs[i].getName().equals("lines_out")) {
				for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
					float x1 = 0;
					float y1 = j * lineSpacing;
					float x2 = currentKfs[i].getTween(j).getPosition();
					float y2 = j * lineSpacing;
					float a = 255 * currentKfs[i].getTween(j).getSeekPosition();

					stroke(200, a);
					line(x1, y1, x2, y2);
				}
			}

			if (currentKfs[i].getName().equals("dataset_1_in")) {
				for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
					float x = data[0][j][0];
					float y = data[0][j][1];
					float size = pointSize
							* currentKfs[i].getTween(j).getPosition();
					float a = 255 * currentKfs[i].getTween(j).getPosition();

					noStroke();
					fill(255 * ((float) j / dataCount), 0, 0, a);
					ellipse(x, y, size, size);
				}
			}

			if (currentKfs[i].getName().equals("dataset_4_out")) {
				for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
					float x = data[3][j][0];
					float y = data[3][j][1];
					float size = pointSize
							* currentKfs[i].getTween(j).getPosition();
					float a = 255 * currentKfs[i].getTween(j).getPosition();

					noStroke();
					fill(255 * ((float) j / dataCount), 0, 0, a);
					ellipse(x, y, size, size);
				}
			}

			if (currentKfs[i].getName().equals("dataset_1_to_2")
					|| currentKfs[i].getName().equals("dataset_2_to_3")
					|| currentKfs[i].getName().equals("dataset_3_to_4")
					|| currentKfs[i].getName().equals("dataset_4_to_1")) {
				String kfName = currentKfs[i].getName();

				noStroke();
				fill(200, 200, 200,
						255 - (255 * currentKfs[i].getSeekPosition()));
				textFont(f, 36);
				kfName = kfName.replace("_", " ").toUpperCase();
				text(kfName, width / 2 - textWidth(kfName) / 2,
						height / 2 + 36 / 2);

				for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
					float x = ((Tween) currentKfs[i].getChild(j))
							.getPosition("x");
					float y = ((Tween) currentKfs[i].getChild(j))
							.getPosition("y");
					float size = pointSize;

					noStroke();
					fill(255 * ((float) j / dataCount), 0, 0);
					ellipse(x, y, size, size);

					String s = (int) (height - y) + "";

					if (!currentKfs[i].getChild(j).isPlaying()) {
						noStroke();
						fill(255 * ((float) j / dataCount), 0, 0);
						textFont(f, 12);
						text(s, x - textWidth(s) + 10, y - 10);
					}
				}
			}
		}

		String time = (int) tl.getTime() + " / " + (int) tl.getDuration();

		noStroke();
		fill(lerpColor(0xFF00FF00, 0xFFFF0000, tl.getSeekPosition()));
		textFont(f, 12);
		text(time, width - textWidth(time) - 10, height - 10);

		// if(tl.isPlaying())
		// mm.addFrame();
		//
		// if(tl.getTime() >= tl.getDuration()) {
		// mm.addFrame();
		// mm.finish();
		// exit();
		// }
	}

	public void keyPressed() {
		if (key == '1')
			tl.gotoAndPlay("dataset_1_to_2");
		else if (key == '2')
			tl.gotoAndPlay("dataset_2_to_3");
		else if (key == '3')
			tl.gotoAndPlay("dataset_3_to_4");
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
