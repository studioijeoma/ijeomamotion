package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.Property;
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.TweenSequence;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.xml.XMLElement;

public class TweenSequence_GML extends PApplet {
	TweenSequence ts;

	PFont font;

	boolean loadingGML = true;

	public void setup() {
		size(800, 580, P3D);

		frameRate(60);
		smooth();

		font = createFont("Arial", 12);
		textFont(font);

		Motion.setup(this);

		ts = new TweenSequence();

		loadGML("818.gml");
		// loadGML("http://www.000000book.com/data/818.gml");
		// loadGML("http://000000book.com/random.gml");

		ts.play();
	}

	public void loadGML(String _fileName) {
		println("begin");

		loadingGML = true;

		XMLElement gml = new XMLElement(this, _fileName);
		XMLElement strokeXML = gml.getChild("tag/drawing/stroke");

		ts.removeChildren();

		PVector[] points = new PVector[strokeXML.getChildCount()];

		for (int i = 0; i < points.length; i++) {
			float x = Float.parseFloat(strokeXML.getChild(i).getChild(0)
					.getContent())
					* width / 2;
			float y = Float.parseFloat(strokeXML.getChild(i).getChild(1)
					.getContent())
					* height / 2;
			float z = Float.parseFloat(strokeXML.getChild(i).getChild(2)
					.getContent());

			points[i] = new PVector(x, y, z);
		}

		float[] lengths = new float[points.length - 1];
		float maxLength = 0;

		for (int i = 0; i < points.length - 1; i++) {
			lengths[i] = PVector.dist(points[i + 1], points[i]);
			maxLength = max(maxLength, lengths[i]);
		}

		for (int i = 0; i < points.length - 1; i++) {
			Tween t = new Tween();
			t.setName("point_" + i + "_" + (i + 1));
			t.addProperty(new Property(this, "x", points[i].x, points[i + 1].x));
			t.addProperty(new Property(this, "y", points[i].y, points[i + 1].y));
			t.addProperty(new Property(this, "z", points[i].z, points[i + 1].z));

			t.setDuration((lengths[i] / maxLength) * 5);

			ts.appendChild(t);
		}

		loadingGML = false;

		println("end");
	}

	public void draw() {
		background(0);

		if (!loadingGML) {
			Tween[] tweens = ts.getTweens();

			strokeWeight(5);
			stroke(255);
			noFill();
			beginShape();
			for (int i = 0; i < ts.getCurrentChildIndex(); i++) {
				strokeWeight(tweens[i].getDuration() * 2);
				vertex(tweens[i].getPosition("x"), tweens[i].getPosition("y"),
						tweens[i].getPosition("z"));
			}
			endShape();

			strokeWeight(1);
			stroke(lerpColor(color(0, 255, 0), color(255, 0, 0),
					ts.getSeekPosition()));
			line(ts.getSeekPosition() * width, 0, ts.getSeekPosition() * width,
					height);

			noStroke();
			if (ts.isPlaying())
				fill(0, 255, 0);
			else
				fill(255, 0, 0);
			String timeAsString = ts.getTime() + " / " + ts.getDuration();
			text(timeAsString, width - textWidth(timeAsString) - 10,
					height - 10);
		}
	}

	public void tweenSequenceStarted(TweenSequence _ts) {
		println("tweenSequenceStarted");
	}

	public void tweenSequenceEnded(TweenSequence _ts) {

		println("tweenSequenceEnded");
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
