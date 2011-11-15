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

import processing.core.PApplet;
import processing.core.PVector;

public class Path3D {
	PApplet parent;

	int pointCount = 5;
	int pathBegin, pathPVectorthLength;

	float tension = 0;
	float bias = 0;

	ArrayList<PVector> points = new ArrayList<PVector>();

	public static String LINEAR = "linear";
	public static String COSINE = "cosine";
	public static String CUBIC = "cubic";
	public static String HERMITE = "hermite";

	private String mode = CUBIC;

	public Path3D(PApplet _parent) {
		parent = _parent;
	}

	public Path3D(PApplet _parent, PVector[] _points) {
		parent = _parent;
		points.addAll(Arrays.asList(_points));
	}

	public Path3D(PApplet _parent, PVector[] _points, String _mode) {
		parent = _parent;
		points.addAll(Arrays.asList(_points));
		mode = _mode;
	}

	public Path3D(PApplet _parent, PVector[] _points, String _mode,
			float _tension, float _bias) {
		parent = _parent;
		points.addAll(Arrays.asList(_points));
		mode = _mode;

		tension = _tension;
		bias = _bias;
	}

	public void draw() {
		// points
		parent.fill(0);

		for (int i = 0; i < points.size(); i++) {
			parent.pushMatrix();
			parent.translate(points.get(i).x, points.get(i).y, points.get(i).z);
			parent.noStroke();
			parent.fill(255, 0, 0);
			parent.sphere(5);
			parent.popMatrix();
		}

		parent.noFill();
		parent.stroke(0);
		parent.beginShape();

		for (int i = 0; i < points.size() - 1; i++) {

			PVector v1, v2, v3, v4;

			v2 = points.get(i);
			v3 = points.get(i + 1);

			v1 = v4 = new PVector();

			if (i == 0) {
				PVector segmentBeginVertex = points.get(0);
				PVector segmentEndVertex = points.get(1);

				float segmentXSlope = (segmentEndVertex.x - segmentBeginVertex.x);
				float segmentYSlope = (segmentEndVertex.y - segmentBeginVertex.y);
				float segmentZSlope = (segmentEndVertex.z - segmentBeginVertex.z);

				v1 = new PVector(segmentBeginVertex.x - segmentXSlope,
						segmentBeginVertex.y - segmentYSlope,
						segmentBeginVertex.z - segmentZSlope);
			} else {
				v1 = points.get(i - 1);
			}

			if ((i + 1) == points.size() - 1) {
				PVector segmentBeginVertex = points.get(points.size() - 2);
				PVector segmentEndVertex = points.get(points.size() - 1);

				float segmentXSlope = (segmentEndVertex.x - segmentBeginVertex.x);
				float segmentYSlope = (segmentEndVertex.y - segmentBeginVertex.y);
				float segmentZSlope = (segmentEndVertex.z - segmentBeginVertex.z);

				v4 = new PVector(segmentEndVertex.x + segmentXSlope,
						segmentEndVertex.y + segmentYSlope,
						segmentBeginVertex.z + segmentZSlope);
			} else {
				v4 = points.get((i + 1) + 1);
			}

			for (float j = 0; j < 1; j += .01) {
				float x = 0, y = 0, z = 0;

				if (mode.equals(LINEAR)) {
					x = Interpolator.linear(v2.x, v3.x, j);
					y = Interpolator.linear(v2.y, v3.y, j);
					z = Interpolator.linear(v2.z, v3.z, j);
				} else if (mode.equals(COSINE)) {
					x = Interpolator.cosine(v2.x, v3.x, j);
					y = Interpolator.cosine(v2.y, v3.y, j);
					z = Interpolator.cosine(v2.z, v3.z, j);
				} else if (mode.equals(CUBIC)) {
					x = Interpolator.cubic(v1.x, v2.x, v3.x, v4.x, j);
					y = Interpolator.cubic(v1.y, v2.y, v3.y, v4.y, j);
					z = Interpolator.cubic(v1.z, v2.z, v3.z, v4.z, j);
				} else if (mode.equals(HERMITE)) {
					x = Interpolator.hermite(v1.x, v2.x, v3.x, v4.x, j,
							tension, bias);
					y = Interpolator.hermite(v1.y, v2.y, v3.y, v4.y, j,
							tension, bias);
					z = Interpolator.hermite(v1.z, v2.z, v3.z, v4.z, j,
							tension, bias);
				}

				parent.point(x, y, z);
			}
		}

		parent.endShape();
	}

