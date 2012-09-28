import ijeoma.motion.*;
import ijeoma.motion.tween.*; 

int c1, c2, c3, c4, c5;
float y1, y2, y3, y4, y5;

Timeline tl;

void setup() {
  size(400, 200);
  smooth();

  Motion.setup(this);

  c1 = c2 = c3 = c4 = c5 = color(255);

  y1 = y3 = y5 = -height;
  y2 = y4 = height;

  tl = new Timeline();
	  tl.add(new Tween(50).add(this, "y1", (float)height).add(this, "c1", 
	  color(0)), 0);
	  tl.add(new Tween(50).add(this, "y2", (float)-height).add(this, "c2", 
	  color(0)), 50);
	  tl.add(new Tween(50).add(this, "y3", (float)height).add(this, "c3", 
	  color(0)), 100);
	  tl.add(new Tween(50).add(this, "y4", (float)-height).add(this, "c4", 
	  color(0)), 150);
	  tl.add(new Tween(50).add(this, "y5", (float)height).add(this, "c5", 
	  color(0)), 200);
  tl.repeat().play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  
  tl.update();

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
  String time = (int) tl.getTime() + " / " + (int) tl.getDuration();
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
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

void mousePressed() {
  tl.pause();
}

void mouseReleased() {
  tl.resume();
}

void mouseDragged() {
  tl.seek(norm(mouseX, 0, width));
} 

