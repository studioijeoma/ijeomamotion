package ijeoma.motion.tween.test;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.*; 
import processing.data.XML;


public class TweenSequence_GML extends PApplet {
	TweenSequence ts;

	PFont font;

	boolean loadingGML = true;
	float x, y, z;

	PVector[] points;

	@Override
	public void setup() {
		size(800, 580);

		frameRate(60);
		smooth();

		font = createFont("Arial", 12);
		textFont(font);

		Motion.setup(this);

		x = y = 0;
		ts = new TweenSequence();

		// loadGML("818.gml");
		loadGML("http://000000book.com/data/42481.gml");
		// loadGML("http://000000book.com/random.gml");

		ts.play();
	}

	public void loadGML(String _fileName) {
		XML gml = loadXML(_fileName);
		XML[] pts = gml.getChildren("tag/drawing/stroke/pt");

		points = new PVector[pts.length];

		for (int i = 0; i < points.length; i++) {
			float x = Float.parseFloat(pts[i].getChild("x").getContent())
					* width / 2;
			float y = Float.parseFloat(pts[i].getChild("y").getContent())
					* height / 2;
			float z = Float.parseFloat(pts[i].getChild("z").getContent());

			points[i] = new PVector(x, y, z);
		}

		float[] lengths = new float[points.length - 1];
		float maxLength = 0;

		for (int i = 0; i < points.length - 1; i++) {
			lengths[i] = PVector.dist(points[i + 1], points[i]);
			maxLength = max(maxLength, lengths[i]);
		}

		ts.removeAll();

		for (int i = 0; i < points.length - 1; i++) {
			ts.add(new Tween((lengths[i] / maxLength) * 15)
					.add(this, "x", points[i].x).add(this, "y", points[i].y)
					.add(this, "z", points[i].z));
		}
	}

	@Override
	public void draw() {
		background(0);

		// if (!loadingGML) {
		Tween[] tweens = ts.getTweens();

		if (ts.getCurrentChildIndex() > 1)
			for (int i = 0; i < ts.getCurrentChildIndex() - 1; i++) {
				stroke(255);
				strokeWeight(tweens[i].getDuration() / 15f * 25);
				noFill();
				beginShape();
				vertex(points[i].x, points[i].y);
				vertex(points[i + 1].x, points[i + 1].y);
				endShape();
			}

		ellipse(x, y, 10, 10);

		noStroke();
		fill(0);
		String time = ts.getTime() + " / " + ts.getDuration();
		text(time, width - textWidth(time) - 10, height - 10);
		// }
	}

	public void tweenSequenceStarted(TweenSequence _ts) {
	}

	public void tweenSequenceEnded(TweenSequence _ts) {
		// loadGML("http://000000book.com/random.gml");
		// println("ts.getChildCount =" + ts.getChildCount());
		// println("ts.getDuration =" + ts.getDuration());
		//
		// delay(1000);
		//
		// ts.play();
	}

	@Override
	public void keyPressed() {
		if (key == ' ')
			ts.play();
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
		ts.seek(norm(mouseX, 0, width));
	}
 }
