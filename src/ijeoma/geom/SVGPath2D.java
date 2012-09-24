package ijeoma.geom;
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
//package ijeoma.processing.geom;
//
//import processing.core.PApplet; 
//import processing.core.PGraphics;
//import processing.core.PShape;
//import processing.core.PVector;
//
//public class SVGPath2D {
//	PGraphics g;
//	PShape path;
//
//	int segmentIndex = 0;
//	int segmentVertexIndex = 0;
//	float segmentPosition = 0;
//	float segmentPositionRange = 0;
//
//	PVector point = new PVector();
//
//	public SVGPath2D(PGraphics _g, PShape _path) {
//		g = _g;
//		path = _path;
//
//		segmentPositionRange = (1.0f / (path.getVertexCount() - 1));
//	}
//
//	public void draw() {
//		g.shape(path);
//	}
//
//	public PVector getPoint(float _position) {
//		if (_position <= 1)
//			segmentVertexIndex = PApplet.floor((path.getVertexCount() - 1)
//					* _position);
//		else
//			segmentVertexIndex = (path.getVertexCount() - 2);
//
//		if (_position <= 1)
//			segmentPosition = PApplet.map((_position % segmentPositionRange),
//					0, segmentPositionRange, 0.0f, 1.0f);
//		else
//			segmentPosition = 1;
//
//		// based on PShape.drawPath()
//		if (path.getVertexCodeCount() == 0) {
//			if (path.getVertexCount() == 2) {
//				for (int i = 0; i < path.getVertexCount(); i++) {
//					point.x = PApplet.lerp(path.getVertexX(segmentVertexIndex),
//							path.getVertexX(segmentVertexIndex + 1),
//							segmentPosition);
//					point.y = PApplet.lerp(path.getVertexX(segmentVertexIndex),
//							path.getVertexX(segmentVertexIndex + 1),
//							segmentPosition);
//				}
//			}
//		} else {
//			if (path.getVertexCount() == 2) {
//				for (int j = 0; j < path.getVertexCodeCount(); j++) {
//					switch (path.getVertexCode(j)) {
//
//					case PApplet.VERTEX:
//						point.x = PApplet.lerp(
//								path.getVertexX(segmentVertexIndex),
//								path.getVertexX(segmentVertexIndex + 1),
//								segmentPosition);
//						point.y = PApplet.lerp(
//								path.getVertexY(segmentVertexIndex),
//								path.getVertexY(segmentVertexIndex + 1),
//								segmentPosition);
//						break;
//
//					case PApplet.QUAD_BEZIER_VERTEX:
//						// g.quadraticVertex(path.getVertexX(index + 0),
//						// path.getVertexY(index + 0),
//						// path.getVertexX(index + 1),
//						// path.getVertexY(index + 1));
//						// index += 2;
//						break;
//
//					case PApplet.BEZIER_VERTEX:
//						point.x = g.bezierPoint(
//								path.getVertexX(segmentVertexIndex),
//								path.getVertexX(segmentVertexIndex + 1),
//								path.getVertexX(segmentVertexIndex + 2),
//								path.getVertexX(segmentVertexIndex + 2),
//								segmentPosition);
//
//						point.y = g.bezierPoint(
//								path.getVertexY(segmentVertexIndex),
//								path.getVertexY(segmentVertexIndex + 1),
//								path.getVertexY(segmentVertexIndex + 2),
//								path.getVertexY(segmentVertexIndex + 2),
//								segmentPosition);
//						break;
//
//					case PApplet.CURVE_VERTEX:
//						point.x = g.curvePoint(
//								path.getVertexX(segmentVertexIndex),
//								path.getVertexX(segmentVertexIndex + 1),
//								path.getVertexX(segmentVertexIndex + 2),
//								path.getVertexX(segmentVertexIndex + 2),
//								segmentPosition);
//
//						point.y = g.curvePoint(
//								path.getVertexY(segmentVertexIndex),
//								path.getVertexY(segmentVertexIndex + 1),
//								path.getVertexY(segmentVertexIndex + 2),
//								path.getVertexY(segmentVertexIndex + 2),
//								segmentPosition);
//						break;
//					}
//				}
//			}
//		}
//
//		return point;
//	}
//
//	public int getSegmentIndex() {
//		return segmentIndex;
//	}
//
//	public int getSegmentVertexIndex() {
//		return segmentVertexIndex;
//	}
//
//	public float getSegmentPosition() {
//		return segmentPosition;
//	}
// }
