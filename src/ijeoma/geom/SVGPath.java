///**
// * ijeomamotion
// * A cross-mode Processing library for sketching animations with numbers, colors vectors, beziers, curves and more. 
// * http://ekeneijeoma.com/processing/ijeomamotion
// *
// * Copyright (C) 2012 Ekene Ijeoma http://ekeneijeoma.com
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
// * @author      Ekene Ijeoma http://ekeneijeoma.com
// * @modified    05/13/2013
// * @version     5.4.1 (54)
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
	PShape shape;

	public SVGPath(PShape svg) {
		super();

		this.parent = Motion.getParent();
		this.shape = svg;

		// PShape path = svg;
		// if (svg.getVertexCount() == 0) {
		// while (path.getVertexCount() == 0)
		// for (int i = 0; i < path.getVertexCount(); i++) {
		// path.getChild(i);
		// }
		// }
		for (int i = 0; i < svg.getVertexCount(); i++)
			add(svg.getVertex(i));

		// segmentTRange = (1.0f / (svg.getVertexCount() - 1));
	}

	// public void draw(PGraphics g, int mode, float t) {
	// if (visible) {
	// if (!computed)
	// compute();
	//
	// int pointCount = (int) (shape.getVertexCount() * step * t);
	//
	// g.beginShape(mode);
	// for (int i = 1; i <= pointCount; i++) {
	// PVector p1 = getPointAt((float) (i - 1)
	// / (shape.getVertexCount() * step));
	// PVector p2 = getPointAt((float) i
	// / (shape.getVertexCount() * step));
	//
	// if (is3D) {
	// g.vertex(p1.x, p1.y, p1.z);
	// g.vertex(p2.x, p2.y, p2.z);
	// } else {
	// g.vertex(p1.x, p1.y);
	// g.vertex(p2.x, p2.y);
	// }
	// }
	// g.endShape();
	// }
	// }
	//
	// public PVector getPointAt(float position) {
	// // Paths might be empty (go figure)
	// // http://dev.processing.org/bugs/show_bug.cgi?id=982
	// // if (path.vertices == null) return;
	//
	// PVector point = new PVector();
	//
	// if (position < 1) {
	// segmentPointIndex = PApplet.floor((shape.getVertexCount() - 1)
	// * position);
	// segmentT = PApplet.map((position % segmentTRange), 0,
	// segmentTRange, 0, 1);
	// } else {
	// segmentPointIndex = (shape.getVertexCount() - 1);
	// segmentT = 1;
	// }
	//
	// if (shape.getVertexCodeCount() == 0) {
	// point.x = shape.getVertex(segmentPointIndex).x;
	// point.x = shape.getVertex(segmentPointIndex).y;
	// } else { // coded set of vertices
	// switch (shape.getVertexCode(segmentPointIndex)) {
	// case PApplet.VERTEX:
	// point.x = PApplet.lerp(shape.getVertexX(segmentPointIndex),
	// shape.getVertexX(segmentPointIndex + 1), segmentT);
	// point.y = PApplet.lerp(shape.getVertexY(segmentPointIndex),
	// shape.getVertexY(segmentPointIndex + 1), segmentT);
	// break;
	// case PApplet.QUAD_BEZIER_VERTEX:
	// point.x = parent.g.bezierPoint(
	// shape.getVertexX(segmentPointIndex),
	// shape.getVertexX(segmentPointIndex + 1),
	// shape.getVertexX(segmentPointIndex + 2),
	// shape.getVertexX(segmentPointIndex + 2), segmentT);
	//
	// point.y = parent.g.bezierPoint(
	// shape.getVertexY(segmentPointIndex),
	// shape.getVertexY(segmentPointIndex + 1),
	// shape.getVertexY(segmentPointIndex + 2),
	// shape.getVertexY(segmentPointIndex + 2), segmentT);
	// break;
	// case PApplet.BEZIER_VERTEX:
	// point.x = parent.g.bezierPoint(
	// shape.getVertexX(segmentPointIndex),
	// shape.getVertexX(segmentPointIndex + 1),
	// shape.getVertexX(segmentPointIndex + 2),
	// shape.getVertexX(segmentPointIndex + 2), segmentT);
	//
	// point.y = parent.g.bezierPoint(
	// shape.getVertexY(segmentPointIndex),
	// shape.getVertexY(segmentPointIndex + 1),
	// shape.getVertexY(segmentPointIndex + 2),
	// shape.getVertexY(segmentPointIndex + 2), segmentT);
	// break;
	//
	// case PApplet.CURVE_VERTEX:
	// point.x = parent.g.curvePoint(
	// shape.getVertexX(segmentPointIndex),
	// shape.getVertexX(segmentPointIndex + 1),
	// shape.getVertexX(segmentPointIndex + 2),
	// shape.getVertexX(segmentPointIndex + 2), segmentT);
	//
	// point.y = parent.g.curvePoint(
	// shape.getVertexY(segmentPointIndex),
	// shape.getVertexY(segmentPointIndex + 1),
	// shape.getVertexY(segmentPointIndex + 2),
	// shape.getVertexY(segmentPointIndex + 2), segmentT);
	// break;
	// }
	// }
	// return point;
	// }

	public PShape getShape() {
		return shape;
	}
}