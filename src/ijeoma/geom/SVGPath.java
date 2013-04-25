///**
// * ##library.name##
// * ##library.sentence##
// * ##library.url##
// *
// * Copyright ##copyright## ##author##
// *
// * This library is free software; you can redistribute it and/or
// * modify it under the terms of the GNU Lesser General Public
// * License as published by the Free Software Foundation; either
// * version 2.1 of the License, or (at your option) any later version.
// * 
// * This library is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// * Lesser General Public License for more details.
// * 
// * You should have received a copy of the GNU Lesser General
// * Public License along with this library; if not, write to the
// * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
// * Boston, MA  02111-1307  USA
// * 
// * @author      ##author##
// * @modified    ##date##
// * @version     ##library.prettyVersion## (##library.version##)
// */
//
package ijeoma.geom;

import ijeoma.motion.Motion;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PVector;

public class SVGPath extends Path {
	PApplet parent;
	PShape svg;

	int segmentIndex = 0;
	int segmentVertexIndex = 0;
	float segmentPosition = 0;
	float segmentPositionRange = 0;

	PVector point = new PVector();

	public SVGPath(PShape svg) {
		super();

		this.parent = Motion.getParent();
		this.svg = svg;

		segmentPositionRange = (1.0f / (svg.getVertexCount() - 1));
	}

	public void draw(PGraphics g) {
		g.shape(svg);
	}

	public PVector getPoint(float position) {
		// Paths might be empty (go figure)
		// http://dev.processing.org/bugs/show_bug.cgi?id=982
		// if (path.vertices == null) return;

		PVector point = new PVector();

		if (position < 1) {
			segmentPointIndex = PApplet.floor((points.size() - 1) * position);
			segmentT = PApplet.map((position % segmentTRange), 0,
					segmentTRange, 0, 1);
		} else {
			segmentPointIndex = (points.size() - 2);
			segmentT = 1;
		}

		if (svg.getVertexCodeCount() == 0) { // each point is a simple
												// vertex
			for (int i = 0; i < svg.getVertexCodeCount(); i++)
//				g.vertex(svg.getVertex(i).x, svg.getVertex(i).y);
		} else { // coded set of vertices
			int index = 0;

			for (int j = 0; j < svg.getVertexCodeCount(); j++) {
				switch (svg.getVertexCode(j)) {

				case PApplet.VERTEX:
					// g.vertex(svg.getVertex(index).x, svg.getVertex(index).y);
					// cx = path.getVertex(index).x;
					// cy = path.getVertex(index).y;

					point.x = PApplet.lerp(svg.getVertexX(segmentVertexIndex),
							svg.getVertexX(segmentVertexIndex + 1),
							segmentPosition);
					point.y = PApplet.lerp(svg.getVertexY(segmentVertexIndex),
							svg.getVertexY(segmentVertexIndex + 1),
							segmentPosition);
					index++;
					break;

				case PApplet.QUAD_BEZIER_VERTEX:
					// g.quadraticVertex(svg.getVertex(index + 0).x,
					// svg.getVertex(index + 0).y,
					// svg.getVertex(index + 1).x,
					// svg.getVertex(index + 1).y);

					point.x = parent.g.bezierPoint(
							svg.getVertexX(segmentVertexIndex),
							svg.getVertexX(segmentVertexIndex + 1),
							svg.getVertexX(segmentVertexIndex + 2),
							svg.getVertexX(segmentVertexIndex + 2),
							segmentPosition);

					point.y = parent.g.bezierPoint(
							svg.getVertexY(segmentVertexIndex),
							svg.getVertexY(segmentVertexIndex + 1),
							svg.getVertexY(segmentVertexIndex + 2),
							svg.getVertexY(segmentVertexIndex + 2),
							segmentPosition);

					index += 2;
					break;

				case PApplet.BEZIER_VERTEX:
					// g.bezierVertex(svg.getVertex(index + 0).x,
					// svg.getVertex(index + 0).y,
					// svg.getVertex(index + 1).x,
					// svg.getVertex(index + 1).y,
					// svg.getVertex(index + 2).x,
					// svg.getVertex(index + 2).y);
					point.x = parent.g.bezierPoint(
							svg.getVertexX(segmentVertexIndex),
							svg.getVertexX(segmentVertexIndex + 1),
							svg.getVertexX(segmentVertexIndex + 2),
							svg.getVertexX(segmentVertexIndex + 2),
							segmentPosition);

					point.y = parent.g.bezierPoint(
							svg.getVertexY(segmentVertexIndex),
							svg.getVertexY(segmentVertexIndex + 1),
							svg.getVertexY(segmentVertexIndex + 2),
							svg.getVertexY(segmentVertexIndex + 2),
							segmentPosition);
					index += 3;
					break;

				case PApplet.CURVE_VERTEX:
					// g.curveVertex(svg.getVertex(index).x,
					// svg.getVertex(index).y);
					point.x = parent.g.curvePoint(
							svg.getVertexX(segmentVertexIndex),
							svg.getVertexX(segmentVertexIndex + 1),
							svg.getVertexX(segmentVertexIndex + 2),
							svg.getVertexX(segmentVertexIndex + 2),
							segmentPosition);

					point.y = parent.g.curvePoint(
							svg.getVertexY(segmentVertexIndex),
							svg.getVertexY(segmentVertexIndex + 1),
							svg.getVertexY(segmentVertexIndex + 2),
							svg.getVertexY(segmentVertexIndex + 2),
							segmentPosition);
					index++;
					break;
				}
			}
			return point;
		}
	}
}

