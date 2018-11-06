/*
 * This file is part of OreLib, licensed under the MIT License (MIT).
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

import java.util.Arrays;
import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.orecruncher.lib.ForgeUtils;
import org.orecruncher.lib.Localization;
import org.orecruncher.lib.logging.ModLog;
import org.orecruncher.proxy.Proxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MOD_ID, useMetadata = true, dependencies = ModInfo.DEPENDENCIES, version = ModInfo.VERSION, acceptedMinecraftVersions = ModInfo.MINECRAFT_VERSIONS, updateJSON = ModInfo.UPDATE_URL, certificateFingerprint = ModInfo.FINGERPRINT)
public class ModBase {
	public static final String MOD_ID = "orelib";
	public static final String API_ID = MOD_ID + "API";
	public static final String RESOURCE_ID = "orelib";
	public static final String MOD_NAME = "OreLib";
	public static final String VERSION = "@VERSION@";
	public static final String MINECRAFT_VERSIONS = "[1.12.2,)";
	public static final String DEPENDENCIES = "required-after:forge@[14.23.2.2635,);";
	public static final String UPDATE_URL = "https://raw.githubusercontent.com/OreCruncher/OreLib/master/version.json";
	public static final String FINGERPRINT = "7a2128d395ad96ceb9d9030fbd41d035b435753a";

	@Instance(MOD_ID)
	protected static ModBase instance;

	@Nonnull
	public static ModBase instance() {
		return instance;
	}

	@SidedProxy(clientSide = "org.orecruncher.proxy.ProxyClient", serverSide = "org.orecruncher.proxy.Proxy")
	protected static Proxy proxy;
	protected static ModLog logger = ModLog.NULL_LOGGER;
	protected static boolean devMode;

	@Nonnull
	public static Proxy proxy() {
		return proxy;
	}

	@Nonnull
	public static ModLog log() {
		return logger;
	}

	public ModBase() {
		logger = ModLog.setLogger(ModBase.MOD_ID, LogManager.getLogger(MOD_ID));
	}

	@EventHandler
	public void preInit(@Nonnull final FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(@Nonnull final FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(@Nonnull final FMLPostInitializationEvent event) {
		proxy.postInit(event);

		// Patch up metadata
		if (!proxy.isRunningAsServer()) {
			final ModMetadata data = ForgeUtils.getModMetadata(ModBase.MOD_ID);
			if (data != null) {
				data.name = Localization.format("orelib.metadata.Name");
				data.credits = Localization.format("orelib.metadata.Credits");
				data.description = Localization.format("orelib.metadata.Description");
				data.authorList = Arrays
						.asList(StringUtils.split(Localization.format("orelib.metadata.Authors"), ','));
			}
		}
	}

	@EventHandler
	public void loadCompleted(@Nonnull final FMLLoadCompleteEvent event) {
		proxy.loadCompleted(event);
	}

	@EventHandler
	public void onFingerprintViolation(@Nonnull final FMLFingerprintViolationEvent event) {
		log().warn("Invalid fingerprint detected!");
	}
}
