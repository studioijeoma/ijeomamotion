package ijeoma.motion.utils;


import ijeoma.motion.Motion;

import java.io.File;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class ImageSequence extends Motion {
	float x = 0;
	float y = 0;
	float width = 0;
	float height = 0;
	ArrayList<PImage> images;

	boolean isPlaying = false;
	int index = 0;

	public ImageSequence(String folder, float width, float height) {
		super();

		images = new ArrayList<PImage>();

		this.width = width;
		this.height = height;

		load(folder);
	}

	public ImageSequence(String folder, float width, float height,
			String timeMode) {
		super();
		setTimeMode(timeMode);

		images = new ArrayList<PImage>();

		this.width = width;
		this.height = height;

		load(folder);
	}

	public ImageSequence(String folder, float x, float y, float width,
			float height) {
		super();

		images = new ArrayList<PImage>();

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		load(folder);
	}

	public ImageSequence(String folder, float x, float y, float width,
			float height, String timeMode) {
		super();
		setTimeMode(timeMode);

		images = new ArrayList<PImage>();

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		load(folder);
	}

	public void update() {
		super.update();

		// if (isPlaying)
		updateFrame();
	}

	public void update(float time) {
		super.update(time);

		// if (isPlaying)
		updateFrame();
	}

	private void updateFrame() {
		index = (int) PApplet.map(time, 0, duration, 0, images.size() - 1);
	}

	public void load(String folder) {
		File[] files = getFiles(folder);
		load(files);
	}

	public void load(File[] imageFiles) {
		for (int i = 0; i < imageFiles.length; i++)
			if (!imageFiles[i].getName().equals(".DS_Store"))
				images.add(parent.requestImage(imageFiles[i].getPath()));

		if (timeMode.equals(FRAMES))
			setDuration(images.size());
		else
			setDuration((float) images.size() / parent.frameRate);
	}

	public void draw(PGraphics g) {
		g.image(getFrame(), 0, 0);
	}

	public PImage getFrame() {
		return images.get(index);
	}

	public int getFrameCount() {
		return images.size();
	}

	private File[] getFiles(String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			return file.listFiles();
		} else {
			return null;
		}
	}
}
