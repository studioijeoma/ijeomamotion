/**
 * ijeomamotion
 * A collection of utilities creating flash-like animations.
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
 * @modified    08/21/2012
 * @version     4 (26)
 */

package ijeoma.processing.geom.test;

import processing.core.*;

import ijeoma.processing.*;
import ijeoma.processing.geom.Curve2D;

public class Curve2D_Basic extends PApplet {

	Curve2D c;

	public void setup() {
		size(100, 100);
		smooth();

		c = new Curve2D(g, 5, 26, 5, 26, 73, 24, 73, 61);
	}

	public void draw() {
		background(255);

		noFill();
		c.draw();
	}
}
