/**
 * ijeomamotion
 * A library for sketching animations with numbers, colors vectors, beziers, curves and more! 
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
 * @modified    09/20/2012
 * @version     4 (26)
 */

package ijeoma.motion.tween.test;

import ijeoma.geom.Path3D;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Path3DTween;
import processing.core.PApplet;
import processing.core.PVector;

public class Path3DTween_Box_2 extends PApplet {
	PVector[] points;

	Path3D p;
	Path3DTween tp;

	int HALF_SIZE = 100;
	float[][] path = { { HALF_SIZE, HALF_SIZE, HALF_SIZE },
			{ HALF_SIZE * 1.5f, 0, 0 }, { HALF_SIZE, -HALF_SIZE, -HALF_SIZE },
			{ 0, -HALF_SIZE * 1.5f, 0 }, { -HALF_SIZE, -HALF_SIZE, HALF_SIZE },
			{ -HALF_SIZE * 1.5f, 0, 0 }, { -HALF_SIZE, HALF_SIZE, -HALF_SIZE },
			{ 0, HALF_SIZE * 1.5f, 0 } };

	@Override
	public void setup() {
		size(400, 400, P3D);
		smooth();

		points = new PVector[path.length + 1];

		for (int i = 0; i < path.length; i++)
			points[i] = new PVector(path[i][0], path[i][1], path[i][2]);

		points[path.length] = new PVector(path[0][0], path[0][1], path[0][2]);

		p = new Path3D(points);

		Motion.setup(this);

		tp = new Path3DTween(p, 0f, 1f, 300f);
		tp.repeat();
		tp.play();
	}

	@Override
	public void draw() {
		background(255);

		translate(width / 2, height / 2, 0);
		rotateY(frameCount / 100.0f);
		rotateX(2.0f);
		rotateZ(frameCount / 200.0f);

		// This draws the large box
		noFill();
		stroke(200);
		box(HALF_SIZE * 2);

		// This draws the path
		noFill();
		stroke(100);
		beginShape();
		p.draw(g);
		endShape();

		// This draws the small black box
		fill(0);
		translate(tp.getX(), tp.getY(), tp.getZ());

		// The above can also be written as
		// PVector pathPoint = tp.getPoint();
		// translate(pathPoint.x, pathPoint.y, pathPoint.z);

		box(20);
	}

	@Override
	public void keyPressed() {
		// Path3D(PApplet _parent, PVector[] _points, String _pathMode)
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
		else
			tp.play();
	}
}
