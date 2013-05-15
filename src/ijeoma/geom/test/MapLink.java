package ijeoma.geom.test;

//import ijeoma.geo.geom.Rect;
import ijeoma.geom.Path;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.NumberProperty;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

class MapLink extends Path {
	// Map map;

	String id;

	PVector ll1, ll2;

	int[] colors = new int[2];

	float w = 1;
	float h = 0;

	float alpha;

	PVector midPoint;
	// Rect bounds;

	// LinkData data;

	Tween positionTween, alphaTween;

	boolean is3D = false;

	// MapLink(Map map, PVector ll1, PVector ll2, float h, LinkData data) {
	// super();
	//
	// this.map = map;
	//
	// this.id = data.countries;
	//
	// this.ll1 = ll1;
	// this.ll2 = ll2;
	//
	// this.h = h;
	//
	// this.data = data;
	//
	// positionTween = new Tween(50).add(this, "t", 1);
	// alphaTween = new Tween(50).add(this, "alpha", 255);
	//
	// compute();
	// }

	MapLink() {
		super();

		this.h = 200;

		positionTween = new Tween(50).add(this, "t", 1);
		alphaTween = new Tween(50).add(this, "alpha", 255);

		compute();
	}

	public void update() {
		computed = false;
		compute();
	}

	public void draw(PGraphics g) {
		if (visible) {
			if (!computed)
				compute();

			// ColorGradient cg = new ColorGradient();
			// cg.addColorAt(0, TColor.newARGB(colors[0]));
			// cg.addColorAt(points.size() * .5f, TColor.newARGB(colors[1]));
			// cg.addColorAt(points.size(), TColor.newARGB(colors[2]));

			// ColorGradient cg = new ColorGradient();
			// for (int i = 1; i <= colors.length; i++)
			// cg.addColorAt(
			// points.size() * ((i - 1f) / (colors.length - 1f)),
			// TColor.newARGB(colors[i - 1]));
			//
			// ColorList colors = cg.calcGradient(0, points.size());

			// t = 1;
			// PApplet.println(t);
			// PApplet.println(alpha);
			g.stroke(255, 255 * alpha);
			g.strokeWeight(4);
			g.noFill();
			g.beginShape(g.POINTS);
			for (int i = 1; i < points.size() * t; i++) {
				PVector p1 = points.get(i - 1);
				PVector p2 = points.get(i);

				// g.stroke(colors.get(i - 1).toARGB(), alpha);
				g.vertex(p1.x, p1.y);
				// g.stroke(colors.get(i).toARGB(), alpha);
				g.vertex(p2.x, p2.y);
			}
			g.endShape();

			// int pointCount = (int) (points.size() * step * t);

			// g.beginShape(g.POINTS);
			// for (int i = 1; i <= pointCount; i++) {
			// PVector p1 = getPointAt((float) (i - 1)
			// / (points.size() * step));
			// PVector p2 = getPointAt((float) i / (points.size() * step));
			//
			// g.vertex(p1.x, p1.y);
			// g.vertex(p2.x, p2.y);
			// }
			// g.endShape();
		}
	}

	public void fadeIn(float duration, float delay) {
		alpha = 0;
		t = 1;

		NumberProperty p = alphaTween.getNumber("alpha");
		p.setEnd(1f);
		alphaTween.setDuration(duration).delay(delay)// .onStart(this, "show")
				.play();
	}

	public void fadeOut(float duration, float delay) {
		alpha = t = 1;

		NumberProperty p = alphaTween.getNumber("alpha");
		p.setEnd(0f);
		alphaTween.setDuration(duration).delay(delay)// .onEnd(this, "hide")
				.play();
	}

	public void animateIn(float duration, float delay) {
		alpha = 1;
		t = 0;

		NumberProperty p = positionTween.getNumber("t");
		p.setEnd(1f);
		positionTween.setDuration(duration).delay(delay).play();
	}

	public void animateOut(float duration, float delay) {
		alpha = t = 1;

		NumberProperty p = positionTween.getNumber("t");
		p.setEnd(0f);
		positionTween.setDuration(duration).delay(delay).play();
	}

	public void show() {
		alpha = t = 1;
		visible = true;
	}

	public void hide() {
		t = 0;
		alpha = 0;
		visible = false;
	}

	public void compute() {
		computePoints();
		computeLength();
		computeMidPoint();
		computeBounds();

		computed = true;
	}

	public void computePoints() {
		// PVector p1 = map.worldPoint(ll1.x, ll1.y);
		// PVector p2 = map.worldPoint(ll2.x, ll2.y);
		PVector p1 = new PVector(0, 300);
		PVector p2 = new PVector(800, 300);
		int steps = 50;

		this.removeAll();

		for (int i = 0; i <= steps; i++) {
			float s = (float) i / steps;
			float x = PApplet.lerp(p1.x, p2.x, s);
			float y = PApplet.lerp(p1.y, p2.y, s) - h
					* PApplet.sin(s * PApplet.PI);
			float z = 0;

			add(new PVector(x, y, z));
		}
	}

	public void computeBounds() {
		PVector topLeft = points.get(0);
		PVector bottomRight = points.get(0);

		for (PVector v : points) {
			topLeft = new PVector(PApplet.min(topLeft.x, v.x), PApplet.min(
					topLeft.y, v.y));
			bottomRight = new PVector(PApplet.max(bottomRight.x, v.x),
					PApplet.max(bottomRight.y, v.y));
		}

		// bounds = new Rect(topLeft, bottomRight);
	}

	public void computeMidPoint() {
		PVector p1 = points.get(0);
		PVector p2 = points.get(points.size() - 1);

		int steps = (int) (25 * .5f * t);
		int i = (int) (steps * .5f);
		float s = (float) i / steps;
		float x = PApplet.lerp(p1.x, p2.x, s);
		float y = (is3D) ? PApplet.lerp(p1.y, p2.y, s) : PApplet.lerp(p1.y,
				p2.y, s) - h * PApplet.sin(s * PApplet.PI);
		float z = (is3D) ? h * PApplet.sin(s * PApplet.PI) : 0;

		midPoint = new PVector(x, y, z);
	}

	public void setHeight(float h) {
		computed = this.h == h;
		this.h = h;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}
}