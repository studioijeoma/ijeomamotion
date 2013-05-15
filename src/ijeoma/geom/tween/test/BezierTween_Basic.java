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

package ijeoma.geom.tween.test;

import ijeoma.geom.Bezier;
import ijeoma.geom.tween.BezierTween;
import ijeoma.motion.Motion;
import processing.core.PApplet;

public class BezierTween_Basic extends PApplet {

	Bezier b1, b2;
	BezierTween bt;

	@Override
	public void setup() {
		size(100, 100);
		smooth();

		b1 = new Bezier(g, 85, 20, 10, 10, 90, 90, 15, 80);
		b2 = new Bezier(g, 30, 20, 80, 5, 80, 75, 30, 75);

		Motion.setup(this);

		bt = new BezierTween(b1, 0f, 1f, 100f).play();
	}

	@Override
	public void draw() {
		background(255);

		noFill();

		bt.getBezier().draw(g);

		fill(0);
		ellipse(bt.getX(), bt.getY(), 10, 10);

		String time = bt.getTime() + " / " + bt.getDuration();
		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		bt.play();
	}

	public void tweenBezierEnded(BezierTween _tb) {
		println("asdf");

		if (b1 == _tb.getBezier())
			_tb.setBezier(b2);
		else
			_tb.setBezier(b1);

		_tb.play();
	}
}
