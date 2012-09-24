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
package org.spout.infobjects.material;

import java.util.Random;

import org.spout.api.material.BlockMaterial;
import org.spout.api.util.config.ConfigurationNode;

import org.spout.infobjects.util.IFOUtils;

public class RandomUniformPicker extends MaterialPicker {
	private Random random = new Random();
	private BlockMaterial material;
	private int odd;

	public RandomUniformPicker(String name) {
		super(name);
	}

	@Override
	public void load(ConfigurationNode config) {
		material = IFOUtils.getBlockMaterial(config.getNode("material").getString());
		odd = config.getNode("odd").getInt();
	}

	@Override
	public BlockMaterial pickMaterial(boolean outer) {
		return random.nextInt(100) < odd ? material : BlockMaterial.AIR;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	@Override
	public String toString() {
		return "Material: " + material.getDisplayName() + " with odd: " + odd;
	}
}