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

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

	PFont f;

	TweenParallel tp;

	float x1 = -width;
	float x2 = width;

	public void setup() {
		size(400, 200);

		ellipseMode(CORNER);
		smooth();

		f = createFont("Arial", 12);
		textFont(f);

		Motion.setup(this);

		tp = new TweenParallel();
		tp.add(new Tween(this, "x1", width, 100), "x1")
				.add(new Tween(this, "x2", -width, 200), "x2").play();

		// The above could also be written as...
		// tp.addTween(this, "x1", width, 100).addTween(this, "x2", -width,
		// 200).play();

		// except the tween's name is set using the variable's name by default
		// The tween's name is used to access the Tween using the
		// TweenParallel.getTween(name) method
	}

	public void draw() {
		background(255);

		stroke(255);
		fill(255 / 2f);
		rect(x1, 0, width, height / 2);
		rect(x2, height / 2, width, height / 2);

		String time;

		time = (int) tp.getTween("x1").getTime() + " / "
				+ (int) tp.getTween("x1").getDuration();

		fill(0);
		text(time, 10, 10 + 12);

		time = (int) tp.getTween("x2").getTime() + " / "
				+ (int) tp.getTween("x2").getDuration();

		fill(0);
		text(time, 10, 100 + 10 + 12);

		time = (int) tp.getTime() + " / " + (int) tp.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		tp.play();
	}

	public void mousePressed() {
		tp.pause();
	}

	public void mouseReleased() {
		tp.resume();
	}

	public void mouseDragged() {
		tp.seek((float) mouseX / width);
	} 