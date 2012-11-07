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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.spout.api.material.BlockMaterial;
import org.spout.api.util.Named;
import org.spout.api.util.config.ConfigurationNode;

public abstract class MaterialPicker implements Named {
	private static final Map<String, Constructor<? extends MaterialPicker>> PICKERS =
			new HashMap<String, Constructor<? extends MaterialPicker>>();
	private final String name;

	static {
		try {
			register("simple", SimplePicker.class);
			register("random-simple", RandomSimplePicker.class);
			register("inner-outer", InnerOuterPicker.class);
			register("random-inner-outer", RandomInnerOuterPicker.class);
		} catch (Exception ex) {
			System.err.println("Failed to register the material pickers");
			ex.printStackTrace();
		}
	}

	public MaterialPicker(String name) {
		this.name = name;
	}

	public abstract void configure(Map<String, String> properties);

	public abstract BlockMaterial pickMaterial(boolean outer);

	@Override
	public String getName() {
		return name;
	}

	public static void register(String type, Class<? extends MaterialPicker> picker)
			throws NoSuchMethodException {
		PICKERS.put(type, picker.getConstructor(String.class));
	}

	public static MaterialPicker newPicker(String type, String name) {
		try {
			return PICKERS.get(type).newInstance(name);
		} catch (Exception ex) {
			return null;
		}
	}
}
