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

package ijeoma.processing.geom;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PVector;

public class SVGPath2D {
	PGraphics g;
	PShape path;

	public SVGPath2D(PGraphics _g, PShape _path) {
		g = _g;
		path = _path;
	}

	public void draw() {
		g.shape(path);

	}

	public PVector getPoint(float _position) {
		float x = 0;
		float y = 0;

		int segmentVertexIndex = 0;

		if (_position < 1)
			segmentVertexIndex = PApplet.floor((path.getVertexCount() - 1)
					* _position);
		else
			segmentVertexIndex = (path.getVertexCount() - 2);

		float segmentPositionRange = (1f / (path.getVertexCount() - 1));

		float segmentPosition = 0;

		if (_position < 1)
			segmentPosition = PApplet.map((_position % segmentPositionRange),
					0, segmentPositionRange, 0, 1);
		else
			segmentPosition = 1;

		if (path.getVertexCodeCount() == 0) {
			if (path.getVertex(0).length == 2) {
				for (int i = 0; i < path.getVertexCount(); i++) {
					x = PApplet.lerp(path.getVertexX(segmentVertexIndex),
							path.getVertexX(segmentVertexIndex + 1),
							segmentPosition);
					y = PApplet.lerp(path.getVertexX(segmentVertexIndex),
							path.getVertexX(segmentVertexIndex + 1),
							segmentPosition);
				}
			}
		} else {
			int index = 0;

			if (path.getVertex(0).length == 2) { // drawing a 2D path
				for (int j = 0; j < path.getVertexCodeCount(); j++) {
					switch (path.getVertexCode(j)) {

					case PShape.VERTEX:
						x = PApplet.lerp(path.getVertexX(segmentVertexIndex),
								path.getVertexX(segmentVertexIndex + 1),
								segmentPosition);
						y = PApplet.lerp(path.getVertexY(segmentVertexIndex),
								path.getVertexY(segmentVertexIndex + 1),
								segmentPosition);
						// index++;
						break;

					case PShape.QUAD_BEZIER_VERTEX:
						// g.quadraticVertex(path.getVertexX(index + 0),
						// path.getVertexY(index + 0),
						// path.getVertexX(index + 1),
						// path.getVertexY(index + 1));
						// index += 2;
						break;

					case PShape.BEZIER_VERTEX:
						// g.bezierVertex(path.getVertexX(index + 0),
						// path.getVertexY(index + 0),
						// path.getVertexX(index + 1),
						// path.getVertexY(index + 1),
						// path.getVertexX(index + 2),
						// path.getVertexY(index + 2));
						// index += 3;
						break;

					case PShape.CURVE_VERTEX:
						// g.curveVertex(path.getVertexX(index),
						// path.getVertexY(index));
						// index++;

						// case BREAK:
						// g.breakShape();
					}
				}
			}
		}

		return new PVector(x, y);
	}
}
