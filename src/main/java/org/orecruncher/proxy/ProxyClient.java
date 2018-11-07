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

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;
import org.orecruncher.ModInfo;
import org.orecruncher.lib.ForgeUtils;
import org.orecruncher.lib.Localization;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ProxyClient extends Proxy {

	@Override
	public boolean isRunningAsServer() {
		return false;
	}

	@Override
	public Side effectiveSide() {
		return FMLCommonHandler.instance().getEffectiveSide();
	}

	@Override
	public void postInit(@Nonnull final FMLPostInitializationEvent event) {
		// Patch up metadata
		final ModMetadata data = ForgeUtils.getModMetadata(ModInfo.MOD_ID);
		if (data != null) {
			data.name = Localization.format("orelib.metadata.Name");
			data.credits = Localization.format("orelib.metadata.Credits");
			data.description = Localization.format("orelib.metadata.Description");
			data.authorList = Arrays.asList(StringUtils.split(Localization.format("orelib.metadata.Authors"), ','));
		}
	}

}
