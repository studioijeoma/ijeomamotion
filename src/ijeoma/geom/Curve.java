/**
 * ijeomamotion
 * A cross-mode Processing library for sketching animations with numbers, colors vectors, beziers, curves and more. 
 * http://ekeneijeoma.com/processing/ijeomamotion
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
 * @modified    05/13/2013
 * @version     5.4.1 (54)
 */

package ijeoma.geom;

import ijeoma.motion.Motion;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Curve {
	PGraphics g;
	float x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4;
	int steps = 1;
	float t = 1;
	boolean is3D = Motion.getParent().isGL();

	public Curve(float _x1, float _y1, float _x2, float _y2, float _x3,
			float _y3, float _x4, float _y4) {
		g = Motion.getParent().g;

		x1 = _x1;
		y1 = _y1;

		x2 = _x2;
		y2 = _y2;

		x3 = _x3;
		y3 = _y3;

		x4 = _x4;
		y4 = _y4;
	}

	public Curve(float _x1, float _y1, float _z1, float _x2, float _y2,
			float _z2, float _x3, float _y3, float _z3, float _x4, float _y4,
			float _z4) {
		g = Motion.getParent().g;

		x1 = _x1;
		y1 = _y1;
		z1 = _z1;

		x2 = _x2;
		y2 = _y2;
		z2 = _z2;

		x3 = _x3;
		y3 = _y3;
		z3 = _z3;

		x4 = _x4;
		y4 = _y4;
		z4 = _z4;

		is3D = true;
	}

	public void draw(PGraphics g) {
		if (is3D)
			g.curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
		else
			g.curve(x1, y1, x2, y2, x3, y3, x4, y4);
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

		for (int i = 0; i <= pointCount; i += steps) {
			PVector p1 = getPointAt((float) i / 100);

			if (is3D)
				g.vertex(p1.x, p1.y, p1.z);
			else
				g.vertex(p1.x, p1.y);
		}
		g.endShape();
	}

	public PVector getPointAt(float t) {
		float x = g.curvePoint(x1, x2, x3, x4, t);
		float y = g.curvePoint(y1, y2, y3, y4, t);

		if (is3D) {
			float z = g.curvePoint(z1, z2, z3, z4, t);
			return new PVector(x, y, z);
		} else
			return new PVector(x, y);
	}
}
