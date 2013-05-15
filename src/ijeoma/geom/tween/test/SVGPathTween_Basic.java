package ijeoma.geom.tween.test;

import ijeoma.geom.SVGPath;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

/**
 * ijeomamotion A cross-mode Processing library for sketching animations with numbers, colors vectors, beziers, curves and more.  http://ekeneijeoma.com/processing/ijeomamotion
 * 
 * Copyright (C) 2012 Ekene Ijeoma http://ekeneijeoma.com
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
 * @author Ekene Ijeoma http://ekeneijeoma.com
 * @modified 05/13/2013
 * @version 5.4.1 (54)
 */

public class SVGPathTween_Basic extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PShape s;
	SVGPath p;
	Tween t;

	@Override
	public void setup() {
		size(800, 600);
		smooth();

		Motion.setup(this);

		s = loadShape(dataPath("Cloud.svg"));
		PShape path = s.findChild("path2890");

		println(path);

		p = new SVGPath(path);

		t = new Tween(100).addPath(p, 1).play();
	}

	@Override
	public void draw() {
		background(255);

		stroke(0);
		noFill();
		p.draw(g);

		PVector v = p.get();

		text(v.toString(), 25, 25);
		float position = (float) mouseX / width;
		text(p.getPointAt(position).toString() + " " + position, 25, 50);

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
