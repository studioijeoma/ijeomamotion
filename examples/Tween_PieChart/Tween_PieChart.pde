import ijeoma.motion.tween.*;
import ijeoma.motion.easing.*;

PFont font;
int fontSize = 16;

TweenGroup tweenGroup;

int   barCount = 1;
float barWidth = 0;
float barHeightMin;
float barHeightMax;
int   barTopMargin = 24;

boolean changeDurationOnEnd = false;

void setup() {
  size(400, 200, P3D);

  noStroke(); 

  font = loadFont("Ziggurat-HTF-Black-32.vlw");
  textFont(font, fontSize);

  barWidth = width / barCount;
  barHeightMax = height;
  barHeightMin = height / 5;

  tweenGroup = new TweenGroup(this);

  for (int i = 0; i < barCount; i++) 
    tweenGroup.addTween(new Tween(this, "Tween" + i, 0f, random(barHeightMin, barHeightMax), 200 , Tween.FRAMES, 0f, Tween.QUAD_EASE_IN));

  tweenGroup.play();
}

void draw() {
  background(255);

  for (int i = 0; i < barCount; i++) {
    Tween t = tweenGroup.getTween(i);

    float h = t.getPosition();
    float r = map(h, barHeightMax/4, barHeightMax, 0, 255);

    String p = (int) h +"";

    fill(color(r, 0, 0));
    rect(i * barWidth, height, barWidth, -h);

    fill(255);
    text(p, i * barWidth + (barWidth - textWidth(p))/2, height - (h - barTopMargin));
  }
}

void tweenGroupStarted(TweenGroup _tg) {
  println(_tg + " started");
}

void tweenGroupEnded(TweenGroup _tg) {
  println(_tg + " ended");

  for (int i = 0; i < _tg.getChildCount(); i++) {
    _tg.getTween(i).setEnd(random(barHeightMin, barHeightMax));
    _tg.getTween(i).setDuration((changeDurationOnEnd) ? random(50,200) : 200);
  }

  _tg.play();
}

void keyPressed() {
  changeDurationOnEnd = !changeDurationOnEnd;

  if(!changeDurationOnEnd) 
    tweenGroup.play();
}





