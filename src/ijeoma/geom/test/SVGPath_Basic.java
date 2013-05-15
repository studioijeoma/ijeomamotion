package ijeoma.geom.test;

import ijeoma.geom.*;
import ijeoma.motion.Motion;
import processing.core.*;

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

public class SVGPath_Basic extends PApplet {

	PShape svg;
	SVGPath p;

	@Override
	public void setup() {
		size(800, 600);
		smooth();

		// s = loadShape(("s.svg"));
		svg = loadShape("Cloud.svg");
		//  

		PShape path = svg;

		Motion.setup(this);
		p = new SVGPath(svg);
	}

	@Override
	public void draw() {
		// shape(letterGestures);
		background(255);
		//
		float t = (float) mouseX / width;

		stroke(0);
		noFill();
		p.draw(g, t);

		// p.setPosition(t);
		//
		PVector v = p.getPointAt((float) mouseX / width);
		// println(v);
		//
		// fill(255, 0, 0);
		// ellipse(v.x, v.y, 10, 10);
	}
}
