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
package org.orecruncher.lib.chunk;

import javax.annotation.Nonnull;

import org.orecruncher.LibBase;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The ClientChunkCache caches chunks within a certain range the the player. The
 * cache is updated once per client tick. If the chunk region changes the cache
 * is updated with the appropriate chunks. The cache will be wiped when a player
 * disconnects or unloads a session.
 *
 * NOTE: Fog scanning uses a range of 20, so if the ranges get adjusted in this
 * cache make sure it is checked with the fog calculators.
 */
@SideOnly(Side.CLIENT)
public final class ClientChunkCache {

	private static IBlockAccessEx INSTANCE;
	private static int range = 34;

	public static IBlockAccessEx instance() {
		if (INSTANCE == null) {
			LibBase.log().warn("Initializing ClientChunkCache on the fly!");
			INSTANCE = new PassThroughChunkCache();
			MinecraftForge.EVENT_BUS.register(ClientChunkCache.class);
		}
		return INSTANCE;
	}

	public static void initialize(final int blockRange, final boolean useDirect) {
		INSTANCE = useDirect ? new DirectChunkCache() : new PassThroughChunkCache();
		range = blockRange;
		MinecraftForge.EVENT_BUS.register(ClientChunkCache.class);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void clientTick(@Nonnull final TickEvent.ClientTickEvent event) {
		if (INSTANCE == null)
			return;
		if (event.side == Side.CLIENT && event.phase == Phase.START) {
			final EntityPlayer player = Minecraft.getMinecraft().player;
			if (player != null) {
				final BlockPos pos = new BlockPos(player.getPosition());
				final BlockPos min = pos.add(-range, -range, -range);
				final BlockPos max = pos.add(range, range, range);
				((IChunkCache) INSTANCE).update(player.getEntityWorld(), min, max);
			} else {
				// If there is no player reference wipe the cache to ensure resources
				// are freed.
				((IChunkCache) ClientChunkCache.INSTANCE).clear();
			}
		}
	}
}
