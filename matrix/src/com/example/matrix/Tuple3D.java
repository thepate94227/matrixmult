package com.example.matrix;

public class Tuple3D {
	private float x;
	private float y;
	private float z;

	private float length = 0f;

	private static final float SENSITIVITY = 0.10f;

	public Tuple3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.length = getLength();
	}

	public float getLength() {
		float result = 0f;
		result += Math.pow(x, 2);
		result += Math.pow(y, 2);
		result += Math.pow(z, 2);
		result = (float) Math.sqrt(result);
		return result;
	}

	public void normalize() {
		float length = getLength();
		this.x = x / length;
		this.y = y / length;
		this.z = z / length;
		this.length = 1;
	}

	protected static boolean isProductEqual(float a, float b) {
		if (a >= (1 - SENSITIVITY) * b && a <= (1 + SENSITIVITY) * b)
			return true;
		return false;
	}

	public boolean isLinearDependant(Tuple3D other) {
		boolean result = false;

		float scalar = this.x * other.getX();
		scalar += this.y * other.getY();
		scalar += this.z * other.getZ();
		
		if (isProductEqual(scalar, getLength()*other.getLength()))
			result = true;
		/*
		 * float ratio = this.x/other.x; boolean y_dependant =
		 * isRatioEqual(this.y/other.y, ratio); boolean z_dependant =
		 * isRatioEqual(this.z/other.z, ratio);
		 * 
		 * if (y_dependant && z_dependant) result = true;
		 */
		return result;
	}

	public double get_X_Angle() {
		return Math.asin(x / length);
	}

	public double get_Y_Angle() {
		return Math.asin(y / length);
	}

	public double get_Z_Angle() {
		return Math.asin(z / length);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
}
