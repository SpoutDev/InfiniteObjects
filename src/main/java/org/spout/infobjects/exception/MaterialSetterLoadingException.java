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
package org.spout.infobjects.exception;

/**
 * An exception thrown when the loading of a material setter fails.
 */
public class MaterialSetterLoadingException extends LoadingException {
	/**
	 * Constructs a new material setter exception from the message.
	 *
	 * @param string The message of this exception
	 */
	public MaterialSetterLoadingException(String string) {
		super(string);
	}

	/**
	 * Constructs a new material setter exception from the name of the material setter and the
	 * parent exception.
	 *
	 * @param name The name of the material setter
	 * @param thrwbl The exception that caused this one
	 */
	public MaterialSetterLoadingException(String name, Throwable thrwbl) {
		super("Could not load material setter \"" + name + "\"", thrwbl);
	}
}
