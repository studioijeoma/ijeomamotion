package ijeoma.motion.tween.test;

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

import ijeoma.geom.Path;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class Path3DTween_Basic extends PApplet {
	int pointCount = 5;
	float pBegin, pEnd, pLength, pSegmentLength;

	PVector[] points = new PVector[pointCount];

	Path p;

	Tween tp;

	@Override
	public void setup() {
		size(400, 400, P3D);

		pBegin = 0;
		pEnd = width;
		pLength = pEnd - pBegin;
		pSegmentLength = pLength / (pointCount - 1);

		setupPath();
		setupPathMotion();
	}

	public void setupPath() {
		// This creates a 3d path with random points
		for (int i = 0; i < pointCount - 1; i++) {
			float x = pBegin + pSegmentLength * i;
			float y = random(-100, 100);
			float z = (i == 0 || i == pointCount - 1) ? 0 : random(-100, 100);

			points[i] = new PVector(x, y, z);
		}

		points[pointCount - 1] = new PVector(pEnd, random(100, 200), random(0,
				10));

		// Path(PGraphics _g, PVector[] _vertices)
		p = new Path(points);
	}

	public void setupPathMotion() {
		Motion.setup(this);

		// TweenPath(String _name, Path _path, float _begin,
		// float _end, float _duration)
		tp = new Tween(300f).addPath(p, 1f);
		tp.repeat();
		tp.play();
	}

	@Override
	public void draw() {
		background(255);

		pushMatrix();
		translate(0, height / 2, 0);
		noFill();
		stroke(0);
		p.draw(g);

		pushMatrix();
		PVector v = p.get();
		translate(v.x, v.y, v.z);
		noStroke();
		fill(255, 0, 0);
		sphere(5);
		popMatrix();

		popMatrix();

		fill(lerpColor(0xFF00FF00, 0xFFFF0000, tp.getPosition()));

		String time = tp.getTime() + " / " + tp.getDuration();
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		// Path(PGraphics _g, PVector[] _vertices, String _pathMode)
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
		else {
			setupPath();
			setupPathMotion();
		}
	}
}