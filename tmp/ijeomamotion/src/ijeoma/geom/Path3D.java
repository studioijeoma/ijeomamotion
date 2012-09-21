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
import processing.core.PGraphics;
import processing.core.PVector;

public class Path3D {
	int segmentIndex = 0;
	int segmentPointIndex = 0;
	float segmentPosition = 0;
	float segmentPositionRange = 0;

	float tension = 0;
	float bias = 0;

	PVector point = new PVector();
	ArrayList<PVector> points = new ArrayList<PVector>();

	public static String LINEAR = "linear";
	public static String COSINE = "cosine";
	public static String CUBIC = "cubic";
	public static String HERMITE = "hermite";

	private String mode = CUBIC;

	public Path3D() {
	}

	public Path3D(PVector[] _points) {
		set(_points);
	}

	public Path3D(PVector[] _points, String _mode) {
		set(_points);

		mode = _mode;
	}

	public Path3D(PVector[] _points, String _mode, float _tension, float _bias) {
		set(_points);

		mode = _mode;

		tension = _tension;
		bias = _bias;
	}

	public void draw(PGraphics _g) {
		_g.beginShape();

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

				_g.vertex(x, y, z);
			}
		}

		_g.endShape();
	}

	public void add(PVector _point) {
		points.add(_point);
	}

	public void add(float _x, float _y, float _z) {
		add(new PVector(_x, _y, _z));
	}

	public PVector get(float _position) {
		if (_position < 1) {
			segmentPointIndex = PApplet.floor((points.size() - 1) * _position);
			segmentPosition = PApplet.map((_position % segmentPositionRange),
					0, segmentPositionRange, 0, 1);
		} else {
			segmentPointIndex = (points.size() - 2);
			segmentPosition = 1;
		}
		// PApplet.println("pathPosition = " + _position);
		// PApplet.println("segmentPositionRange = " + segmentPositionRange);
		// PApplet.println("segmentBeginVertexIndex = " + segmentVertexIndex);
		// PApplet.println("segmentPosition = " + segmentPosition);

		PVector v1, v2, v3, v4;

		v2 = points.get(segmentPointIndex);
		v3 = points.get(segmentPointIndex + 1);

		v1 = v4 = new PVector();

		if (segmentPointIndex == 0) {
			PVector segmentBeginVertex = points.get(0);
			PVector segmentEndVertex = points.get(1);

			float segmentXSlope = (segmentEndVertex.x - segmentBeginVertex.x);
			float segmentYSlope = (segmentEndVertex.y - segmentBeginVertex.y);

			v1 = new PVector(segmentBeginVertex.x - segmentXSlope,
					segmentBeginVertex.y - segmentYSlope);
		} else {
			v1 = points.get(segmentPointIndex - 1);
		}

		if ((segmentPointIndex + 1) == points.size() - 1) {
			PVector segmentBeginVertex = points.get(points.size() - 2);
			PVector segmentEndVertex = points.get(points.size() - 1);

			float segmentXSlope = (segmentEndVertex.x - segmentBeginVertex.x);
			float segmentYSlope = (segmentEndVertex.y - segmentBeginVertex.y);

			v4 = new PVector(segmentEndVertex.x + segmentXSlope,
					segmentEndVertex.y + segmentYSlope);
		} else {
			v4 = points.get((segmentPointIndex + 1) + 1);
		}

		if (mode.equals(LINEAR)) {
			point.x = Interpolator.linear(v2.x, v3.x, segmentPosition);
			point.y = Interpolator.linear(v2.y, v3.y, segmentPosition);
			point.z = Interpolator.linear(v2.z, v3.z, segmentPosition);
		} else if (mode.equals(COSINE)) {
			point.x = Interpolator.cosine(v2.x, v3.x, segmentPosition);
			point.y = Interpolator.cosine(v2.y, v3.y, segmentPosition);
			point.z = Interpolator.cosine(v2.z, v3.z, segmentPosition);
		} else if (mode.equals(CUBIC)) {
			point.x = Interpolator.cubic(v1.x, v2.x, v3.x, v4.x,
					segmentPosition);
			point.y = Interpolator.cubic(v1.y, v2.y, v3.y, v4.y,
					segmentPosition);
			point.z = Interpolator.cubic(v1.z, v2.z, v3.z, v4.z,
					segmentPosition);
		} else if (mode.equals(HERMITE)) {
			point.x = Interpolator.hermite(v1.x, v2.x, v3.x, v4.x,
					segmentPosition, tension, bias);
			point.y = Interpolator.hermite(v1.y, v2.y, v3.y, v4.y,
					segmentPosition, tension, bias);
			point.z = Interpolator.hermite(v1.z, v2.z, v3.z, v4.z,
					segmentPosition, tension, bias);
		}

		return point;
	}

	public void set(PVector[] _points) {
		point = new PVector();

		points = new ArrayList<PVector>();
		points.addAll(Arrays.asList(_points));

		segmentPositionRange = (1f / (points.size() - 1));
	}

	public PVector[] get() {
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

	public int getSegmentIndex() {
		return segmentIndex;
	}

	public int getSegmentVertexIndex() {
		return segmentPointIndex;
	}

	public float getSegmentPosition() {
		return segmentPosition;
	}
}