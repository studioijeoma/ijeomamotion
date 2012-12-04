import ijeoma.motion.*; 
import ijeoma.motion.tween.*; 

PFont font;

Timeline tl;

String word = "";

void setup() {
  size(400, 200);

  ellipseMode(CORNER);
  smooth();

  font = createFont("Arial", 50);
  textFont(font);

  Motion.setup(this);

  tl = new Timeline();
  tl.call(this, "zero", 0).call(this, "one", 100).call(this, "two", 200)
    .call(this, "three", 300).call(this, "four", 400)
      .call(this, "five", 500);
  tl.repeat().play();
}


void draw() {
  background(255);

  fill(0);
  textFont(font, 36);
  text(word, width / 2 - textWidth(word) / 2, height / 2 + 36 / 2);

  textFont(font, 12);
  String time = (int) tl.getTime() + " / " + (int) tl.getDuration();
  text(time, width - textWidth(time) - 10, height - 10);
}

void zero() {
  word = "ZERO";
  println(word);
}

void one() {
  word = "ONE";
  println(word);
}

void two() {
  word = "TWO";
  println(word);
}

void three() {
  word = "THREE";
  println(word);
}

void four() {
  word = "FOUR";
  println(word);
}

void five() {
  word = "FIVE";
  println(word);
}

void keyPressed() {
  tl.play();
}

void mousePressed() {
  tl.pause();
}

void mouseReleased() {
  tl.resume();
  word = "";
}

void mouseDragged() {
  tl.seek(norm(mouseX, 0, width));
}
