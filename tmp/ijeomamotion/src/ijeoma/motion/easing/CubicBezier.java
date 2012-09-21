package ijeoma.motion.easing;

public class CubicBezier {
	// Cubic Bezier tween from b to b+c, influenced by p1 & p2
	// t: current time, b: beginning value, c: total change, d: duration
	// p1, p2: Bezier control point positions
	public static float easeIn(float t, float b, float c, float d, float p1,
			float p2) {
		return ((t /= d) * t * c + 3 * (1 - t)
				* (t * (p2 - b) + (1 - t) * (p1 - b)))
				* t + b;
	}

	public static float easeOut(float t, float b, float c, float d, float p1,
			float p2) {
		return ((t /= d) * t * c + 3 * (1 - t)
				* (t * (p2 - b) + (1 - t) * (p1 - b)))
				* t + b;
	}

	public static float easeBoth(float t, float b, float c, float d, float p1,
			float p2) {
		return ((t /= d) * t * c + 3 * (1 - t)
				* (t * (p2 - b) + (1 - t) * (p1 - b)))
				* t + b;
	}
}
