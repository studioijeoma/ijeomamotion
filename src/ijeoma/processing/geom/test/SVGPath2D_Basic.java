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

package ijeoma.processing.geom.test;

import ijeoma.processing.geom.SVGPath2D;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class SVGPath2D_Basic extends PApplet {

	PShape s;
	SVGPath2D p;

	@Override
	public void setup() {
		size(800, 600);
		smooth();

		s = loadShape(dataPath("cloud.svg"));

		PShape path = s.getChild(0);

		p = new SVGPath2D(g, path);
	}

	@Override
	public void draw() {
		background(255);

		stroke(0);
		noFill();
		p.draw();

		PVector v = p.getPoint((float) mouseX / width);

		fill(255, 0, 0);
		ellipse(v.x, v.y, 10, 10);
	}
}
