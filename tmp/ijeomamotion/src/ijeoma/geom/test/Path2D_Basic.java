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

package ijeoma.geom.test;

import ijeoma.geom.Path2D;
import processing.core.*;

public class Path2D_Basic extends PApplet {
	Path2D path1;
	Path2D path2;

	@Override
	public void setup() {
		size(800, 600, P3D);

		setupPath();
	}

	public void setupPath() {
		path1 = new Path2D();

		float x = 0;

		while (x < width) {
			path1.add(x, random(250, 400));

			x += random(5, 10);
		}

		path1.add(width, random(200, 400));
		path1.setMode(Path2D.LINEAR);

		println("path1.getCount() = " + path1.getCount());

		path2 = new Path2D(path1.get());
		path2.setMode(Path2D.LINEAR);
		// path2.simplify(1f, false);
		// path2.simplify();

		println("path2.getCount() = " + path2.getCount());
	}

	@Override
	public void draw() {
		background(255);

		stroke(0);
		strokeWeight(5);
		noFill();
		path1.drawLine(g);

		noFill();
		stroke(255, 0, 0);
		strokeWeight(1);
		path2.drawLine(g);

		PVector p = path2.get((float) mouseX / width);
		noStroke();
		// fill(255, 0, 0);
		// ellipse(p.x, p.y, 10, 10);
	}

	public void mouseMoved() {
		float t = map(mouseX, 0, width, 0.1f, 5);
		// path2.set(path1.get());
		// path2.simplify(t, true);
		// path2.setMode(Path2D.LINEAR);
		path2.simplify(t, true);

		println("path1.getCo/t() = " + path2.getCount());
	}

	@Override
	public void keyPressed() {
		if (key == ' ')
			setupPath();
		else if (key == '1')
			path2.setMode(Path2D.LINEAR);
		else if (key == '2')
			path2.setMode(Path2D.COSINE);
		else if (key == '3')
			path2.setMode(Path2D.CUBIC);
		else if (key == '4')
			path2.setMode(Path2D.HERMITE);
	}
}