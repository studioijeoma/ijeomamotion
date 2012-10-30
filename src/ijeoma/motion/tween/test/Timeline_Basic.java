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

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.*;

public class Timeline_Basic extends PApplet {
	int c1, c2, c3, c4, c5;
	float y1, y2, y3, y4, y5;
	float w;

	Timeline tl;

	@Override
	public void setup() {
		size(600, 300);
		smooth();

		Motion.setup(this);

		c1 = c2 = c3 = c4 = c5 = color(255);

		y1 = y3 = y5 = -height;
		y2 = y4 = height;

		w = width / 5f;

		int d = 100;

		tl = new Timeline();
		tl.add(new Tween(d).add(this, "y1", (float) height).addColor(this,
				"c1", color(0)), 0);
		 tl.add(new Tween(d).add(this, "y2", (float) -height).addColor(this,
		 "c2", color(0)), d);
		 tl.add(new Tween(d).add(this, "y3", (float) height).addColor(this,
		 "c3", color(0)), d * 2);
		 tl.add(new Tween(d).add(this, "y4", (float) -height).addColor(this,
		 "c4", color(0)), d * 3);
		 tl.add(new Tween(d).add(this, "y5", (float) height).addColor(this,
		 "c5", color(0)), d * 4);
		tl.repeat().reverse().play();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();
		fill(0);

		noStroke();
		fill(c1);
		rect(0, y1, w, height);
		 fill(c2);
		 rect(w, y2, w, height);
		 fill(c3);
		 rect(w * 2, y3, w, height);
		 fill(c4);
		 rect(w * 3, y4, w, height);
		 fill(c5);
		 rect(w * 4, y5, w, height);

		String time = tl.get(0).getTime() + " / " + tl.get(0).getDuration();
		fill((tl.get(0).isPlaying() ? color(0, 255, 0) : color(255, 0, 0)));
		text(time, 0, height - 10);

		time = tl.get(1).getTime() + " / " + tl.get(1).getDuration();
		fill((tl.get(1).isPlaying() ? color(0, 255, 0) : color(255, 0, 0)));
		text(time, w, height - 10);

		time = tl.get(2).getTime() + " / " + tl.get(2).getDuration();
		fill((tl.get(2).isPlaying() ? color(0, 255, 0) : color(255, 0, 0)));
		text(time, w * 2, height - 10);

		time = tl.get(3).getTime() + " / " + tl.get(3).getDuration();
		fill((tl.get(3).isPlaying() ? color(0, 255, 0) : color(255, 0, 0)));
		text(time, w * 3, height - 10);

		time = tl.get(4).getTime() + " / " + tl.get(4).getDuration();
		fill((tl.get(4).isPlaying() ? color(0, 255, 0) : color(255, 0, 0)));
		text(time, w * 4, height - 10);

		time = tl.getTime() + " / " + tl.getDuration();
		fill(0);
		text(time, width - textWidth(time) - 10, 10 + 12);
	}

	@Override
	public void keyPressed() {
		if (key == '1')
			tl.gotoAndPlay(0);
		else if (key == '2')
			tl.gotoAndPlay(50);
		else if (key == '3')
			tl.gotoAndPlay(100);
		else if (key == '4')
			tl.gotoAndPlay(150);
		else if (key == '5')
			tl.gotoAndPlay(200);
		else
			tl.play();
	}

	@Override
	public void mousePressed() {
		// tl.play();
		tl.pause();
	}

	@Override
	public void mouseReleased() {
		tl.resume();
	}

	@Override
	public void mouseDragged() {
		tl.seek(norm(mouseX, 0, width));
	}
}
