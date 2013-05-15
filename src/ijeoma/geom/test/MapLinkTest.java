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

package ijeoma.geom.test;

import java.util.List;

import ijeoma.geom.Path;
import ijeoma.motion.Motion;
import processing.core.PApplet;
import processing.core.PVector;

public class MapLinkTest extends PApplet {
	MapLink link1, link2;

	@Override
	public void setup() {
		size(800, 600, P3D);

		Motion.setup(this);

		setupPath();
	}

	public void setupPath() {
		link1 = new MapLink();
		// link1.fadeIn(100, 0);
		link1.animateIn(100, 0);
		// link2 = new MapLink();
		// link2.setPoints(link1.getPointList());
		// link1.toUniform(100);
	}

	@Override
	public void draw() {
		background(0);

		link1.draw(g);
		// link2.draw(g);

		// PVector p = link.getPointAt((float) mouseX / width);
		// noStroke();
		// fill(255, 0, 0);
		// ellipse(p.x, p.y, 10, 10);
	}

	public void mouseMoved() {
	}

	public void keyPressed() {
		if (key == ' ')
			setupPath();
		else if (key == '1')
			link1.animateIn(100, 0);
		else if (key == '2')
			link1.animateOut(100, 0);
		else if (key == '3')
			link1.fadeIn(100, 0);
		else if (key == '4')
			link1.fadeOut(100, 0);
	}
}