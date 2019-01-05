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
package org.orecruncher.lib.compat;

import javax.annotation.Nonnull;

import org.orecruncher.lib.ReflectedField.IntegerField;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public final class EntityUtil {

	//@formatter:off
	private static final IntegerField<Entity> nextStepDistance =
		new IntegerField<>(
			Entity.class,
			"nextStepDistance",
			"field_70150_b",
			0
		);
	//@formatter:on
	
	public static int getNextStepDistance(@Nonnull final Entity entity) {
		return nextStepDistance.get(entity);
	}

	public static void setNextStepDistance(@Nonnull final Entity entity, final int dist) {
		nextStepDistance.set(entity, dist);
	}

	@Nonnull
	public static String getClassName(@Nonnull final Class<? extends Entity> entityClass) {
		final ResourceLocation key = EntityList.getKey(entityClass);
		if (key != null)
			return key.getPath();
		return "EntityHasNoClass";
	}
}
