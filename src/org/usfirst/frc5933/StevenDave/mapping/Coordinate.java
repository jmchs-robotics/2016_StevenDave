
package org.usfirst.frc5933.StevenDave.mapping;


public class Coordinate {

	private double x_ = 0;
	private double y_ = 0;
	private double z_ = 0;

	public Coordinate() {
	}

	public Coordinate(double x, double y, double z) {
		x_ = x;
		y_ = y;
		z_ = y;
	}

	public final double getX() {
		return x_;
	}

	public final double getY() {
		return y_;
	}

	public final double getZ() {
		return z_;
	}
}
