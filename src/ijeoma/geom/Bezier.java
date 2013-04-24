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

import ijeoma.motion.Motion;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Bezier {
	PGraphics g;
	float x1, y1, z1, cx1, cy1, cz1, cx2, cy2, cz2, x2, y2, z2;
	int steps = 1;
	float t = 1;
	boolean is3D = Motion.getParent().isGL();

	public Bezier(PGraphics _g, float _x1, float _y1, float _cx1, float _cy1,
			float _cx2, float _cy2, float _x2, float _y2) {
		g = Motion.getParent().g;

		x1 = _x1;
		y1 = _y1;
		cx1 = _cx1;
		cy1 = _cy1;
		cx2 = _cx2;
		cy2 = _cy1;
		x2 = _x2;
		y2 = _y2;
	}

	Bezier(PGraphics _g, float _x1, float _y1, float _z1, float _cx1,
			float _cy1, float _cz1, float _cx2, float _cy2, float _cz2,
			float _x2, float _y2, float _z2) {
		g = Motion.getParent().g;

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

		is3D = true;
	}

	public void draw(PGraphics g) {
		if (is3D)
			g.bezier(x1, y1, z1, cx1, cy1, cz1, cx2, cy2, cz2, x2, y2, z2);
		else
			g.bezier(x1, y1, cx1, cy1, cx2, cy2, x2, y2);
	}

	public void draw(PGraphics g, int steps) {
		draw(g, PApplet.LINE, steps, t);
	}

	public void draw(PGraphics g, float t) {
		draw(g, PApplet.LINE, steps, t);
	}

	public void draw(PGraphics g, int steps, float t) {
		draw(g, PApplet.LINE, steps, t);
	}

	public void draw(PGraphics g, int mode, int steps, float t) {
		int pointCount = PApplet.floor(100 * steps * t);

		if (mode == PApplet.LINE)
			g.beginShape();
		else
			g.beginShape(mode);

		for (int i = 0; i < pointCount; i += steps) {
			PVector p1 = getPointAt((float) i / 100);

			if (is3D)
				g.vertex(p1.x, p1.y, p1.z);
			else
				g.vertex(p1.x, p1.y);
		}
		g.endShape();
	}

	public PVector getPointAt(float t) {
		float x = g.bezierPoint(x1, cx1, cx2, x2, t);
		float y = g.bezierPoint(y1, cy1, cy2, y2, t);

		if (is3D) {
			float z = g.bezierPoint(z1, cz1, cz2, z2, t);
			return new PVector(x, y, z);
		} else
			return new PVector(x, y);
	}
}
