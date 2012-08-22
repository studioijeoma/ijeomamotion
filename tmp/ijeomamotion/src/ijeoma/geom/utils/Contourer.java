package ijeoma.geom.utils;

import java.util.Iterator;
import processing.core.*;

public class Contourer extends Conrec implements Iterable<Contour> {
	PApplet parent;

	PImage image;
	int[] pixelData;

	public int width = 0, height = 0;
	public int columns = 0, rows = 0;

	int resolution = 10;

	double[][] contourData;
	double[] contourDataXs;
	double[] contourDataYs;
	double[] contourDataZs;

	int contourLevels = 1;
	int contourElevation = 1;

	PImage dataImage;

	public Contourer(PApplet _parent) {
		super();

		parent = _parent;
	}

	public Contourer(PApplet _parent, int _width, int _height) {
		super();

		parent = _parent;

		width = _width;
		height = _height;
	}

	private void compute() {
		computeContours(contourData, 0, contourDataXs.length - 1, 0,
				contourDataYs.length - 1, contourDataXs, contourDataYs,
				contourDataZs.length, contourDataZs);
	}

	public void compute(double[][] _contourData, double[] _contourDataXs,
			double[] _contourDataYs, double[] _contourDataZs) {
		contourData = _contourData;
		contourDataXs = _contourDataXs;
		contourDataYs = _contourDataYs;
		contourDataZs = _contourDataZs;

		compute();
	}

	public void computeFromData(double[][] _contourData,
			double[] _contourDataXs, double[] _contourDataYs,
			double[] _contourDataZs, int _contourElevation, int _contourLevels) {
		contourData = _contourData;
		contourDataXs = _contourDataXs;
		contourDataYs = _contourDataYs;
		contourDataZs = _contourDataZs;

		contourElevation = _contourElevation;
		contourLevels = _contourLevels;

		compute();

		createDataImage();
	}

	public void computeFromImage(PImage _image, int _resolution,
			int _contourLevels, int _contourElevation) {
		image = _image.get();

		resolution = _resolution;

		contourLevels = _contourLevels;
		contourElevation = _contourElevation;

		width = image.width;
		height = image.height;

		setResolution(resolution);

		computeContourDataFromImage();
		computeContourDataXYs();
		computeContourDataZs();
		compute();

		// createDataImage();
	}

	public void computeFromData() {
		computeContourDataFromImage();
		computeContourDataXYs();
		computeContourDataZs();
		compute();

		// createDataImage();
	}

	public void computeFromImage() {
		computeContourDataFromImage();
		computeContourDataXYs();
		computeContourDataZs();
		compute();

		// createDataImage();
	}

	private void computeContourDataFromImage() {
		contourData = new double[columns][rows];

		for (int c = 0; c < columns; c++)
			for (int r = 0; r < rows; r++) {
				// contourData[c][r] = parent
				// .brightness(image.pixels[(c * resolution)
				// + (r * resolution) * image.width]) / 255;
				contourData[c][r] = getImageAverageBrightnessAt(image, c, r,
						resolution, resolution) / 255;
			}
	}

	private void computeContourDataXYs() {
		// contourDataXs = new double[contourData.length];
		contourDataXs = new double[columns];
		for (int i = 0; i < contourDataXs.length; i++)
			contourDataXs[i] = (double) i * resolution;

		// contourDataYs = new double[contourData[0].length];
		contourDataYs = new double[rows];
		for (int i = 0; i < contourDataYs.length; i++)
			contourDataYs[i] = (double) i * resolution;
	}

	private void computeContourDataZs() {
		contourDataZs = new double[contourLevels];
		for (int i = 0; i < contourDataZs.length; i++)
			contourDataZs[i] = i * ((double) 1 / contourLevels);
	}

	private void createDataImage() {
		dataImage = parent.createImage(width, height, PApplet.RGB);

		dataImage.loadPixels();
		for (int c = 0; c < contourDataXs.length; c++) {
			for (int r = 0; r < contourDataYs.length; r++) {
				int cX = (int) (c * resolution);
				int rY = (int) (r * resolution);

				for (int x = cX; x < cX + resolution; x++)
					for (int y = rY; y < rY + resolution; y++)
						dataImage.pixels[x + y * dataImage.width] = parent
								.color((int) (contourData[c][r] * 255));
			}
		}
		dataImage.updatePixels();
	}

	public void drawData2D() {
		for (int c = 0; c < contourDataXs.length; c++) {
			for (int r = 0; r < contourDataYs.length; r++) {
				int x = (int) (c * resolution);
				int y = (int) (r * resolution);

				parent.noStroke();
				parent.fill((int) (contourData[c][r] * 255));
				parent.rect(x, y, resolution, resolution);
			}
		}

		// parent.image(dataImage, 0, 0);
	}

