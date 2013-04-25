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

package ijeoma.geom.tween.test;

import java.util.List;

import ijeoma.geom.Path;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class Path2D_Basic extends PApplet {
	Path p;
	Tween t;

	@Override
	public void setup() {
		size(800, 600, P3D);

		Motion.setup(this);

		setupPath();
	}

	public void setupPath() {
		p = new Path();
		p.setMode(Path.HERMITE);

		float x = 0;

		while (x < width) {
			p.add(x, random(200, 400), 0);
			x += 50;
		}

		p.add(x, random(200, 400), 0);

		t = new Tween(200).addPath(p, 1).play();
	}

	@Override
	public void draw() {
		background(255);
		smooth();

		stroke(0);
		strokeWeight(1);
		noFill();
		// p.draw(g, (float) mouseX / width);
		// p.draw(g);

		// uniform points
		// noStroke();
		fill(0, 0, 255);
		int c1 = color(255, 0, 0);
		int c2 = color(0, 255, 0);
		// List<PVector> points = p.getPointList(10, (float) mouseX / width);
		List<PVector> points = p.getUniformPointList(10);
		//
		strokeWeight(3);
		beginShape(POINTS);
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

		PVector v = p.getPointAt((float) mouseX / width);
		noStroke();
		fill(255, 0, 0);
		ellipse(v.x, v.y, 10, 10);

		fill(0);
		// text(t.getPosition() + " " + p.getPosition(), 25, 25);
	}

	@Override
	public void keyPressed() {
		if (key == ' ')
			setupPath();
		else if (key == '1')
			p.setMode(Path.LINEAR);
		else if (key == '2')
			p.setMode(Path.COSINE);
		else if (key == '3')
			p.setMode(Path.CUBIC);
		else if (key == '4')
			p.setMode(Path.HERMITE);
	}
}