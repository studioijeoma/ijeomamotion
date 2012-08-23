/**
 * ijeomamotion
 * A collection of utilities creating flash-like animations.
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
 * @modified    08/21/2012
 * @version     4 (26)
 */

package ijeoma.motion.timeline.test;

import ijeoma.motion.Motion;
import ijeoma.motion.timeline.KeyFrame;
import ijeoma.motion.timeline.Timeline;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;

public class Timeline_Keyframe_Basic extends PApplet {
	PFont font;

	Timeline t;

	float k1y1;
	float k2y1, k2y2;

	@Override
	public void setup() {
		size(400, 200);

		ellipseMode(CORNER);
		smooth();

		font = createFont("Arial", 36);

		Motion.setup(this);

		t = new Timeline();

		k1y1 = -height;

		k2y1 = -height;
		k2y2 = height;

		KeyFrame kf1 = new KeyFrame(0);
		kf1.add(new Tween(this, "k1y1", height, 100));
		// kf1.addCallback(new Callback("k1"));

		KeyFrame kf2 = new KeyFrame(100);
		kf2.add(new Tween(this, "k2y1", height, 100));
		kf2.add(new Tween(this, "k2y2", -height, 100));

		// KeyFrame kf3 = new KeyFrame(200);
		// kf3.add(new Tween(-height, height, 100));
		// kf3.add(new Tween(height, -height, 100));
		// kf3.add(new Tween(-height, height, 100));
		//
		// KeyFrame kf4 = new KeyFrame(300);
		// kf4.add(new Tween(-height, height, 100));
		// kf4.add(new Tween(height, -height, 100));
		// kf4.add(new Tween(-height, height, 100));
		// kf4.add(new Tween(height, -height, 100));
		//
		// KeyFrame kf5 = new KeyFrame(400);
		// kf5.add(new Tween(-height, height, 100));
		// kf5.add(new Tween(height, -height, 100));
		// kf5.add(new Tween(-height, height, 100));
		// kf5.add(new Tween(height, -height, 100));
		// kf5.add(new Tween(-height, height, 100));

		t.addKeyFrame(kf1, "100");
		t.addKeyFrame(kf2, "200");
		// t.addKeyFrame(kf3, "300");
		// t.addKeyFrame(kf4, "400");
		// t.addKeyFrame(kf5, "500");

		t.setTimeScale(2f);
		t.repeat();
		t.play();
	}

	@Override
	public void draw() {
		background(255);

		KeyFrame[] currentKfs = t.getCurrentKeyFrames();

		// PApplet.println(currentKfs);

		for (int i = 0; i < currentKfs.length; i++) {
			for (int k = 0; k < currentKfs[i].getCount(); k++) {
				float rectWidth = width / currentKfs[i].getCount();
				Tween t = currentKfs[i].getTween(k);
				float rectY = t.getPosition();

				noStroke();
				fill(0);
				rect(k * rectWidth, rectY, rectWidth, height);
			}

			// String kfName = currentKfs[0].getName();

			// noStroke();
			// fill(255);
			// textFont(font, 36);
			// text(kfName, width / 2 - textWidth(kfName) / 2, height / 2 + 36 /
			// 2);
		}

		drawGUI();

	}

	public void drawGUI() {
		int seekColor = lerpColor(color(0, 255, 0), color(255, 0, 0),
				t.getPosition());

		stroke(seekColor);
		line(t.getPosition() * width, 0, t.getPosition() * width, height);

		String timeAsString = t.getTime() + " / " + t.getDuration();

		fill(seekColor);
		textFont(font, 12);
		text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		if (key == '1')
			t.gotoAndPlay("ONE");
		else if (key == '2')
			t.gotoAndPlay("TWO");
		else if (key == '3')
			t.gotoAndPlay("THREE");
		else if (key == '4')
			t.gotoAndPlay("FOUR");
		else if (key == '5')
			t.gotoAndPlay("FIVE");
		else
			t.play();
	}

	@Override
	public void mousePressed() {
		t.pause();
	}

	@Override
	public void mouseReleased() {
		t.resume();
	}

	@Override
	public void mouseDragged() {
		t.seek(norm(mouseX, 0, width));
	}
}
