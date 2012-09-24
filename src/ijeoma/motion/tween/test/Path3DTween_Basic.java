package ijeoma.motion.tween.test;

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

import ijeoma.geom.Path3D;
import ijeoma.geom.tween.Path3DTween;
import ijeoma.motion.Motion;
import processing.core.PApplet;
import processing.core.PVector;

public class Path3DTween_Basic extends PApplet {
	int pointCount = 5;
	float pBegin, pEnd, pLength, pSegmentLength;

	PVector[] points = new PVector[pointCount];

	Path3D p;

	Path3DTween tp;

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

		// Path3D(PGraphics _g, PVector[] _vertices)
		p = new Path3D(points);
	}

	public void setupPathMotion() {
		Motion.setup(this);

		// TweenPath3D(String _name, Path3D _path, float _begin,
		// float _end, float _duration)
		tp = new Path3DTween(p, 0f, 1f, 300f);
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
		translate(tp.getX(), tp.getY(), tp.getZ());
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
		// Path3D(PGraphics _g, PVector[] _vertices, String _pathMode)
		// _pathMode is set to CUBIC by default but can also be set to LINEAR,
		// COSINE, HERMITE

		if (key == '1')
			p.setMode(Path3D.LINEAR);
		else if (key == '2')
			p.setMode(Path3D.COSINE);
		else if (key == '3')
			p.setMode(Path3D.CUBIC);
		else if (key == '4')
			p.setMode(Path3D.HERMITE);
		else {
			setupPath();
			setupPathMotion();
		}
	}
}