// public PVector getPoint(float _position) {
// if (_position <= 1)
// segmentVertexIndex = PApplet.floor((path.getVertexCount() - 1)
// * _position);
// else
// segmentVertexIndex = (path.getVertexCount() - 2);
//
// if (_position <= 1)
// segmentPosition = PApplet.map((_position % segmentPositionRange),
// 0, segmentPositionRange, 0.0f, 1.0f);
// else
// segmentPosition = 1;
//
// // based on PShape.drawPath()
// if (path.getVertexCodeCount() == 0) { // each point is a simple
// vertex
// if ( path.getVertexCount() == 2) { // drawing 2D vertices
// for (int i = 0; i < path.getVertexCount(); i++) {
// g.vertex(path.vertices[i][X], path.vertices[i][Y]);
// }
// }
// } else { // coded set of vertices
// int index = 0;
//
// if (path.getVertex(0).length == 2) { // drawing a 2D path
// for (int j = 0; j < path.getVertexCodeCount(); j++) {
// switch (vertexCodes[j]) {
//
// case PApplet.VERTEX:
// g.vertex(path.vertices[index][X], path.vertices[index][Y]);
// // cx = path.vertices[index][X];
// // cy = path.vertices[index][Y];
// index++;
// break;
//
// case PApplet.QUAD_BEZIER_VERTEX:
// g.quadraticVertex(path.vertices[index+0][X],
// path.vertices[index+0][Y],
// path.vertices[index+1][X], path.vertices[index+1][Y]);
// // float x1 = path.vertices[index+0][X];
// // float y1 = path.vertices[index+0][Y];
// // float x2 = path.vertices[index+1][X];
// // float y2 = path.vertices[index+1][Y];
// // g.bezierVertex(x1 + ((cx-x1)*2/3.0f), y1 + ((cy-y1)*2/3.0f),
// // x2 + ((cx-x2)*2/3.0f), y2 + ((cy-y2)*2/3.0f),
// // x2, y2);
// // cx = path.vertices[index+1][X];
// // cy = path.vertices[index+1][Y];
// index += 2;
// break;
//
// case PApplet.BEZIER_VERTEX:
// g.bezierVertex(path.vertices[index+0][X], path.vertices[index+0][Y],
// path.vertices[index+1][X], path.vertices[index+1][Y],
// path.vertices[index+2][X], path.vertices[index+2][Y]);
// // cx = path.vertices[index+2][X];
// // cy = path.vertices[index+2][Y];
//
// index += 3;
// break;
//
// case PApplet.CURVE_VERTEX:
// g.curveVertex(path.vertices[index][X], path.vertices[index][Y]);
// index++;
// break;
//
// // case BREAK:
// // if (insideContour) {
// // g.endContour();
// // }
// // g.beginContour();
// // insideContour = true;
// // }
// }
// } else { // drawing a 3D path
// for (int j = 0; j < path.getVertexCodeCount(); j++) {
// switch (vertexCodes[j]) {
//
// case PApplet.VERTEX:
// g.vertex(path.vertices[index][X], path.vertices[index][Y],
// path.vertices[index][Z]);
// // cx = path.vertices[index][X];
// // cy = path.vertices[index][Y];
// // cz = path.vertices[index][Z];
// index++;
// break;
//
// case PApplet.QUAD_BEZIER_VERTEX:
// g.quadraticVertex(path.vertices[index+0][X],
// path.vertices[index+0][Y], path.vertices[index+0][Z],
// path.vertices[index+1][X], path.vertices[index+1][Y],
// path.vertices[index+0][Z]);
// index += 2;
// break;
//
//
// case PApplet.BEZIER_VERTEX:
// g.bezierVertex(path.vertices[index+0][X], path.vertices[index+0][Y],
// path.vertices[index+0][Z],
// path.vertices[index+1][X], path.vertices[index+1][Y],
// path.vertices[index+1][Z],
// path.vertices[index+2][X], path.vertices[index+2][Y],
// path.vertices[index+2][Z]);
// index += 3;
// break;
//
// case CURVE_VERTEX:
// g.curveVertex(path.vertices[index][X], path.vertices[index][Y],
// path.vertices[index][Z]);
// index++;
// break;
//
// // case BREAK:
// // if (insideContour) {
// // g.endContour();
// // }
// // g.beginContour();
// // insideContour = true;
// // }
// }
// }
// }

