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

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.orecruncher.lib.VersionChecker;
import org.orecruncher.lib.logging.ModLog;
import org.orecruncher.proxy.Proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.InstanceFactory;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

//@formatter:off
@Mod(
	modid = LibInfo.MOD_ID,
	useMetadata = true,
	dependencies = LibInfo.DEPENDENCIES,
	version = LibInfo.VERSION,
	acceptedMinecraftVersions = LibInfo.MINECRAFT_VERSIONS,
	acceptableRemoteVersions = LibInfo.REMOTE_VERSIONS,
	updateJSON = LibInfo.UPDATE_URL,
	certificateFingerprint = LibInfo.FINGERPRINT
)
//@formatter:on
public class LibBase {
	
	private static final ModLog logger;
	private static final LibBase instance;
	
	static {
		logger = ModLog.setLogger(LibInfo.MOD_ID, LogManager.getLogger(LibInfo.MOD_ID));
		instance = new LibBase();
	}
	
	@InstanceFactory
	@Nonnull
	public static LibBase instance() {
		return instance;
	}

	@SidedProxy(clientSide = "org.orecruncher.proxy.ProxyClient", serverSide = "org.orecruncher.proxy.Proxy")
	protected static Proxy proxy;

	@Nonnull
	public static Proxy proxy() {
		return proxy;
	}

	@Nonnull
	public static ModLog log() {
		return logger;
	}

	public LibBase() {
		MinecraftForge.EVENT_BUS.register(this);
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
	}

	@EventHandler
	public void loadCompleted(@Nonnull final FMLLoadCompleteEvent event) {
		proxy.loadCompleted(event);
	}

	@EventHandler
	public void onFingerprintViolation(@Nonnull final FMLFingerprintViolationEvent event) {
		log().warn("Invalid fingerprint detected!");
	}
	
	@SubscribeEvent
	public void playerLogin(final PlayerLoggedInEvent event) {
		if (LibOptions.logging.enableVersionCheck)
			VersionChecker.doCheck(event, LibInfo.MOD_ID);
	}

}
