package org.usfirst.frc5933.StevenDave.mapping;

import java.util.ArrayList;

public class FieldOfPlay {
	private static final String ARENA_NAME = "Stronghold";
	private static final Coordinate ARENA_ORIGIN = new Coordinate(0, 0, 0);
	private static final double ARENA_WIDTH_INCHES = (26 * 12) + 7;
	private static final double ARENA_LENGTH_INCHES = (54 * 12) + 1;

	// We will layout the field on a Cartesian graph where the Center is right in the middle
	// and the towers are along the y axis.
	private Rectangle blueHalf_ = new Rectangle("Blue Half Court", new Coordinate(0, (ARENA_LENGTH_INCHES / 2), 0));
	private Rectangle redHalf_ = new Rectangle("Red Half Court", new Coordinate(0, -(ARENA_LENGTH_INCHES / 2), 0));

	private ArrayList landmarks_ = new ArrayList();
	private ArrayList startingPositions_ = new ArrayList();
	private ArrayList defensePositions_ = new ArrayList();

	public FieldOfPlay() {
		// Right and left games pieces are from the point of view that someone is standing 
		// at the center of the field looking down the positive y axis. 
		landmarks_.add(new Landmark("RedTowerLeftUpperWindow", new Coordinate(6, 7, 8))); // FIXME
		landmarks_.add(new Landmark("RedTowerCenterUpperWindow", new Coordinate(6, 7, 8))); // FIXME
		landmarks_.add(new Landmark("RedTowerRightUpperWindow", new Coordinate(6, 7, 8))); // FIXME

		landmarks_.add(new Landmark("BlueTowerLeftUpperWindow", new Coordinate(6, 7, 8))); // FIXME
		landmarks_.add(new Landmark("BlueTowerCenterUpperWindow", new Coordinate(6, 7, 8))); // FIXME
		landmarks_.add(new Landmark("BlueTowerRightUpperWindow", new Coordinate(6, 7, 8))); // FIXME

		// Counting from left to right from the center of the field
		defensePositions_.add(new Rectangle("RedDefensePosition1", new Coordinate(6, 7, 8))); // FIXME
		defensePositions_.add(new Rectangle("RedDefensePosition2", new Coordinate(6, 7, 8))); // FIXME
		defensePositions_.add(new Rectangle("RedDefensePosition3", new Coordinate(6, 7, 8))); // FIXME
		defensePositions_.add(new Rectangle("RedDefensePosition4", new Coordinate(6, 7, 8))); // FIXME
		defensePositions_.add(new Rectangle("RedDefensePosition5", new Coordinate(6, 7, 8))); // FIXME

		defensePositions_.add(new Rectangle("BlueDefensePosition1", new Coordinate(6, 7, 8))); // FIXME
		defensePositions_.add(new Rectangle("BlueDefensePosition2", new Coordinate(6, 7, 8))); // FIXME
		defensePositions_.add(new Rectangle("BlueDefensePosition3", new Coordinate(6, 7, 8))); // FIXME
		defensePositions_.add(new Rectangle("BlueDefensePosition4", new Coordinate(6, 7, 8))); // FIXME
		defensePositions_.add(new Rectangle("BlueDefensePosition5", new Coordinate(6, 7, 8))); // FIXME
	}
}
