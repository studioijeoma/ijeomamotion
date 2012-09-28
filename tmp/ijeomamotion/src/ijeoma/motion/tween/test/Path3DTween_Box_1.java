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

import ijeoma.geom.Path3D;
import ijeoma.geom.tween.Path3DTween;
import ijeoma.motion.Motion;
import processing.core.PApplet;
import processing.core.PVector;

public class Path3DTween_Box_1 extends PApplet {
	PVector[] points;

	Path3D p;
	Path3DTween tp;

	int HALF_SIZE = 100;

	int[][] path = { { HALF_SIZE, HALF_SIZE, HALF_SIZE },
			{ -HALF_SIZE, -HALF_SIZE, -HALF_SIZE } };

	@Override
	public void setup() {
		size(400, 400, P3D);
		smooth();

		points = new PVector[path.length];

		for (int i = 0; i < path.length; i++)
			points[i] = new PVector(path[i][0], path[i][1], path[i][2]);

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
		p.drawLine(g);

		// This draws the small black box
		fill(0);
		pushMatrix();
		translate(tp.getX(), tp.getY(), tp.getZ());
		box(20);
		popMatrix();
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
