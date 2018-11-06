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

package org.orecruncher.proxy;

import javax.annotation.Nonnull;

import org.orecruncher.ModBase;
import org.orecruncher.lib.Localization;
import org.orecruncher.lib.compat.ModEnvironment;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import net.minecraftforge.fml.relauncher.Side;

public class Proxy {

	protected long connectionTime = 0;

	protected void registerLanguage() {
		Localization.initialize(Side.SERVER);
	}

	protected static void register(final Class<?> clazz) {
		ModBase.log().debug("Registering for Forge events: %s", clazz.getName());
		MinecraftForge.EVENT_BUS.register(clazz);
	}

	public long currentSessionDuration() {
		return System.currentTimeMillis() - this.connectionTime;
	}

	public boolean isRunningAsServer() {
		return true;
	}

	public Side effectiveSide() {
		return Side.SERVER;
	}

	public void preInit(@Nonnull final FMLPreInitializationEvent event) {
		registerLanguage();
	}

	public void init(@Nonnull final FMLInitializationEvent event) {
		ModEnvironment.initialize();
	}

	public void postInit(@Nonnull final FMLPostInitializationEvent event) {
	}

	public void loadCompleted(@Nonnull final FMLLoadCompleteEvent event) {
	}

	public void clientConnect(@Nonnull final ClientConnectedToServerEvent event) {
		// NOTHING SHOULD BE HERE - OVERRIDE IN ProxyClient!
	}

	public void clientDisconnect(@Nonnull final ClientDisconnectionFromServerEvent event) {
		// NOTHING SHOULD BE HERE - OVERRIDE IN ProxyClient!
	}

	public void serverAboutToStart(@Nonnull final FMLServerAboutToStartEvent event) {
	}

	public void serverStarting(@Nonnull final FMLServerStartingEvent event) {
	}

	public void serverStopping(@Nonnull final FMLServerStoppingEvent event) {

	}

	public void serverStopped(@Nonnull final FMLServerStoppedEvent event) {
	}

}
