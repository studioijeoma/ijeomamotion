package ijeoma.geom.test;

/**
 Title: 2D Path 

 Author: Ekene Ijeoma
 Date: Febuary 2010    
 Description: Creates a random 2D path 
 Controls:
 SPACE: create random path
 1: linear mode
 2: cosine mode
 3: cubic mode
 4: hermite mode
 */

import ijeoma.geom.Path2D;
import processing.core.PApplet;
import processing.core.PVector;

public class Path2DTest extends PApplet {
	int vertexCount = 5;
	float pathBegin, pathEnd, pathLength, pathSegmentLength;

	PVector[] vertices = new PVector[vertexCount];

	Path2D path;

	@Override
	public void setup() {
		size(300, 300, P3D);

		pathBegin = 0;
		pathEnd = width;
		pathLength = pathEnd - pathBegin;
		pathSegmentLength = pathLength / (vertexCount - 1);

		setupPath();
	}

	public void setupPath() {
		for (int i = 0; i < vertexCount - 1; i++) {
			float x = pathBegin + pathSegmentLength * i;

			float y = random(100, 200);
			vertices[i] = new PVector(x, y, 0);
		}

		vertices[vertexCount - 1] = new PVector(pathEnd, random(100, 200), 0);

		path = new Path2D(this, vertices);
		
		
	}

	@Override
	public void draw() {
		background(255);

		path.draw();

		PVector pathVertex = path.getPoint((float) mouseX / width);

		ellipse(pathVertex.x, pathVertex.y, 10, 10);
	}

	@Override
	public void keyPressed() {
		if (key == ' ')
			setupPath();
		else if (key == '1')
			path.setMode(Path2D.LINEAR);
		else if (key == '2')
			path.setMode(Path2D.COSINE);
		else if (key == '3')
			path.setMode(Path2D.CUBIC);
		else if (key == '4')
			path.setMode(Path2D.HERMITE);
	}
}