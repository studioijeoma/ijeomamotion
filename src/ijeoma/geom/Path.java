/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package ijeoma.geom;

import ijeoma.math.Interpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Path {
	protected int segmentIndex = 0;
	protected int segmentPointIndex = 0;
	protected float segmentPosition = 0;
	protected float segmentPositionRange = 0;

	public float[] segmentLengths;

	protected int step = 5;
	protected float t = 1;

	protected float tension = 0;
	protected float bias = 0;

	protected ArrayList<PVector> points = new ArrayList<PVector>();

	protected float length = 0;

	boolean computed = false;

	public static String LINEAR = "linear";
	public static String COSINE = "cosine";
	public static String CUBIC = "cubic";
	public static String HERMITE = "hermite";

	protected String mode = LINEAR;

	boolean visible = true;

	boolean is3D = false;

	public Path() {
	}

	public Path(PVector[] points) {
		setPoints(points);
	}

	public Path(List<PVector> pointList) {
		setPoints(pointList);
	}

	public Path(PVector[] points, String mode) {
		setPoints(points);

		this.mode = mode;
	}

	public Path(PVector[] points, String mode, float tension, float bias) {
		setPoints(points);

		this.mode = mode;

		this.tension = tension;
		this.bias = bias;
	}

	public void draw(PGraphics g) {
		draw(g, PApplet.LINES, t);
	}

	public void draw(PGraphics g, int mode) {
		draw(g, mode, t);
	}

	public void draw(PGraphics g, float t) {
		this.t = t;
		draw(g, PApplet.LINES, t);
	}

	public void draw(PGraphics g, int mode, float t) {
		if (visible) {
			if (!computed)
				compute();

			int pointCount = (int) (points.size() * step * t);

			g.beginShape(mode);
			for (int i = 1; i <= pointCount; i++) {
				PVector p1 = getPointAt((float) (i - 1)
						/ (points.size() * step));
				PVector p2 = getPointAt((float) i / (points.size() * step));

				if (is3D) {
					g.vertex(p1.x, p1.y, p1.z);
					g.vertex(p2.x, p2.y, p2.z);
				} else {
					g.vertex(p1.x, p1.y);
					g.vertex(p2.x, p2.y);
				}
			}
			g.endShape();
		}
	}

	public void show() {
		visible = true;
	}

	public void hide() {
		visible = false;
	}

	public Path add(float x, float y) {
		return add(new PVector(x, y));
	}

	public Path add(PVector point) {
		computed = false;

		points.add(point);

		segmentPositionRange = 1f / (points.size() - 1);

		// compute();

		return this;
	}

	public Path addAll(PVector[] points) {
		computed = false;

		this.points.addAll(new ArrayList<PVector>(Arrays.asList(points)));

		segmentPositionRange = (1f / (this.points.size() - 1));

		// compute();

		return this;
	}

	public Path addAll(List<PVector> pointList) {
		computed = false;

		points.addAll(pointList);

		segmentPositionRange = (1f / (points.size() - 1));

		// compute();

		return this;
	}

	public void removeAll() {
		length = 0;

		points.clear();
	}

	public PVector get() {
		return getPointAt(t);
	}

	public PVector get(int index) {
		return points.get(index);
	}

	public PVector getPointAt(float position) {
		PVector point = new PVector();

		if (position < 1) {
			segmentPointIndex = PApplet.floor((points.size() - 1) * position);
			segmentPosition = PApplet.map((position % segmentPositionRange), 0,
					segmentPositionRange, 0, 1);
		} else {
			segmentPointIndex = (points.size() - 2);
			segmentPosition = 1;
		}

		PVector v1, v2, v3, v4;

		v2 = points.get(segmentPointIndex);
		v3 = points.get(segmentPointIndex + 1);

		v1 = v4 = new PVector();

		if (segmentPointIndex == 0) {
			PVector segmentBegin = points.get(0);
			PVector segmentEnd = points.get(1);
			PVector segmentSlope = PVector.sub(segmentEnd, segmentBegin);

			v1 = PVector.sub(segmentEnd, segmentSlope);
		} else {
			v1 = points.get(segmentPointIndex - 1);
		}

		if ((segmentPointIndex + 1) == points.size() - 1) {
			PVector segmentBegin = points.get(points.size() - 2);
			PVector segmentEnd = points.get(points.size() - 1);
			PVector segmentSlope = PVector.sub(segmentEnd, segmentBegin);

			v4 = PVector.add(segmentEnd, segmentSlope);
		} else {
			v4 = points.get((segmentPointIndex + 1) + 1);
		}

		if (mode.equals(LINEAR)) {
			point.x = Interpolator.linear(v2.x, v3.x, segmentPosition);
			point.y = Interpolator.linear(v2.y, v3.y, segmentPosition);
			if (is3D)
				point.z = Interpolator.linear(v2.z, v3.z, segmentPosition);
		} else if (mode.equals(COSINE)) {
			point.x = Interpolator.cosine(v2.x, v3.x, segmentPosition);
			point.y = Interpolator.cosine(v2.y, v3.y, segmentPosition);
			if (is3D)
				point.z = Interpolator.cosine(v2.z, v3.z, segmentPosition);
		} else if (mode.equals(CUBIC)) {
			point.x = Interpolator.cubic(v1.x, v2.x, v3.x, v4.x,
					segmentPosition);
			point.y = Interpolator.cubic(v1.y, v2.y, v3.y, v4.y,
					segmentPosition);
			if (is3D)
				point.z = Interpolator.cubic(v1.z, v2.z, v3.z, v4.z,
						segmentPosition);
		} else if (mode.equals(HERMITE)) {
			point.x = Interpolator.hermite(v1.x, v2.x, v3.x, v4.x,
					segmentPosition, tension, bias);
			point.y = Interpolator.hermite(v1.y, v2.y, v3.y, v4.y,
					segmentPosition, tension, bias);
			if (is3D)
				point.z = Interpolator.hermite(v1.z, v2.z, v3.z, v4.z,
						segmentPosition, tension, bias);
		}

		return point;
	}

	public PVector getPointAtLength(float l) {
		if (!computed)
			compute();

		PVector point = new PVector();

		if (points.size() > 1) {
			int pointCount = (int) (points.size() * step * t);

			float length = 0;
			int i = 0;

			while (length < l) {
				PVector p1 = getPointAt((float) (i - 1)
						/ (points.size() * step));
				PVector p2 = getPointAt((float) i / (points.size() * step));

				length += PVector.dist(p1, p2);

				point = p1;
				i++;
			}
		}

		return point;
	}

	public Path setPoints(PVector[] points) {
		return setPoints(new ArrayList<PVector>(Arrays.asList(points)));
	}

	public Path setPoints(List<PVector> points) {
		computed = false;

		points = new ArrayList<PVector>(points);

		for (PVector p : points)
			if (p.z != 0) {
				is3D = true;
				break;
			}

		segmentPositionRange = (1f / (points.size() - 1));

		// compute();

		return this;
	}

	public PVector[] getPoints() {
		return points.toArray(new PVector[points.size()]);
	}

	public List<PVector> getPointList() {
		return points;
	}

	public List<PVector> getPointList(int step) {
		return getPointList(step, t);
	}

	public List<PVector> getPointList(int step, float t) {
		if (!computed)
			compute();

		float[] psegmentLengths = segmentLengths;
		float plength = length;
		int pstep = step;
		float pt = this.t;

		this.step = step;
		this.t = t;

		compute();

		List<PVector> steppedPoints = new ArrayList<PVector>();

		float delta = step / length;
		int j = 1;
		int pointCount = (int) (points.size() * step * t);

		if (pointCount > 1) {
			for (float t1 = 0; t1 < 1.0; t1 += delta) {
				float t2 = t1 * length;

				while (t2 > segmentLengths[j])
					j++;

				// PVector p1 = getPointAt((float) (j - 1)
				// / (points.size() * step * t));
				// PVector p2 = getPointAt((float) j / (points.size() * step));
				// //cool effect!!!
				PVector p1 = getPointAt((float) (j - 1)
						/ (points.size() * step));
				PVector p2 = getPointAt((float) j / (points.size() * step));

				float t3 = (float) ((t2 - segmentLengths[j - 1]) / (segmentLengths[j] - segmentLengths[j - 1]));
				PVector p3 = PVector.lerp(p1, p2, t3);

				steppedPoints.add(p3);
			}
		}

		segmentLengths = psegmentLengths;
		length = plength;
		this.step = pstep;
		this.t = pt;

		return steppedPoints;
	}

	public int getPointCount() {
		return points.size();
	}

	public Path setPosition(float t) {
		this.t = t;
		return this;
	}

	public float getPosition() {
		return t;
	}

	public Path setStep(int step) {
		this.step = step;
		return this;
	}

	public float getStep() {
		return step;
	}

	public int getSegmentCount() {
		return segmentLengths.length;
	}

	public int getSegmentIndex() {
		return segmentIndex;
	}

	public int getSegmentPointIndex() {
		return segmentPointIndex;
	}

	public float getSegmentPosition() {
		return segmentPosition;
	}

	public float getLength() {
		if (!computed)
			compute();

		return length;
	}

	public Path setTension(float tension) {
		this.tension = tension;
		return this;
	}

	public float getTension() {
		return tension;
	}

	public Path setBias(float bias) {
		this.bias = bias;
		return this;
	}

	public float getBias() {
		return bias;
	}

	public Path setMode(String mode) {
		computed = false;

		this.mode = mode;
		return this;
	}

	public String getMode() {
		return mode;
	}

	public Path simplify(float tolerance) {
		PVector[] simplifiedPoints = Simplify.simplify(
				points.toArray(new PVector[points.size()]), tolerance);
		points = new ArrayList<PVector>(Arrays.asList(simplifiedPoints));
		return this;
	}

	public Path simplify(float tolerance, boolean highestQuality) {
		PVector[] simplifiedPoints = Simplify.simplify(
				points.toArray(new PVector[points.size()]), tolerance,
				highestQuality);
		points = new ArrayList<PVector>(Arrays.asList(simplifiedPoints));
		return this;
	}

	public void compute() {
		computeLength();

		computed = true;
	}

	public void computeLength() {
		if (points.size() > 1) {
			int pointCount = (int) (points.size() * step * t);

			length = 0;
			segmentLengths = new float[pointCount];

			for (int i = 1; i < pointCount; i++) {
				PVector p1 = getPointAt((float) (i - 1)
						/ (points.size() * step));
				PVector p2 = getPointAt((float) i / (points.size() * step));

				length += PVector.dist(p1, p2);
				segmentLengths[i] = length;
			}
		}
	}

	public float getLengthToSegment(PVector p1, PVector p2, PVector p3) {
		final float xDelta = p2.x - p1.x;
		final float yDelta = p2.y - p1.y;

		// if ((xDelta == 0) && (yDelta == 0)) {
		// throw new IllegalArgumentException(
		// "p1 and p2 cannot be the same point");
		// println(p1 + " and " + p2 + " cannot be the same point");
		// }

		final float u = ((p3.x - p1.x) * xDelta + (p3.y - p1.y) * yDelta)
				/ (xDelta * xDelta + yDelta * yDelta);

		final PVector closestPoint;

		if (u < 0) {
			closestPoint = p1;
		} else if (u > 1) {
			closestPoint = p2;
		} else {
			closestPoint = new PVector(p1.x + u * xDelta, p1.y + u * yDelta);
		}

		return closestPoint.dist(p3);
	}

	public boolean contains(PVector p) {
		boolean c = false;

		for (int i = 0; i < points.size() - 1; i++) {
			PVector v1 = points.get(i);
			PVector v2 = points.get(i + 1);
			float d = getLengthToSegment(v1, v2, p);
			if (d < 5) {
				c = true;
				continue;
			}
		}

		return c;
	}
}