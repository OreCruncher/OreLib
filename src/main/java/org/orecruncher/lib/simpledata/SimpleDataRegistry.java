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

package org.orecruncher.lib.simpledata;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Simple data registry that maps POJO class references to data instances of
 * that class. This is that anonymous data can be centralized and serialization
 * handled.
 */
public abstract class SimpleDataRegistry implements ISimpleDataRegistry {

	private Map<Class<? extends ISimpleData>, ISimpleData> dataMap = null;
	private boolean isDirty;

	@Override
	@Nullable
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = null;
		if (this.dataMap != null) {
			nbt = new NBTTagCompound();
			if (this.dataMap != null && !this.dataMap.isEmpty()) {
				for (final Entry<Class<? extends ISimpleData>, ISimpleData> e : this.dataMap.entrySet()) {
					final String key = e.getKey().getName();
					final NBTTagCompound value = e.getValue().serializeNBT();
					nbt.setTag(key, value);
				}
			}
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(@Nonnull final NBTTagCompound nbt) {
		if (nbt.getSize() == 0)
			return;
		for (final String className : nbt.getKeySet()) {
			try {
				@SuppressWarnings("unchecked")
				final Class<? extends ISimpleData> clazz = (Class<? extends ISimpleData>) Class.forName(className);
				final ISimpleData data = getData(clazz);
				data.deserializeNBT(nbt.getCompoundTag(className));
			} catch (@Nonnull final Throwable t) {
				throw new IllegalStateException(t);
			}
		}
	}

	/**
	 * Obtains a reference to the requested information. If the corresponding
	 * reference does not exist it is created.
	 */
	@Override
	@Nonnull
	public ISimpleData getData(@Nonnull final Class<? extends ISimpleData> clazz) {
		if (this.dataMap == null)
			this.dataMap = new IdentityHashMap<>();
		ISimpleData result = this.dataMap.get(clazz);
		if (result == null) {
			try {
				result = clazz.newInstance();
			} catch (@Nonnull final Throwable t) {
				throw new IllegalStateException(t);
			}
		}
		return result;
	}

	@Override
	public boolean isDirty() {
		return this.isDirty;
	}

	@Override
	public void setDirty() {
		this.isDirty = true;
	}

	@Override
	public void clearDirty() {
		this.isDirty = false;
	}
}
