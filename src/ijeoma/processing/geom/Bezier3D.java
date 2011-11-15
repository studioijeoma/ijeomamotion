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

import processing.core.PGraphics;
import processing.core.PVector;

public class Bezier3D {
	PGraphics g;
	float x1, y1, z1, cx1, cy1, cz1, cx2, cy2, cz2, x2, y2, z2;

	Bezier3D(PGraphics _g, float _x1, float _y1, float _z1, float _cx1, float _cy1, float _cz1, float _cx2, float _cy2, float _cz2, float _x2, float _y2, float _z2) {
		g = _g;

		x1 = _x1;
		y1 = _y1;
		z1 = _z1;
		cx1 = _cx1;
		cy1 = _cy1;
		cz1 = _cz1;
		cx2 = _cx2;
		cy2 = _cy1;
		cz2 = _cz1;
		x2 = _x2;
		y2 = _y2;
		z2 = _z2;
	}

	public void draw() {
		g.bezier(x1, y1, z1, cx1, cy1, cz1, cx2, cy2, cz2, x2, y2, z2);
	}

	public PVector getPoint(float _position) {
		float x = g.bezierPoint(x1, cx1, cx2, x2, _position);
		float y = g.bezierPoint(y1, cy1, cy2, y2, _position);
		float z = g.bezierPoint(z1, cz1, cz2, z2, _position);

		return new PVector(x, y, z);
	}
}
