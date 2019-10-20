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
package org.orecruncher.lib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.orecruncher.LibBase;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import net.minecraftforge.fml.relauncher.CoreModManager;

public class ReflectedMethod<R> {
	
	protected static boolean isDeobfuscatedEnvironment = false;

	static {
		try {
			final Field f = CoreModManager.class.getDeclaredField("deobfuscatedEnvironment");
			f.setAccessible(true);
			isDeobfuscatedEnvironment = f.getBoolean(null);
		} catch (@Nonnull final Throwable t) {
			LibBase.log().error("Unable to determine obsfucated environment", t);
		}
	}

	protected final String className;
	protected final String methodName;
	protected final Method method;

	public ReflectedMethod(@Nonnull final String className, @Nonnull final String methodName,
			@Nullable final String obfMethodName, Class<?>... parameters) {
		this.className = className;
		this.methodName = methodName;
		this.method = resolve(className, methodName, obfMethodName, parameters);
	}

	public ReflectedMethod(@Nonnull final Class<?> clazz, @Nonnull final String methodName,
			@Nullable final String obfMethodName, Class<?>... parameters) {
		Preconditions.checkNotNull(clazz);
		Preconditions.checkArgument(StringUtils.isNotEmpty(methodName), "Field name cannot be empty");
		this.className = clazz.getName();
		this.methodName = methodName;
		this.method = resolve(clazz, methodName, obfMethodName, parameters);
	}

	@Nullable
	private Method resolve(@Nonnull final String className, @Nonnull final String methodName,
			@Nullable final String obfMethodName, Class<?>... parameters) {
		try {
			return resolve(Class.forName(className), methodName, obfMethodName, parameters);
		} catch (@Nonnull final Throwable t) {
			// Left intentionally blank
		}
		return null;
	}

	@Nullable
	private Method resolve(@Nonnull final Class<?> clazz, @Nonnull final String methodName,
			@Nullable final String obfMethodName, Class<?>... parameters) {
		final String nameToFind = isDeobfuscatedEnvironment ? methodName : MoreObjects.firstNonNull(obfMethodName, methodName);
		try {
			final Method f = clazz.getDeclaredMethod(nameToFind, parameters);
			f.setAccessible(true);
			return f;
		} catch (final Throwable t) {
			final String msg = String.format("Unable to locate field [%s::%s]", clazz.getName(), nameToFind);
			LibBase.log().warn(msg);
		}

		return null;
	}

	public boolean isAvailable() {
		return this.method != null;
	}
	
	@SuppressWarnings("unchecked")
	public R invoke(Object ref, Object... parms) {
		try {
			return (R) this.method.invoke(ref, parms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void check() {
		if (!isAvailable()) {
			final String msg = String.format("Uninitialized method [%s::%s]", this.className, this.methodName);
			throw new IllegalStateException(msg);
		}
	}

	protected void report(@Nonnull final Throwable t) {
		final String msg = String.format("Unable to access field [%s::%s]", this.className, this.methodName);
		LibBase.log().error(msg, t);
	}

	@Nullable
	protected static Class<?> resolveClass(@Nonnull final String className) {
		try {
			return Class.forName(className);
		} catch (@Nonnull final Throwable t) {
			;
		}
		return null;
	}
}
