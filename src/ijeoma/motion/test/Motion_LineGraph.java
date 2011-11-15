package ijeoma.motion.test;

import java.util.Arrays;
import java.util.Comparator;

import ijeoma.motion.*;
import ijeoma.motion.timeline.KeyFrame;
import ijeoma.motion.timeline.Timeline;
import ijeoma.motion.tween.*;

import processing.core.PApplet;
import processing.core.PFont;

//import processing.video.MovieMaker;

public class Motion_LineGraph extends PApplet {
	PFont f;

	Timeline tl;

	// MovieMaker mm;

	float pointSize = 5;
	float pointSpacing = 0;
	float pointMargin = 25;

	int lineCount = 10;
	float lineSpacing = (float) 200 / lineCount;

	Data[][] data;
	int dataCount = 100;
	float dataMin, dataMax;
	int dataSetCount = 4;
	int dataSetIndex = 0;

	class Data implements Comparator<Data>, Comparable<Data> {
		public float x, y;

		Data(float _x, float _y) {
			x = _x;
			y = _y;
		}

		public int compareTo(Data _d) {
			return (int) (y - _d.y);
		}

		public int compare(Data _d1, Data _d2) {
			return (int) (_d2.y - _d1.y);
		}
	}

	public void setup() {
		size(400, 200);
		frameRate(15);
		smooth();

		// mm = new MovieMaker(this, width, height, "drawing.mov");
		// mm = new MovieMaker(this, width, height, "drawing.mov", 15,
		// MovieMaker.ANIMATION, MovieMaker.HIGH);

		f = createFont("Arial", 12);
		textFont(f, 12);

		setupGraphData();
		setupGraphMotion();
	}

	void setupGraphData() {
		pointSpacing = (width - pointMargin * 2) / (dataCount - 1);

		dataMin = pointMargin;
		dataMax = height - pointMargin;

		data = new Data[dataSetCount][dataCount];

		for (int i = 0; i < dataCount; i++) {
			float x = i * pointSpacing + pointMargin;

			data[0][i] = new Data(x, random(dataMin, dataMax));
			data[1][i] = new Data(x, random(dataMin, dataMax));
		}

		arrayCopy(data[1], data[2]);
		// Arrays.sort(data[2]);

		arrayCopy(data[2], data[3]);
		// Arrays.sort(data[3]);
		// PApplet.reverse(data[3]);

		// println(data[2]);
		// println(data[3]);
	}

