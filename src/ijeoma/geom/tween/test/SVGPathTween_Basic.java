package ijeoma.geom.tween.test;

import ijeoma.geom.SVGPath;
import ijeoma.geom.tween.SVGPathTween;
import ijeoma.motion.Motion;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

/**
 * ##library.name## ##library.sentence## ##library.url##
 * 
 * Copyright ##copyright## ##author##
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * @author ##author##
 * @modified ##date##
 * @version ##library.prettyVersion## (##library.version##)
 */

public class SVGPathTween_Basic extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PShape s;
	SVGPath p;
	SVGPathTween t;

	@Override
	public void setup() {
		size(800, 600);
		smooth();

		s = loadShape(dataPath("Cloud.svg"));

		PShape path = s.findChild("path2890");
		// path = s.getChild(1);
		println(path.getChildCount());
		println(path.getVertexCount());
		println(path.getVertexCodeCount());

		Motion.setup(this);

		p = new SVGPath(g, path);
		t = new SVGPathTween(p, 0, 1, 500);

		t.play();
	}

	@Override
	public void draw() {
		background(255);

		stroke(0);
		noFill();
		p.draw();

		PVector v = t.getPoint();

		text(v.toString(), 25, 25);
		float position = (float) mouseX / width;
		text(p.getPoint(position).toString() + " " + position, 25, 50);

		fill(255, 0, 0);
		ellipse(v.x, v.y, 10, 10);

		fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getPosition()));

		String time = t.getTime() + " / " + t.getDuration();
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		t.play();
	}
}
