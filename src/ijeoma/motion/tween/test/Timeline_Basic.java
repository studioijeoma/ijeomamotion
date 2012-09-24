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

import ijeoma.motion.Motion;
import ijeoma.motion.tween.KeyFrame;
import ijeoma.motion.tween.Timeline;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Timeline_Basic extends PApplet {
	int c1, c2, c3, c4, c5;
	float y1, y2, y3, y4, y5;

	Timeline tl;

	@Override
	public void setup() {
		size(400, 200);
		smooth();
		
		Motion.setup(this);

		c1 = c2 = c3 = c4 = c5 = color(255);

		y1 = y3 = y5 = -height;
		y2 = y4 = height;

		tl = new Timeline();
		tl.add(new Tween(50).add(this, "y1", (float) height).add(this, "c1",
				color(0)), 0);
		tl.add(new Tween(50).add(this, "y2", (float) -height).add(this, "c2",
				color(0)), 50);
		tl.add(new Tween(50).add(this, "y3", (float) height).add(this, "c3",
				color(0)), 100);
		tl.add(new Tween(50).add(this, "y4", (float) -height).add(this, "c4",
				color(0)), 150);
		tl.add(new Tween(50).add(this, "y5", (float) height).add(this, "c5",
				color(0)), 200);
		tl.repeat().play();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();
		fill(0);

		noStroke();
		fill(c1);
		rect(0, y1, 80, height);
		fill(c2);
		rect(80, y2, 80, height);
		fill(c3);
		rect(160, y3, 80, height);
		fill(c4);
		rect(240, y4, 80, height);
		fill(c5);
		rect(320, y5, 80, height);

		fill(0);
		println();
		Tween t = tl.getKeyFrame(1).getTween(0); 
		String time = (int) t.getTime() + " / " + (int) t.getDuration();
//		String time = (int) t.() + " / " + (int) t.getDuration();
		text(time, width - textWidth(time) - 10, height - 30);

		time = (int) tl.getTime() + " / " + (int) tl.getDuration();
		text(time, width - textWidth(time) - 10, height - 10);
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
