//A MotionController is an object which controls other Motion objects.
//TweenParallel, TweenSequence and Timeline inherit MotionController and Tween inherits Motion.

package ijeoma.motion.test;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Motion_TextFlip_Grid extends PApplet {
	PFont f;

	Tween[] tweens;

	int gridRows;
	int gridCols;
	int gridSize;

	PImage pi;

	String text = "FLIP";

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

		int letterIndex = 0;

		for (int i = 0; i < gridCols * gridCols; i++) {
			tweens[i] = new Tween(str(text.charAt(letterIndex)), 1f, -1f, 100);
			letterIndex = (letterIndex < text.length() - 1) ? letterIndex += 1
					: 0;
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
				float h = textAscent();

				float d = dist(x, y, mouseX - gridSize / 2, mouseY - gridSize
						/ 2);

				if (d < gridSize / 2 && !t.isPlaying())
					t.play();

				pushMatrix();
				translate(x, y + gridSize - (h * sp));
				scale(1, p);
				// fill(255 - (255 * sp));
				fill(255);
				text(tweens[k].getName(), 0, 0);
				popMatrix();
			}
		}
	}
}