	public void addPoint(PVector _point) {
		points.add(_point);
	}

	public PVector getPoint(float _position) {
		float pathX = 0, pathY = 0, pathZ = 0;

		int segmentBeginVertexIndex = 0;

		if (_position < 1)
			segmentBeginVertexIndex = PApplet.floor((points.size() - 1)
					* _position);
		else
			segmentBeginVertexIndex = (points.size() - 2);

		float segmentPositionRange = (1f / (points.size() - 1));

		float segmentPosition = 0;

		if (_position < 1)
			segmentPosition = PApplet.map((_position % segmentPositionRange),
					0, segmentPositionRange, 0, 1);
		else
			segmentPosition = 1;

		// PApplet.println("pathPosition = " + _position);
		// PApplet.println("segmentPositionRange = " + segmentPositionRange);
		// PApplet.println("segmentBeginVertexIndex = " +
		// segmentBeginVertexIndex);
		// PApplet.println("segmentPosition = " + segmentPosition);

		PVector v1, v2, v3, v4;

		v2 = points.get(segmentBeginVertexIndex);
		v3 = points.get(segmentBeginVertexIndex + 1);

		v1 = v4 = new PVector();

		if (segmentBeginVertexIndex == 0) {
			PVector segmentBeginVertex = points.get(0);
			PVector segmentEndVertex = points.get(1);

			float segmentXSlope = (segmentEndVertex.x - segmentBeginVertex.x);
			float segmentYSlope = (segmentEndVertex.y - segmentBeginVertex.y);

			v1 = new PVector(segmentBeginVertex.x - segmentXSlope,
					segmentBeginVertex.y - segmentYSlope);
		} else {
			v1 = points.get(segmentBeginVertexIndex - 1);
		}

		if ((segmentBeginVertexIndex + 1) == points.size() - 1) {
			PVector segmentBeginVertex = points.get(points.size() - 2);
			PVector segmentEndVertex = points.get(points.size() - 1);

			float segmentXSlope = (segmentEndVertex.x - segmentBeginVertex.x);
			float segmentYSlope = (segmentEndVertex.y - segmentBeginVertex.y);

			v4 = new PVector(segmentEndVertex.x + segmentXSlope,
					segmentEndVertex.y + segmentYSlope);
		} else {
			v4 = points.get((segmentBeginVertexIndex + 1) + 1);
		}

		if (mode.equals(LINEAR)) {
			pathX = Interpolator.linear(v2.x, v3.x, segmentPosition);
			pathY = Interpolator.linear(v2.y, v3.y, segmentPosition);
			pathZ = Interpolator.linear(v2.z, v3.z, segmentPosition);
		} else if (mode.equals(COSINE)) {
			pathX = Interpolator.cosine(v2.x, v3.x, segmentPosition);
			pathY = Interpolator.cosine(v2.y, v3.y, segmentPosition);
			pathZ = Interpolator.cosine(v2.z, v3.z, segmentPosition);
		} else if (mode.equals(CUBIC)) {
			pathX = Interpolator.cubic(v1.x, v2.x, v3.x, v4.x, segmentPosition);
			pathY = Interpolator.cubic(v1.y, v2.y, v3.y, v4.y, segmentPosition);
			pathZ = Interpolator.cubic(v1.z, v2.z, v3.z, v4.z, segmentPosition);
		} else if (mode.equals(HERMITE)) {
			pathX = Interpolator.hermite(v1.x, v2.x, v3.x, v4.x,
					segmentPosition, tension, bias);
			pathY = Interpolator.hermite(v1.y, v2.y, v3.y, v4.y,
					segmentPosition, tension, bias);
			pathZ = Interpolator.hermite(v1.z, v2.z, v3.z, v4.z,
					segmentPosition, tension, bias);
		}

		return new PVector(pathX, pathY, pathZ);
	}

	public void setPoints(PVector[] _points) {
		points = new ArrayList<PVector>();
		points.addAll(Arrays.asList(_points));
	}

	public PVector[] getPoints() {
		return points.toArray(new PVector[points.size()]);
	}

	public void setTension(float _tension) {
		tension = _tension;
	}

	public float getTension() {
		return tension;
	}

	public void setBias(float _bias) {
		bias = _bias;
	}

	public float getBias() {
		return bias;
	}

	public void setMode(String _mode) {
		mode = _mode;
	}
}