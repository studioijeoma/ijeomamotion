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

package ijeoma.motion.timeline.test;

import ijeoma.motion.Callback;
import ijeoma.motion.Motion;
import ijeoma.motion.timeline.Timeline;
import processing.core.PApplet;
import processing.core.PFont;

public class Timeline_Callback_Basic extends PApplet {
	PFont font;

	Timeline tl;

	String word = "";

	@Override
	public void setup() {
		size(400, 200);

		ellipseMode(CORNER);
		smooth();

		font = createFont("Arial", 50);
		textFont(font);

		Motion.setup(this);

		tl = new Timeline();
		// Timeline.addCallback(String _callbackObjectMethodName, float _time,
		// float _duration)
		tl.add(new Callback("zero"), 0);
		tl.add(new Callback("one"), 100);
		tl.add(new Callback("two"), 200);
		tl.add(new Callback("three"), 300);
		tl.add(new Callback("four"), 400);
		tl.add(new Callback("five"), 500);
		tl.repeat().play();
	}

	@Override
	public void draw() {
		background(255);

		fill(0);
		textFont(font, 36);
		text(word, width / 2 - textWidth(word) / 2, height / 2 + 36 / 2);

		textFont(font, 12);
		String time = (int) tl.getTime() + " / " + (int) tl.getDuration();
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void zero() {
		word = "ZERO";
		println(word);
	}

	public void one() {
		word = "ONE";
		println(word);
	}

	public void two() {
		word = "TWO";
		println(word);
	}

	public void three() {
		word = "THREE";
		println(word);
	}

	public void four() {
		word = "FOUR";
		println(word);
	}

	public void five() {
		word = "FIVE";
		println(word);
	}

	@Override
	public void keyPressed() {
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
