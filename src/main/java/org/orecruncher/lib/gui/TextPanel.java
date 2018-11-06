/* This file is part of Dynamic Surroundings, licensed under the MIT License (MIT).
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

package org.orecruncher.lib.gui;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextPanel extends Panel<TextPanel> {

	private final FontRenderer font = Minecraft.getMinecraft().fontRenderer;

	private List<String> text = ImmutableList.of();

	public TextPanel() {
		super();
	}

	@Nonnull
	public TextPanel setText(@Nonnull final List<String> text) {
		this.text = text;
		setHeight((text.size() + 1) * this.font.FONT_HEIGHT);

		int w = 0;
		for (final String s : text)
			w = Math.max(this.font.getStringWidth(s), w);
		w += this.font.FONT_HEIGHT;
		setWidth(w);
		return this;
	}

	@Nonnull
	public TextPanel resetText() {
		this.text = ImmutableList.of();
		return this;
	}

	public boolean hasText() {
		return !this.text.isEmpty();
	}

	@Override
	public void render(final int locX, final int locY, @Nonnull final Reference ref) {

		if (this.text.isEmpty())
			return;

		final int posY;

		switch (ref) {
		case CENTER:
			posY = locY - getCenterY();
			break;
		case TOP_CENTER:
			posY = locY;
			break;
		default:
			posY = locY;
		}

		super.render(locX, locY, ref);

		GlStateManager.color(1F, 1F, 1F, this.alpha);
		GlStateManager.enableBlend();

		final int textRGB = getForegroundColor().rgbWithAlpha(this.alpha);

		final int drawX;

		if (ref == Reference.CENTER || ref == Reference.TOP_CENTER)
			drawX = locX;
		else
			drawX = locX + getCenterX();

		int drawY = posY + (this.font.FONT_HEIGHT + 1) / 2;

		for (int i = 0; i < this.text.size(); i++) {
			GuiUtils.drawCenteredString(this.font, this.text.get(i), drawX, drawY, textRGB);
			drawY += this.font.FONT_HEIGHT;
		}

		GlStateManager.color(1F, 1F, 1F, 1F);

	}

}
