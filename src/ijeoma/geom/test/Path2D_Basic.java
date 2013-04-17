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

import java.util.List;

import ijeoma.geom.Path;
import processing.core.PApplet;
import processing.core.PVector;

public class Path2D_Basic extends PApplet {
	Path path;
	Path path2;

	@Override
	public void setup() {
		size(800, 600, P3D);

		setupPath();
	}

	public void setupPath() {
		path = new Path();

		float x = 0;

		while (x < width) {
			path.add(x, random(250, 400));
			// path.add(x, height / 2);

			x += 50;// random(25, 50);
		}

		path.add(width, random(200, 400));
		// println("path1.getCount() = " + path1.getPointCount());

		// path2 = new Path(path1.getPoints());
		// path2.simplify(5, true);

		// println("path2.getCount() = " + path2.getPointCount());
	}

	@Override
	public void draw() {
		background(255);

		int steps = 10;
		// float t = 1f;// (float) mouseX / width;

		// path line
		stroke(0);
		strokeWeight(1);
		noFill();
		path.draw(g, steps, 1);

		// path points
		// strokeWeight(3);
		// stroke(255, 0, 0);
		// path.draw(g, POINTS, steps, t);

		// uniform points
		// noStroke();
		fill(0, 0, 255);
		int c1 = color(255, 0, 0);
		int c2 = color(0, 255, 0);
		List<PVector> points = path.getUniformPointList(5);

		strokeWeight(3);
		beginShape(LINES);
		for (int i = 1; i < points.size(); i++) {
			PVector p1 = points.get(i - 1);
			PVector p2 = points.get(i);

			float t = (float) (i - 1) / points.size();
			int c = lerpColor(c1, c2, t);
			stroke(c);
			vertex(p1.x, p1.y);

			t = (float) i / points.size();
			c = lerpColor(c1, c2, t);
			stroke(c);
			vertex(p2.x, p2.y);
		}
		endShape();

		// points
		noStroke();
		fill(255, 0, 255);
		for (PVector p : path.getPointList())
			ellipse(p.x, p.y, 5, 5);

		PVector p = path.getPointAt((float) mouseX / width);
		noStroke();
		fill(255, 0, 0);
		ellipse(p.x, p.y, 10, 10);
	}

	@Override
	public void mouseMoved() {
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
			path.setMode(Path.LINEAR);
		else if (key == '2')
			path.setMode(Path.COSINE);
		else if (key == '3')
			path.setMode(Path.CUBIC);
		else if (key == '4')
			path.setMode(Path.HERMITE);
	}
}