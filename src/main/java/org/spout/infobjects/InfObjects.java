/*
 * This file is part of InfiniteObjects.
 *
 * Copyright (c) 2012 Spout LLC <http://www.spout.org/>
 * InfiniteObjects is licensed under the Spout License Version 1.
 *
 * InfiniteObjects is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * InfiniteObjects is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license,
 * including the MIT license.
 */
package org.spout.infobjects;

import org.spout.api.command.CommandRegistrationsFactory;
import org.spout.api.command.annotated.AnnotatedCommandRegistrationFactory;
import org.spout.api.command.annotated.SimpleAnnotatedCommandExecutorFactory;
import org.spout.api.command.annotated.SimpleInjector;
import org.spout.api.plugin.CommonPlugin;

/**
 * The InfiniteObjects plugin. Keeps a static {@link IWGOManager} for the folder
 * "plugins/InfObjects/IWGOs".
 */
public class InfObjects extends CommonPlugin {
	private static final IWGOManager MANAGER = new IWGOManager("plugins/InfObjects/IWGOs", true);

	@Override
	public void onEnable() {
		try {
			Class.forName("org.spout.infobjects.function.RandomFunction");
		} catch (ClassNotFoundException ex) {
			getLogger().warning("Class \"org.spout.infobjects.function.RandomFunction\""
					+ " couldn't be found. IWGO loading may fail");
		}
		final CommandRegistrationsFactory<Class<?>> commandRegFactory =
				new AnnotatedCommandRegistrationFactory(getEngine(), new SimpleInjector(), new SimpleAnnotatedCommandExecutorFactory());
		getEngine().getRootCommand().addSubCommands(this, IWGOCommands.class, commandRegFactory);
		MANAGER.loadIWGOs();
		getLogger().info("Loaded " + MANAGER.getIWGOMap().size() + " IWGO(s)");
		getLogger().info("v" + getDescription().getVersion() + " enabled");
	}

	@Override
	public void onDisable() {
		MANAGER.unloadIWGOs();
		getLogger().info("disabled");
	}

	@Override
	public void onReload() {
		MANAGER.reloadIWGOs();
	}

	/**
	 * Gets the static iWGO manager for the folder "plugins/InfObjects/IWGOs" kept by the plugin.
	 *
	 * @return The iWGO manager.
	 */
	public static IWGOManager getIWGOManager() {
		return MANAGER;
	}
}
