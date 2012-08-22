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

public class Curve2D {
	PGraphics g;
	float x1, y1, x2, y2, x3, y3, x4, y4;

	public Curve2D(PGraphics _g, float _x1, float _y1, float _x2, float _y2,
			float _x3, float _y3, float _x4, float _y4) {
		g = _g;

		x1 = _x1;
		y1 = _y1;

		x2 = _x2;
		y2 = _y2;

		x3 = _x3;
		y3 = _y3;

		x4 = _x4;
		y4 = _y4;
	}

	public void draw() {
		g.curve(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	public PVector getPoint(float _position) {
		float x = g.curvePoint(x1, x2, x3, x4, _position);
		float y = g.curvePoint(y1, y2, y3, y4, _position);

		return new PVector(x, y);
	}
}
