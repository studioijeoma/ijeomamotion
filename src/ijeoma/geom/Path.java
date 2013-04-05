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

package ijeoma.geom;

import ijeoma.math.Interpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Path {
	protected int segmentIndex = 0;
	protected int segmentVertexIndex = 0;
	protected float segmentPosition = 0;
	protected float segmentPositionRange = 0;

	// protected int resolution = 10;

	protected float tension = 0;
	protected float bias = 0;

	protected ArrayList<PVector> vertices = new ArrayList<PVector>();

	protected float length = 0;

	boolean computed = false;

	public static String LINEAR = "linear";
	public static String COSINE = "cosine";
	public static String CUBIC = "cubic";
	public static String HERMITE = "hermite";

	protected String mode = LINEAR;

	boolean is3D = false;

	public Path() {
	}

	public Path(PVector[] vertices) {
		setVertices(vertices);
	}

	public Path(List<PVector> vertexList) {
		setVertices(vertexList);
	}

	public Path(PVector[] vertices, String _mode) {
		setVertices(vertices);

		mode = _mode;
	}

	public Path(PVector[] vertices, String mode, float tension, float bias) {
		setVertices(vertices);

		this.mode = mode;

		this.tension = tension;
		this.bias = bias;
	}

	public void drawPoints(PGraphics g) {
		draw(g, PConstants.POINTS, 1);
	}

	public void drawPoints(PGraphics g, float t) {
		draw(g, PConstants.POINTS, t);
	}

	public void drawPoints(PGraphics g, int steps, float t) {
		draw(g, PConstants.POINTS, steps, t);
	}

	public void drawLine(PGraphics g) {
		draw(g, PConstants.LINE, 1);
	}

	public void drawLine(PGraphics g, float t) {
		draw(g, PConstants.LINE, t);
	}

	public void drawLine(PGraphics g, int steps, float t) {
		draw(g, PConstants.LINE, steps, t);
	}

	public void draw(PGraphics g, int mode, float t) {
		draw(g, mode, 1, t);
	}

	public void draw(PGraphics g, int mode, int steps, float t) {
		// int vertexCount = vertices.size() * resolution;
		int vertexCount = (int) (vertices.size() * steps * t);

		if (mode == PConstants.LINE)
			g.beginShape();
		else
			g.beginShape(mode);

		for (int i = 0; i <= vertexCount; i += steps) {
			PVector p1 = getVertexAt((float) i / vertexCount);

			if (is3D)
				g.vertex(p1.x, p1.y, p1.z);
			else
				g.vertex(p1.x, p1.y);
		}
		g.endShape();
	}

	// public void drawPoints(PGraphics _g) {
	// _g.beginShape();
	// float s = _g.getStyle().strokeWeight * 2f;
	// for (PVector p : points)
	// _g.ellipse(p.x, p.y, s, s);
	// _g.endShape();
	// }

	public Path addVertex(float x, float y) {
		return addVertex(new PVector(x, y));
	}

	public Path addVertex(PVector vertex) {
		vertices.add(vertex);

		segmentPositionRange = (1f / (vertices.size() - 1));

		return this;
	}

	public Path addVertices(PVector[] vertices) {
		this.vertices.addAll(new ArrayList<PVector>(Arrays.asList(vertices)));

		segmentPositionRange = (1f / (this.vertices.size() - 1));

		return this;
	}

	public Path addVertices(List<PVector> vertexList) {
		vertices.addAll(vertexList);

		segmentPositionRange = (1f / (vertices.size() - 1));

		return this;
	}

	public void removeAll() {
		vertices.clear();
	}

	public PVector getVertexAt(float _position) {
		PVector point = new PVector();

		if (!computed)
			compute();

		if (_position < 1) {
			segmentVertexIndex = PApplet.floor((vertices.size() - 1)
					* _position);
			segmentPosition = PApplet.map((_position % segmentPositionRange),
					0, segmentPositionRange, 0, 1);
		} else {
			segmentVertexIndex = (vertices.size() - 2);
			segmentPosition = 1;
		}

		PVector v1, v2, v3, v4;

		v2 = vertices.get(segmentVertexIndex);
		v3 = vertices.get(segmentVertexIndex + 1);

		v1 = v4 = new PVector();

		if (segmentVertexIndex == 0) {
			PVector segmentBegin = vertices.get(0);
			PVector segmentEnd = vertices.get(1);
			PVector segmentSlope = PVector.sub(segmentEnd, segmentBegin);

			v1 = PVector.sub(segmentEnd, segmentSlope);
		} else {
			v1 = vertices.get(segmentVertexIndex - 1);
		}

		if ((segmentVertexIndex + 1) == vertices.size() - 1) {
			PVector segmentBegin = vertices.get(vertices.size() - 2);
			PVector segmentEnd = vertices.get(vertices.size() - 1);
			PVector segmentSlope = PVector.sub(segmentEnd, segmentBegin);

			v4 = PVector.add(segmentEnd, segmentSlope);
		} else {
			v4 = vertices.get((segmentVertexIndex + 1) + 1);
		}

		if (mode.equals(LINEAR)) {
			point.x = Interpolator.linear(v2.x, v3.x, segmentPosition);
			point.y = Interpolator.linear(v2.y, v3.y, segmentPosition);
			if (is3D)
				point.z = Interpolator.linear(v2.z, v3.z, segmentPosition);
		} else if (mode.equals(COSINE)) {
			point.x = Interpolator.cosine(v2.x, v3.x, segmentPosition);
			point.y = Interpolator.cosine(v2.y, v3.y, segmentPosition);
			if (is3D)
				point.z = Interpolator.cosine(v2.z, v3.z, segmentPosition);
		} else if (mode.equals(CUBIC)) {
			point.x = Interpolator.cubic(v1.x, v2.x, v3.x, v4.x,
					segmentPosition);
			point.y = Interpolator.cubic(v1.y, v2.y, v3.y, v4.y,
					segmentPosition);
			if (is3D)
				point.z = Interpolator.cubic(v1.z, v2.z, v3.z, v4.z,
						segmentPosition);
		} else if (mode.equals(HERMITE)) {
			point.x = Interpolator.hermite(v1.x, v2.x, v3.x, v4.x,
					segmentPosition, tension, bias);
			point.y = Interpolator.hermite(v1.y, v2.y, v3.y, v4.y,
					segmentPosition, tension, bias);
			if (is3D)
				point.z = Interpolator.hermite(v1.z, v2.z, v3.z, v4.z,
						segmentPosition, tension, bias);
		}

		return point;
	}

	public Path setVertices(PVector[] _points) {
		return setVertices(new ArrayList<PVector>(Arrays.asList(_points)));
	}

	public Path setVertices(List<PVector> _points) {
		vertices = new ArrayList<PVector>(_points);

		for (PVector p : vertices)
			if (p.z != 0) {
				is3D = true;
				break;
			}

		segmentPositionRange = (1f / (vertices.size() - 1));

		compute();

		return this;
	}

	public PVector[] getVertices() {
		return vertices.toArray(new PVector[vertices.size()]);
	}

	public List<PVector> getVertexList() {
		return vertices;
	}

	public int getVertexCount() {
		return vertices.size();
	}

	public Path simplify(float tolerance) {
		PVector[] simplifiedPoints = Simplify.simplify(
				vertices.toArray(new PVector[vertices.size()]), tolerance);
		vertices = new ArrayList<PVector>(Arrays.asList(simplifiedPoints));
		return this;
	}

	public Path simplify(float tolerance, boolean highestQuality) {
		PVector[] simplifiedPoints = Simplify.simplify(
				vertices.toArray(new PVector[vertices.size()]), tolerance,
				highestQuality);
		vertices = new ArrayList<PVector>(Arrays.asList(simplifiedPoints));
		return this;
	}

	public void compute() {
		computeLength();

		computed = true;
	}

	public void computeLength() {
		length = 0;

		for (int i = 0; i < vertices.size() - 1; i++) {
			PVector d1 = vertices.get(i);
			PVector d2 = vertices.get(i + 1);

			length += PApplet.dist(d1.x, d1.y, d2.x, d2.y);
		}
	}

	public void setTension(float _tension) {
		tension = _tension;
	}

	public float getTension() {
		return tension;
	}

	public Path setBias(float _bias) {
		bias = _bias;
		return this;
	}

	public float getBias() {
		return bias;
	}

	public Path setMode(String _mode) {
		mode = _mode;
		return this;
	}

	public int getSegmentIndex() {
		return segmentIndex;
	}

	public int getSegmentPointIndex() {
		return segmentVertexIndex;
	}

	public float getSegmentPosition() {
		return segmentPosition;
	}

	public float getLength() {
		return length;
	}

	// public float getHeading2DAt(float t) {
	// // var angle = Math.atan2(vectorA.y - vectorB.y, vectorA.x - vectorB.x)
	// // var angle = Math.atan2(vectorA.y - vectorB.y, vectorA.x - vectorB.x)
	// return 0;
	// }
	//
	// public float getHeadingAt(float t) {
	// // // Make up two vectors
	// // PVector v1 = new PVector(5, -20, -14);
	// // PVector v2 = new PVector(-1, 3, 2);
	// //
	// // // Store some information about them for below
	// // float dot = v1.dot(v2);
	// // float lengthA = v1.;
	// // float lengthB = v2.;
	// //
	// // // Now to find the angle
	// // float theta = acos( dot / (lengthA * lengthB) ); // Theta = 3.06
	// // radians or 175.87 degrees
	// //
	// // return theta;
	// return 0;
	// }

	// public float[] getBounds() {
	// return float[] {};//pointMin.x, pointMin.y, width,height}
	// }
}