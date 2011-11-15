package ijeoma.motion.tween.test;
//package ijeoma.motion.tween.path.test;
//
//import ijeoma.geom.path.Path3D;
//import ijeoma.motion.Motion;
//import ijeoma.motion.tween.TweenSequence;
//import ijeoma.motion.tween.path.TweenPath3D;
//import processing.core.PApplet;
//import processing.core.PFont;
//import processing.core.PVector;
//import processing.xml.XMLElement;
//
//public class TweenPath3D_GML extends PApplet {
//	int RED = color(255, 0, 0);
//	int GREEN = color(0, 255, 0);
//
//	PFont font;
//
//	Path3D p;
//	TweenPath3D tp;
//
//	boolean loadingGML = true;
//
//	public void setup() {
//		size(800, 580, P3D);
//
//		frameRate(60);
//		smooth();
//
//		font = createFont("Arial", 12);
//		textFont(font);
//		textMode(SCREEN);
//
//		Motion.setup(this);
//
//		loadGML("http://000000book.com/data/21725.gml");
//	}
//
//	public void loadGML(String _fileName) {
//		loadingGML = true;
//
//		XMLElement gml = new XMLElement(this, _fileName);
//		XMLElement strokeXML = gml.getChild("tag/drawing/stroke");
//
//		PVector[] points = new PVector[strokeXML.getChildCount()];
//
//		println(strokeXML.getChildCount());
//
//		// float[] lengths = new float[points.length - 1];
//		// float maxLength = 0;
//
//		for (int i = 0; i < points.length; i++) {
//			float x = Float.parseFloat(strokeXML.getChild(i).getChild(0)
//					.getContent())
//					* width / 2;
//			float y = Float.parseFloat(strokeXML.getChild(i).getChild(1)
//					.getContent())
//					* height / 2;
//			float z = Float.parseFloat(strokeXML.getChild(i).getChild(2)
//					.getContent());
//
//			points[i] = new PVector(x, y, z);
//
//			// lengths[i] = PVector.dist(points[i + 1], points[i]);
//			// maxLength = max(maxLength, lengths[i]);
//		}
//
//		p = new Path3D(this, points, Path3D.LINEAR);
//		tp = new TweenPath3D(p, 0, 1, 100);
//		tp.play();
//
//		loadingGML = false;
//	}
//
//	public void draw() {
//		background(255);
//
//		float pathResolutionStep = 1f / p.getVertexCount();
//
//		if (!loadingGML) {
//			// p.draw();
//
//			stroke(0);
//			noFill();
//			beginShape();
//			for (float i = 0; i < tp.getPosition(); i += pathResolutionStep) {
//				PVector v = p.getVertex(i);
//				vertex(v.x, v.y, v.z);
//			}
//			endShape();
//
//			int seekColor = lerpColor(GREEN, RED, tp.getSeekPosition());
//
//			stroke(seekColor);
//			line(tp.getSeekPosition() * width, 0, tp.getSeekPosition() * width,
//					height);
//
//			String timeAsString = tp.getTime() + " / " + tp.getDuration();
//
//			noStroke();
//			fill(seekColor);
//			text(timeAsString, width - textWidth(timeAsString) - 10,
//					height - 10);
//		}
//	}
//
//	public void keyPressed() {
//		if (key == ' ')
//			tp.play();
//	}
//
//	public void mousePressed() {
//		tp.pause();
//	}
//
//	public void mouseReleased() {
//		tp.resume();
//	}
//
//	public void mouseDragged() {
//		tp.seek(norm(mouseX, 0, width));
//	}
// }