// if (path.getVertexCodeCount() == 0) {
// if (path.getVertexCount() == 2) {
// for (int i = 0; i < path.getVertexCount(); i++) {
// point.x = PApplet.lerp(path.getVertexX(segmentVertexIndex),
// path.getVertexX(segmentVertexIndex + 1),
// segmentPosition);
// point.y = PApplet.lerp(path.getVertexX(segmentVertexIndex),
// path.getVertexX(segmentVertexIndex + 1),
// segmentPosition);
// }
// }
// } else {
// if (path.getVertexCount() == 2) {
// for (int j = 0; j < path.getVertexCodeCount(); j++) {
// switch (path.getVertexCode(j)) {
//
// case PApplet.VERTEX:
// point.x = PApplet.lerp(
// path.getVertexX(segmentVertexIndex),
// path.getVertexX(segmentVertexIndex + 1),
// segmentPosition);
// point.y = PApplet.lerp(
// path.getVertexY(segmentVertexIndex),
// path.getVertexY(segmentVertexIndex + 1),
// segmentPosition);
// break;
//
// case PApplet.QUAD_BEZIER_VERTEX:
// g.quadraticVertex(
// path.getVertexX(segmentVertexIndex + 0),
// path.getVertexY(segmentVertexIndex + 0),
// path.getVertexX(segmentVertexIndex + 1),
// path.getVertexY(segmentVertexIndex + 1));
// // index += 2;
// break;
//
// case PApplet.BEZIER_VERTEX:
// point.x = g.bezierPoint(
// path.getVertexX(segmentVertexIndex),
// path.getVertexX(segmentVertexIndex + 1),
// path.getVertexX(segmentVertexIndex + 2),
// path.getVertexX(segmentVertexIndex + 2),
// segmentPosition);
//
// point.y = g.bezierPoint(
// path.getVertexY(segmentVertexIndex),
// path.getVertexY(segmentVertexIndex + 1),
// path.getVertexY(segmentVertexIndex + 2),
// path.getVertexY(segmentVertexIndex + 2),
// segmentPosition);
// break;
//
// case PApplet.CURVE_VERTEX:
// point.x = g.curvePoint(
// path.getVertexX(segmentVertexIndex),
// path.getVertexX(segmentVertexIndex + 1),
// path.getVertexX(segmentVertexIndex + 2),
// path.getVertexX(segmentVertexIndex + 2),
// segmentPosition);
//
// point.y = g.curvePoint(
// path.getVertexY(segmentVertexIndex),
// path.getVertexY(segmentVertexIndex + 1),
// path.getVertexY(segmentVertexIndex + 2),
// path.getVertexY(segmentVertexIndex + 2),
// segmentPosition);
// break;
// }
// }
// }
// }
//
// return point;
// }

