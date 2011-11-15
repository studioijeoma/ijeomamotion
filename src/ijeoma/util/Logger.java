package ijeoma.util;
import processing.core.*;

public class Logger {

	public boolean isLogging = false;

	public Logger() {
	}

	public void enable() {
		isLogging = true;
	}

	public void disable() {
		isLogging = false;
	}

	public void println(String str) {
		if (isLogging) {
			PApplet.println(str);
		}
	}

	public void println(int i) {
		if (isLogging)
			PApplet.println(i);
	}

	public void println(float i) {
		if (isLogging)
			PApplet.println(i);
	}

	public void println(int[] i) {
		if (isLogging)
			PApplet.println(i);
	}

	public void println(float[] f) {
		if (isLogging)
			PApplet.println(f);
	}

	public void println(PVector v) {
		if (isLogging)
			PApplet.println("[" + v.x + ", " + v.y + ", " + v.z + "]");
	}

	public void print(String str) {
		if (isLogging)
			PApplet.print(str);
	}
}
