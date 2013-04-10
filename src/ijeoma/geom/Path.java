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

	protected int steps = 1;

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
		draw(g, PApplet.LINE, steps, 1);
	}

	public void draw(PGraphics g, float t) {
		draw(g, PApplet.LINE, steps, t);
	}

	public void draw(PGraphics g, int steps, float t) {
		draw(g, PApplet.LINE, steps, t);
	}

	public void draw(PGraphics g, int mode, int steps, float t) {
		int pointCount = (int) (points.size() * steps * t);

		if (mode == PApplet.LINE)
			g.beginShape();
		else
			g.beginShape(mode);

		for (int i = 0; i <= pointCount; i += steps) {
			PVector p1 = getPointAt((float) i / pointCount);

			if (is3D)
				g.vertex(p1.x, p1.y, p1.z);
			else
				g.vertex(p1.x, p1.y);
		}
		g.endShape();
	}

	public Path add(float x, float y) {
		return add(new PVector(x, y));
	}

	public Path add(PVector point) {
		points.add(point);

		segmentPositionRange = (1f / (points.size() - 1));

		return this;
	}

	public Path addAll(PVector[] points) {
		this.points.addAll(new ArrayList<PVector>(Arrays.asList(points)));

		segmentPositionRange = (1f / (this.points.size() - 1));

		return this;
	}

	public Path add(List<PVector> pointList) {
		points.addAll(pointList);

		segmentPositionRange = (1f / (points.size() - 1));

		return this;
	}

	public void removeAll() {
		points.clear();
	}

	public PVector get(int index) {
		return points.get(index);
	}

	public PVector getPointAt(float position) {
		PVector point = new PVector();

		if (!computed)
			compute();

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

	public Path setPoints(PVector[] points) {
		return setPoints(new ArrayList<PVector>(Arrays.asList(points)));
	}

	public Path setPoints(List<PVector> points) {
		points = new ArrayList<PVector>(points);

		for (PVector p : points)
			if (p.z != 0) {
				is3D = true;
				break;
			}

		segmentPositionRange = (1f / (points.size() - 1));

		compute();

		return this;
	}

	public PVector[] getPoints() {
		return points.toArray(new PVector[points.size()]);
	}

	public List<PVector> getPointList() {
		return points;
	}

	public int getPointCount() {
		return points.size();
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
		length = 0;

		for (int i = 0; i < points.size() - 1; i++) {
			PVector d1 = points.get(i);
			PVector d2 = points.get(i + 1);

			length += PApplet.dist(d1.x, d1.y, d2.x, d2.y);
		}
	}

	public void setTension(float tension) {
		this.tension = tension;
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
		this.mode = mode;
		return this;
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
		return length;
	}

	// public float getHeading2DAt(float t) {
	// // var angle = Math.atan2(vectorA.y - vectorB.y, vectorA.x - vectorB.x)
	// // var angle = Math.atan2(vectorA.y - vectorB.y, vectorA.x - vectorB.x)
	// return 0;
	// }
	//
	// public float getHeadingAt(float t) {
	// // // Make up two vectors
	// // PVector v1 = new PVector(5, -20, -14);
	// // PVector v2 = new PVector(-1, 3, 2);
	// //
	// // // Store some information about them for below
	// // float dot = v1.dot(v2);
	// // float lengthA = v1.;
	// // float lengthB = v2.;
	// //
	// // // Now to find the angle
	// // float theta = acos( dot / (lengthA * lengthB) ); // Theta = 3.06
	// // radians or 175.87 degrees
	// //
	// // return theta;
	// return 0;
	// }

	// public float[] getBounds() {
	// return float[] {};//pointMin.x, pointMin.y, width,height}
	// }
}