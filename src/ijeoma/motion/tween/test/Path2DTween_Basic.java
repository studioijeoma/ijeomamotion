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
package ijeoma.motion.tween.test;

import ijeoma.geom.Path;
import ijeoma.geom.tween.PathTween;
import ijeoma.motion.Motion;
import processing.core.PApplet;
import processing.core.PVector;

public class Path2DTween_Basic extends PApplet {
	PVector[] points;

	Path p;
	PathTween tp;

	@Override
	public void setup() {
		size(400, 400, P3D);
		smooth();

		Motion.setup(this);

		setupPath();
	}

	public void setupPath() {
		p = new Path();

		float x = 0;

		while (x < width) {
			p.addVertex(x, random(200, 400));

			x += random(10, 20);
		}

		p.addVertex(width, random(200, 400));

		tp = new PathTween(p, 0f, 1f, 300f);
		tp.repeat();
		tp.play();
	}

	@Override
	public void draw() {
		background(255);

		noFill();
		stroke(100);
		p.drawLine(g);

		fill(255, 0, 0);
		ellipse(tp.getX(), tp.getY(), 10, 10);

		// println(tp.getPoint());
		// text(tp.getPosition(), 0, height - 100);
	}

	@Override
	public void keyPressed() {
		// Path3D(PApplet _parent, PVector[] _points, String _pathMode)
		// _pathMode is set to CUBIC by default but can also be set to LINEAR,
		// COSINE, HERMITE

		if (key == '1')
			p.setMode(Path.LINEAR);
		else if (key == '2')
			p.setMode(Path.COSINE);
		else if (key == '3')
			p.setMode(Path.CUBIC);
		else if (key == '4')
			p.setMode(Path.HERMITE);
		else if (key == ' ')
			setupPath();
		else
			tp.play();
	}
}
