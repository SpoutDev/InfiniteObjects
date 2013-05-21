/*
 * This file is part of InfObjects.
 *
 * Copyright (c) 2012, SpoutDev <http://www.spout.org/>
 * InfObjects is licensed under the SpoutDev License Version 1.
 *
 * InfObjects is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * InfObjects is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.infobjects.shape;

import java.util.Map;
import java.util.Random;

import org.spout.infobjects.IWGO;
import org.spout.infobjects.exception.ShapeLoadingException;
import org.spout.infobjects.instruction.Instruction;
import org.spout.infobjects.material.MaterialSetter;
import org.spout.infobjects.util.RandomOwner;
import org.spout.infobjects.value.Value;

/**
 * A shape to draw a sphere defined by its radiuses on x, y and z.
 */
public class Sphere extends Shape {
	private Value radiusX;
	private Value radiusY;
	private Value radiusZ;

	static {
		register("sphere", Sphere.class);
	}

	/**
	 * Constructs a new sphere shape from the parent instruction.
	 *
	 * @param iwgo The parent instruction
	 */
	public Sphere(Instruction instruction) {
		super(instruction);
	}

	/**
	 * Sets the size of the sphere from the values in the map. The size values represent the
	 * radiuses for each axis. The expected values for the map are "radiusX", "radiusY" and
	 * "radiusZ". The x size If any of these are missing, an exception is thrown.
	 *
	 * @param sizes The size as a string, value map
	 * @throws ShapeLoadingException If any of the "radiusX", "radiusY" or "radiusZ" keys are
	 * missing
	 */
	@Override
	public void setSize(Map<String, Value> sizes) throws ShapeLoadingException {
		if (!sizes.containsKey("radiusX")) {
			throw new ShapeLoadingException("radiusX size is missing");
		}
		if (!sizes.containsKey("radiusY")) {
			throw new ShapeLoadingException("radiusY size is missing");
		}
		if (!sizes.containsKey("radiusZ")) {
			throw new ShapeLoadingException("radiusZ size is missing");
		}
		radiusX = sizes.get("radiusX");
		radiusY = sizes.get("radiusY");
		radiusZ = sizes.get("radiusZ");
	}

	/**
	 * Draws the sphere. The position values indicate the center of the sphere. The size parameters
	 * define the radius for each axis.
	 */
	@Override
	public void draw() {
		final int px = (int) getX().getValue();
		final int py = (int) getY().getValue();
		final int pz = (int) getZ().getValue();
		final double rx = radiusX.getValue() + 0.5;
		final double ry = radiusY.getValue() + 0.5;
		final double rz = radiusZ.getValue() + 0.5;
		final double invRadiusX = 1 / rx;
		final double invRadiusY = 1 / ry;
		final double invRadiusZ = 1 / rz;
		final int ceilRadiusX = (int) Math.ceil(rx);
		final int ceilRadiusY = (int) Math.ceil(ry);
		final int ceilRadiusZ = (int) Math.ceil(rz);
		final IWGO iwgo = getInstruction().getIWGO();
		final MaterialSetter setter = getMaterialSetter();
		double nextXn = 0;
		forX:
		for (int xx = 0; xx <= ceilRadiusX; xx++) {
			final double xn = nextXn;
			nextXn = (xx + 1) * invRadiusX;
			double nextYn = 0;
			forY:
			for (int yy = 0; yy <= ceilRadiusY; yy++) {
				final double yn = nextYn;
				nextYn = (yy + 1) * invRadiusY;
				double nextZn = 0;
				forZ:
				for (int zz = 0; zz <= ceilRadiusZ; zz++) {
					final double zn = nextZn;
					nextZn = (zz + 1) * invRadiusZ;
					if (lengthSquared(xn, yn, zn) > 1) {
						if (zz == 0) {
							if (yy == 0) {
								break forX;
							}
							break forY;
						}
						break forZ;
					}
					final boolean outer = lengthSquared(nextXn, yn, zn) > 1
							|| lengthSquared(xn, nextYn, zn) > 1
							|| lengthSquared(xn, yn, nextZn) > 1;
					setter.setMaterial(iwgo.transform(px + xx, py + yy, pz + zz), outer);
					setter.setMaterial(iwgo.transform(px - xx, py + yy, pz + zz), outer);
					setter.setMaterial(iwgo.transform(px + xx, py - yy, pz + zz), outer);
					setter.setMaterial(iwgo.transform(px + xx, py + yy, pz - zz), outer);
					setter.setMaterial(iwgo.transform(px - xx, py - yy, pz + zz), outer);
					setter.setMaterial(iwgo.transform(px + xx, py - yy, pz - zz), outer);
					setter.setMaterial(iwgo.transform(px - xx, py + yy, pz - zz), outer);
					setter.setMaterial(iwgo.transform(px - xx, py - yy, pz - zz), outer);
				}
			}
		}
	}

	private static double lengthSquared(double x, double y, double z) {
		return x * x + y * y + z * z;
	}

	/**
	 * Randomizes the size values of the line by recalculating them. Then calls the super method.
	 */
	@Override
	public void randomize() {
		super.randomize();
		radiusX.calculate();
		radiusY.calculate();
		radiusZ.calculate();
	}

	/**
	 * Sets the random for each size value if they implement
	 * {@link org.spout.infobjects.util.RandomOwner}. Calls the super method.
	 *
	 * @param random The random to use
	 */
	@Override
	public void setRandom(Random random) {
		super.setRandom(random);
		if (radiusX instanceof RandomOwner) {
			((RandomOwner) radiusX).setRandom(random);
		}
		if (radiusY instanceof RandomOwner) {
			((RandomOwner) radiusY).setRandom(random);
		}
		if (radiusZ instanceof RandomOwner) {
			((RandomOwner) radiusZ).setRandom(random);
		}
	}

	/**
	 * Returns the string representation of the shape.
	 *
	 * @return The string form of the shape
	 */
	@Override
	public String toString() {
		return "Sphere{x=" + getX() + ", y=" + getY() + ", z=" + getZ() + ", setter=" + getMaterialSetter()
				+ ", radiusX=" + radiusX + ", radiusY=" + radiusY + ", radiusZ=" + radiusZ + '}';
	}
}
