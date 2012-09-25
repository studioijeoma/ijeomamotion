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
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Path2D extends Path {
	public Path2D() {
	}

	public Path2D(PVector[] _points) {
		super(_points);
	}

	public Path2D(PVector[] _points, String _mode) {
		super(_points, _mode);
	}

	public Path2D(PVector[] _points, String _mode, float _tension, float _bias) {
		super(_points, _mode, _tension, _bias);
	}

	public PVector get(float _position) {
		PVector point = new PVector();

		if (!computed)
			compute();

		if (_position < 1) {
			segmentPointIndex = PApplet.floor((points.size() - 1) * _position);
			segmentPosition = PApplet.map((_position % segmentPositionRange),
					0, segmentPositionRange, 0, 1);
		} else {
			segmentPointIndex = (points.size() - 2);
			segmentPosition = 1;
		}

		PVector v1, v2, v3, v4;

		v2 = points.get(segmentPointIndex);
		v3 = points.get(segmentPointIndex + 1);

		v1 = v4 = new PVector();

		if (segmentPointIndex == 0) {
			PVector segmentBeginPoint = points.get(0);
			PVector segmentEndPoint = points.get(1);

			float segmentXSlope = (segmentEndPoint.x - segmentBeginPoint.x);
			float segmentYSlope = (segmentEndPoint.y - segmentBeginPoint.y);

			v1 = new PVector(segmentBeginPoint.x - segmentXSlope,
					segmentBeginPoint.y - segmentYSlope);
		} else {
			v1 = points.get(segmentPointIndex - 1);
		}

		if ((segmentPointIndex + 1) == points.size() - 1) {
			PVector segmentBeginPoint = points.get(points.size() - 2);
			PVector segmentEndPoint = points.get(points.size() - 1);

			float segmentXSlope = (segmentEndPoint.x - segmentBeginPoint.x);
			float segmentYSlope = (segmentEndPoint.y - segmentBeginPoint.y);

			v4 = new PVector(segmentEndPoint.x + segmentXSlope,
					segmentEndPoint.y + segmentYSlope);
		} else {
			v4 = points.get((segmentPointIndex + 1) + 1);
		}

		if (mode.equals(LINEAR)) {
			point.x = Interpolator.linear(v2.x, v3.x, segmentPosition);
			point.y = Interpolator.linear(v2.y, v3.y, segmentPosition);
		} else if (mode.equals(COSINE)) {
			point.x = Interpolator.cosine(v2.x, v3.x, segmentPosition);
			point.y = Interpolator.cosine(v2.y, v3.y, segmentPosition);
		} else if (mode.equals(CUBIC)) {
			point.x = Interpolator.cubic(v1.x, v2.x, v3.x, v4.x,
					segmentPosition);
			point.y = Interpolator.cubic(v1.y, v2.y, v3.y, v4.y,
					segmentPosition);
		} else if (mode.equals(HERMITE)) {
			point.x = Interpolator.hermite(v1.x, v2.x, v3.x, v4.x,
					segmentPosition, tension, bias);
			point.y = Interpolator.hermite(v1.y, v2.y, v3.y, v4.y,
					segmentPosition, tension, bias);
		}

		return point;
	}
}