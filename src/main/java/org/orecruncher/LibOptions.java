/*
 * Licensed under the MIT License (MIT).
 *
 * Copyright (c) OreCruncher
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.orecruncher;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = LibInfo.MOD_ID)
@Config(modid = LibInfo.MOD_ID, type = Type.INSTANCE, name = LibInfo.MOD_ID, category = "")
public class LibOptions {

	private static final String PREFIX = "config." + LibInfo.MOD_ID;

	@Name("Logging")
	@Comment({ "Options to control logging" })
	@LangKey(Logging.PREFIX)
	public static Logging logging = new Logging();

	public static class Logging {

		private static final String PREFIX = LibOptions.PREFIX + ".logging";

		@LangKey(PREFIX + ".enableLogging")
		@Comment({ "Enables debug logging output for diagnostics" })
		public boolean enableLogging = false;

		@LangKey(PREFIX + ".enableVersionCheck")
		@Comment({ "Enables display of chat messages related to newer versions", "of the mod being available." })
		public boolean enableVersionCheck = true;
	}

	@SubscribeEvent
	public static void onConfigChangedEvent(final OnConfigChangedEvent event) {
		if (event.getModID().equals(LibInfo.MOD_ID)) {
			ConfigManager.sync(LibInfo.MOD_ID, Type.INSTANCE);
			LibBase.log().setDebug(LibOptions.logging.enableLogging);
		}
	}
}
