/**
 * ijeomamotion
 * A library for sketching animations with numbers, colors vectors, beziers, curves and more! 
 * http://ekeneijeoma.com/processing/ijeomamotion/
 *
 * Copyright (C) 2012 Ekene Ijeoma http://ekeneijeoma.com
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
 * @author      Ekene Ijeoma http://ekeneijeoma.com
 * @modified    09/20/2012
 * @version     4 (26)
 */

package ijeoma.processing.geom;

import processing.core.PGraphics;
import processing.core.PVector;

public class Bezier2D {
	PGraphics g;
	float x1, y1, cx1, cy1, cx2, cy2, x2, y2;
	float position;

	public Bezier2D(PGraphics _g, float _x1, float _y1, float _cx1, float _cy1,
			float _cx2, float _cy2, float _x2, float _y2) {
		g = _g;

		x1 = _x1;
		y1 = _y1;
		cx1 = _cx1;
		cy1 = _cy1;
		cx2 = _cx2;
		cy2 = _cy1;
		x2 = _x2;
		y2 = _y2;
	}

	public void draw() {
		g.bezier(x1, y1, cx1, cy1, cx2, cy2, x2, y2);
	}

	// public void draw(int steps) {
	// steps *= position;
	//
	// g.beginShape();
	// for (int i = 0; i <= steps; i++) {
	// float t = i / (float) steps;
	// float x = g.bezierPoint(x1, cx1, cx2, x2, t);
	// float y = g.bezierPoint(y1, cy1, cy2, y2, t);
	// g.vertex(x, y);
	// }
	// g.endShape();
	// }

	public PVector getPoint(float _position) {
		float x = g.bezierPoint(x1, cx1, cx2, x2, _position);
		float y = g.bezierPoint(y1, cy1, cy2, y2, _position);

		return new PVector(x, y);
	}

	// public void setPosition(float _t) {
	// position = _t;
	// }
	//
	// public float getPosition() {
	// return position;
	// }
}
