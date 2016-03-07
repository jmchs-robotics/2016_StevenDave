package org.usfirst.frc5933.StevenDave.mapping;

public class Shape {
	protected String name_;
	protected Coordinate origin_;

	Shape() {
		name_ = "unknown";
	}

	Shape(Coordinate origin) {
		name_ = "unknown";
		origin_ = origin;
	}

	Shape(String name) {
		name_ = name;
	}

	Shape(String name, Coordinate origin) {
		name_ = name;
		origin_ = origin;
	}

	public final String getName() {
		return name_;
	}

	public final Coordinate getOrigin() {
		return origin_;
	}
}