	void setupGraphMotion() {
		Motion.setup(this);

		tl = new Timeline();
		tl.setTimeMode(Timeline.SECONDS);
		// tl.addEventListener(this);

		KeyFrame linesin = new KeyFrame("lines_in", 0);
		for (int i = 0; i < lineCount; i++)
			linesin.addChild(new Tween("line_" + i, 0, width, 1f, 0,
					Tween.BOUNCE_OUT));

		linesin.setDuration(10f);

		KeyFrame data1in = new KeyFrame("dataset_1_in", .5f);

		for (int i = 0; i < dataCount; i++)
			data1in.addChild(new Tween("data_" + i, 0, 1, 1.0f, 0,
					Tween.BOUNCE_OUT));

		data1in.setDuration(1.5f);

		KeyFrame data1to2 = new KeyFrame("dataset_1_to_2", 2);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[0][i].x, data[1][i].x));
			t1.addProperty(new Property("y", data[0][i].y, data[1][i].y));
			t1.setDuration(1.5f);
			t1.setEasing(Tween.BOUNCE_OUT);
			data1to2.addChild(t1);
		}

		data1to2.setDuration(2f);

		KeyFrame data2to3 = new KeyFrame("dataset_2_to_3", 4f);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[1][i].x, data[2][i].x));
			t1.addProperty(new Property("y", data[1][i].y, data[2][i].y));
			t1.setDuration(1.5f);
			t1.setEasing(Tween.BOUNCE_OUT);
			data2to3.addChild(t1);
		}

		data2to3.setDuration(2f);

		KeyFrame data3to4 = new KeyFrame("dataset_3_to_4", 6f);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[2][i].x, data[3][i].x));
			t1.addProperty(new Property("y", data[2][i].y, data[3][i].y));
			t1.setDuration(1.5f);
			t1.setEasing(Tween.BOUNCE_OUT);
			data3to4.addChild(t1);
		}

		data3to4.setDuration(2f);

		KeyFrame data4to1 = new KeyFrame("dataset_4_to_1", 8f);

		for (int i = 0; i < dataCount; i++) {
			Tween t1 = new Tween();
			t1.setName("data_" + i);
			t1.addProperty(new Property("x", data[3][i].x, data[0][i].x));
			t1.addProperty(new Property("y", data[3][i].y, data[0][i].y));
			t1.setDuration(1.5f);
			t1.setEasing(Tween.BOUNCE_OUT);
			data4to1.addChild(t1);
		}

		data4to1.setDuration(2f);

		KeyFrame data1out = new KeyFrame("dataset_1_out", 10f);

		for (int i = 0; i < dataCount; i++)
			data1out.addChild(new Tween("data_" + i, 1, 0, .5f, 0,
					Tween.BOUNCE_IN));

		data1out.setDuration(1f);

		KeyFrame linesout = new KeyFrame("lines_out", 10f);
		for (int i = 0; i < lineCount; i++)
			linesout.addChild(new Tween("line_" + i, width, 0, 1, 0,
					Tween.BOUNCE_IN));

		tl.addKeyFrame(linesin);
		tl.addKeyFrame(data1in);
		tl.addKeyFrame(data1to2);
		tl.addKeyFrame(data2to3);
		tl.addKeyFrame(data3to4);
		tl.addKeyFrame(data4to1);
		tl.addKeyFrame(data1out);
		tl.addKeyFrame(linesout);
		tl.play();
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
					float x = data[0][j].x;
					float y = data[0][j].y;
					float size = pointSize
							* currentKfs[i].getTween(j).getPosition();
					float a = 255 * currentKfs[i].getTween(j).getPosition();

					if (j < dataCount - 1) {
						beginShape();
						stroke(255 * ((float) j / dataCount), 0, 0, a);
						vertex(x, y);
						stroke(255 * ((float) (j + 1) / dataCount), 0, 0, a);
						vertex(data[0][j + 1].x, data[0][j + 1].y);
						endShape();
					}

					noStroke();
					fill(255 * ((float) j / dataCount), 0, 0, a);
					ellipse(x, y, size, size);
				}
			}

			if (currentKfs[i].getName().equals("dataset_1_out")) {
				for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
					float x = data[0][j].x;
					float y = data[0][j].y;
					float size = pointSize
							* currentKfs[i].getTween(j).getPosition();
					float a = 255 * currentKfs[i].getTween(j).getPosition();

					if (j < dataCount - 1) {
						beginShape();
						stroke(255 * ((float) j / dataCount), 0, 0, a);
						vertex(x, y);
						stroke(255 * ((float) (j + 1) / dataCount), 0, 0, a);
						vertex(data[0][j + 1].x, data[0][j + 1].y);
						endShape();
					}

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
				// fill(200);
				fill(200, 200, 200,
						255 - (255 * currentKfs[i].getSeekPosition()));
				textFont(f, 36);
				kfName = kfName.replace("_", " ").toUpperCase();
				text(kfName, width / 2 - textWidth(kfName) / 2,
						height / 2 + 36 / 2);

				for (int j = 0; j < currentKfs[i].getTweenCount(); j++) {
					// float x = data[0][j].x;
					float x1 = currentKfs[i].getTween(j).getPosition("x");
					float y1 = currentKfs[i].getTween(j).getPosition("y");
					float a = 255 * currentKfs[i].getTween(j).getPosition();

					// if (j < dataCount - 1) {
					// float y2 = currentKfs[i].getTween(j + 1).getPosition(
					// "y");
					//
					// beginShape();
					// stroke(255 * ((float) j / dataCount), 0, 0);
					// vertex(x1, y1);
					// stroke(255 * ((float) (j + 1) / dataCount), 0, 0);
					// vertex(data[0][j + 1].x, y2);
					// endShape();
					// }

					noStroke();
					fill(255 * ((float) j / dataCount), 0, 0);
					ellipse(x1, y1, pointSize, pointSize);

					String s = (int) (height - y1) + "";

					noStroke();
					fill(255 * ((float) j / dataCount), 0, 0);
					textFont(f, 12);
					text(s, x1 - textWidth(s) + 10, y1 - 10);
				}
			}
		}

		String time = (int) tl.getTime() + " / " + (int) tl.getDuration();

		noStroke();
		fill(lerpColor(0xFF00FF00, 0xFFFF0000, tl.getSeekPosition()));
		textFont(f, 12);
		text(time, width - textWidth(time) - 10, height - 10);

		// if (tl.isPlaying())
		// mm.addFrame();
		//
		// if (tl.getTime() >= tl.getDuration()) {
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
		else if (key == '4')
			tl.gotoAndPlay("dataset_4_to_1");
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

	public void timelineEnded(Timeline _t) {
		println("asdf");
		println("asdf");
		// mm.finish();
	}
}
