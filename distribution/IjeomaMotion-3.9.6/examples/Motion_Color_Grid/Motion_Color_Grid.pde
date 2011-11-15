import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont f;

Tween[] tweens;

int gridRows;
int gridCols;
int gridSize;

void setup() {
  size(400, 400);

  f = createFont("Arial", 14);
  textFont(f);

  gridSize = 50;
  gridCols = width / gridSize;
  gridRows = height / gridSize;

  Motion.setup(this);

  tweens = new Tween[gridCols * gridRows];

  for (int i = 0; i < gridCols * gridCols; i++)
    tweens[i] = new Tween("grid"+1, 255, 0, 100);
}

void draw() {
  background(0);

  for (int i = 0; i < gridCols; i++) {
    for (int j = 0; j < gridRows; j++) {
      int k = i * gridCols + j;

      int x = i * gridSize;
      int y = j * gridSize;

      float d = dist(x, y, mouseX - gridSize / 2, mouseY - gridSize
        / 2);

      Tween t = tweens[k];
      int p = (int) t.getPosition();

      if (d < gridSize / 2)
        t.play();

      if (p < 255) {
        fill(p);
        rect(x, y, gridSize, gridSize);

        fill(0);
        text(p, x + gridSize / 2 - textWidth(p + "") / 2, y
          + gridSize / 2 + 4);
      }
    }
  }
}
