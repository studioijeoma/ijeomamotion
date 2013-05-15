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
package ijeoma.motion.tween.test;

import ijeoma.geom.Path;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class Path2DTween_Basic extends PApplet {
	PVector[] points;

	Path p;
	Tween tp;

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
			p.add(x, random(200, 400), 0);

			x += random(10, 20);
		}

		p.add(width, random(200, 400), 0);

		tp = new Tween(300f).addPath(p, 1f);
		tp.repeat();
		tp.play();
	}

	@Override
	public void draw() {
		background(255);

		noFill();
		stroke(100);
		p.draw(g);

		fill(255, 0, 0);
		PVector v = p.get();
		ellipse(v.x, v.y, 10, 10);

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
