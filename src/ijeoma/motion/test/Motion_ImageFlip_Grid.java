//A MotionController is an object which controls other Motion objects.
//TweenParallel, TweenSequence and Timeline inherit MotionController and Tween inherits Motion.

package ijeoma.motion.test;

import ijeoma.motion.*;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.*;
import ijeoma.motion.tween.test.Tween_Events_2.TweenEventListener;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Motion_ImageFlip_Grid extends PApplet {
	PFont f;

	Tween[] tweens;

	int gridRows;
	int gridCols;
	int gridSize;

	PImage pi;

	public void setup() {
		size(400, 400);

		gridSize = 50;
		gridCols = width / gridSize;
		gridRows = height / gridSize;

		f = createFont("Arial", gridSize);
		textFont(f);

		pi = loadImage("640.jpg");

		Motion.setup(this);

		tweens = new Tween[gridCols * gridRows];

		for (int i = 0; i < gridCols * gridCols; i++) {
			Tween t = new Tween("grid" + i, 1f, -1f, 75, 0, Tween.CIRC_BOTH);
			tweens[i] = t;
		}
	}

	public void draw() {
		background(0);

		for (int i = 0; i < gridCols; i++) {
			for (int j = 0; j < gridRows; j++) {
				int k = i * gridCols + j;

				Tween t = tweens[k];

				float p = t.getPosition();
				float sp = t.getSeekPosition();

				int x = i * gridSize;
				int y = j * gridSize;

				float d = dist(x, y, mouseX - gridSize / 2, mouseY - gridSize
						/ 2);

				if (d < gridSize / 2 && !t.isPlaying())
					t.play();

				pushMatrix();
				translate(x + (gridSize * sp), y);
				scale(p, 1);
				// stroke(255);
				// noFill();
				// rect(0, 0, gridSize, gridSize);
				image(pi, 0, 0, gridSize, gridSize);
				popMatrix();
			}
		}
	}
}
