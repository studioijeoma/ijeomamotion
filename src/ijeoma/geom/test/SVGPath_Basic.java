package ijeoma.geom.test;

import ijeoma.geom.*;
import processing.core.*;

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

public class SVGPath_Basic extends PApplet {

	PShape letterGestures;
	SVGPath p;

	@Override
	public void setup() {
		size(800, 600);
		smooth();

		// s = loadShape(("s.svg"));
		// s = loadShape(dataPath("cloud.svg"));
		letterGestures = loadShape("LETTERS2.svg");

		PShape path = letterGestures.getChild(0);

		p = new SVGPath(g, path);
	}

	@Override
	public void draw() {
		shape(letterGestures);
		// background(255);
		//
		// stroke(0);
		// noFill();
		// p.draw();
		//
		// PVector v = p.getPoint((float) mouseX / width);
		//
		// fill(255, 0, 0);
		// ellipse(v.x, v.y, 10, 10);
	}
}