	public void drawData3D() {
		// for (int c = 0; c < contourDataXs.length; c++) {
		// for (int r = 0; r < contourDataYs.length; r++) {
		// int x = (int) (c * resolution);
		// int y = (int) (r * resolution);
		//
		// parent.noStroke();
		// parent.fill((int) (contourData[c][r] * 255));
		// parent.rect(x, y, resolution, resolution);
		// }
		// }
	}

	public void drawContours2D() {
		parent.colorMode(PConstants.HSB, 360, 100, 100);
		for (int i = 0; i < contours.size(); i++) {
			Contour l = contours.get(i);

			parent.noFill();
			parent.stroke((l.az / 1f * 360), 100, 100);
			parent.line(l.ax, l.ay, l.bx, l.by);
		}
		parent.colorMode(PConstants.RGB, 255, 255, 255);
	}

	public void drawContours2D(PGraphics _pg) {
		parent.colorMode(PConstants.HSB, 360, 100, 100);
		for (int i = 0; i < contours.size(); i++) {
			Contour l = contours.get(i);

			_pg.noFill();
			_pg.stroke((l.az / 1f * 360), 100, 100);
			_pg.line(l.ax, l.ay, l.az * contourElevation, l.bx, l.by, l.bz
					* contourElevation);
		}
		_pg.colorMode(PConstants.RGB, 255, 255, 255);
	}

	public void drawContours3D() {
		drawContours3D(parent.g);
	}

	public void drawContours3D(PGraphics _pg) {
		int h = 300;
		parent.colorMode(PConstants.HSB, h, 250, 200);
		// parent.beginShape();
		for (int i = 0; i < contours.size(); i++) {
			Contour l = contours.get(i);

			parent.noFill();
			parent.stroke(360 - h * l.az / 1f, 250, 200);
			parent.line(l.ax, l.ay, l.az * contourElevation, l.bx, l.by, l.bz
					* contourElevation);
			// parent.vertex(l.ax, l.ay, l.az * contourElevation);
			// parent.vertex(l.bx, l.by, l.bz * contourElevation);
		}
		// parent.endShape();0
		parent.colorMode(parent.RGB, 255, 255, 255);
	}

	public void drawContours3D(int[] _colors) {
		drawContours3D(parent.g, _colors);
	}

	public void drawContours3D(PGraphics _pg, int[] _colors) {
		int i = 0;

		float z1 = contours.get(0).az;
		float zMin = z1, zMax = z1;

		for (Contour l : contours) {
			zMin = PApplet.min(zMin, l.az);
			zMax = PApplet.max(zMax, l.az);
		}

		for (int c : _colors) {
			_pg.beginShape(PApplet.LINE);
			for (Contour l : contours) {
				int j = (int) PApplet.map(l.az, zMin, zMax, 0,
						_colors.length - 1);

				if (j == i) {
					_pg.noFill();
					_pg.strokeWeight(1);
					_pg.stroke(c);
					// _pg.line(l.ax, l.ay, l.az * contourElevation, l.bx,
					// l.by, l.bz * contourElevation);
					_pg.vertex(l.ax, l.ay, l.az * contourElevation);
					_pg.vertex(l.bx, l.by, l.bz * contourElevation);
				}
			}
			_pg.endShape();

			i++;
		}
	}

	public void setResolution(int _resolution) {
		resolution = _resolution;

		columns = (int) Math.ceil((float) width / resolution) - 1;
		rows = (int) Math.ceil((float) height / resolution) - 1;

		if (contourData != null)
			computeFromImage();// computeContours();
	}

	public void setLevels(int _levels) {
		contourLevels = _levels;

		if (contourData != null) {
			computeContourDataZs();
			compute();
		}
	}

	public void setElevation(int _elevation) {
		contourElevation = _elevation;
		if (contourData != null)
			compute();
	}

	private int[] getImageAverageColorRGBAt(PImage _image, int _x, int _y,
			int _width, int _height) {
		int rSum = 0, bSum = 0, gSum = 0;
		int pCount = (int) (_width * _height);

		for (int i = _x; i < _x + _width; i++) {
			for (int j = _y; j < _y + _height; j++) {
				int p = _image.pixels[i + j * _image.width];

				rSum += (p >> 16) & 0x000000FF;
				gSum += (p >> 8) & 0x000000FF;
				bSum += (p) & 0x000000FF;
			}
		}

		return new int[] { rSum / pCount, gSum / pCount, bSum / pCount };
	}

	private float getImageAverageBrightnessAt(PImage _image, int _x, int _y,
			int _width, int _height) {
		int[] c = getImageAverageColorRGBAt(_image, _x, _y, _width, _height);
		return rgbToBrightness(c[0], c[1], c[2]);
	}

	private float rgbToBrightness(float _r, float _g, float _b) {
		return 0.3f * _r + 0.59f * _g + 0.11f * _b;
	}

	public Iterator<Contour> iterator() {
		return contours.iterator();
	}
}