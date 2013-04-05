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

package ijeoma.geom.test;

import ijeoma.geom.Path;
import processing.core.PApplet;
import processing.core.PVector;

public class Path2D_Basic extends PApplet {
	Path path1;
	Path path2;

	@Override
	public void setup() {
		size(800, 600, P3D);

		setupPath();
	}

	public void setupPath() {
		path1 = new Path();

		float x = 0;

		while (x < width) {
			path1.addVertex(x, random(250, 400));

			x += random(5, 10);
		}

		path1.addVertex(width, random(200, 400));

		println("path1.getCount() = " + path1.getVertexCount());

		path2 = new Path(path1.getVertices());
		path2.simplify(5, true);

		println("path2.getCount() = " + path2.getVertexCount());
	}

	@Override
	public void draw() {
		background(255);

		stroke(0);
		strokeWeight(3);
		noFill();
		path1.drawLine(g);
		// path1.drawPoints(g);

		noFill();
		stroke(255, 0, 0);
		strokeWeight(2);
		path2.drawLine(g);
		// path2.drawPoints(g);

		PVector p = path2.getVertexAt((float) mouseX / width);
		noStroke();
		fill(255, 0, 0);
		ellipse(p.x, p.y, 10, 10);
	}

	@Override
	public void mouseMoved() {
		float t = map(mouseX, 0, width, 0.1f, 5);
		// path2.set(path1.get());
		// path2.simplify(t, true);
		// path2.setMode(Path.LINEAR);
		// path2.simplify(t, true);

		// println("path1.getCo/t() = " + path2.getCount());
	}

	@Override
	public void keyPressed() {
		if (key == ' ')
			setupPath();
		else if (key == '1')
			path2.setMode(Path.LINEAR);
		else if (key == '2')
			path2.setMode(Path.COSINE);
		else if (key == '3')
			path2.setMode(Path.CUBIC);
		else if (key == '4')
			path2.setMode(Path.HERMITE);
	}
}