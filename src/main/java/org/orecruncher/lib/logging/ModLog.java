/*
 * This file is part of Dynamic Surroundings, licensed under the MIT License (MIT).
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

package org.orecruncher.lib.logging;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

public class ModLog {

	private static final Map<String, ModLog> loggers = Maps.newHashMap();

	private final Logger logger;
	private boolean DEBUGGING;
	private int traceMask;

	private ModLog(@Nonnull final Logger logger) {
		this.logger = logger;
		this.DEBUGGING = false;
		this.traceMask = 0;
	}

	@Nonnull
	private String[] formatLines(@Nonnull final String format, @Nullable final Object... parms) {
		return StringUtils.split(String.format(format, parms), '\n');
	}

	public static ModLog getLogger(@Nonnull final String id) {
		final ModLog result = loggers.get(id);
		return result != null ? result : NULL_LOGGER;
	}

	public static ModLog setLogger(@Nonnull final String id, @Nonnull final Logger log) {
		final ModLog result = log == null ? NULL_LOGGER : new ModLog(log);
		loggers.put(id, result);
		return result;
	}

	public ModLog setDebug(final boolean flag) {
		this.DEBUGGING = flag;
		return this;
	}

	public ModLog setTraceMask(final int mask) {
		this.traceMask = mask;
		return this;
	}

	public boolean testTrace(final int mask) {
		return (this.traceMask & mask) != 0;
	}

	public boolean isDebugging() {
		return this.DEBUGGING;
	}

	public void info(@Nonnull final String msg, @Nullable final Object... parms) {
		for (final String s : formatLines(msg, parms))
			this.logger.info(s);
	}

	public void warn(@Nonnull final String msg, @Nullable final Object... parms) {
		for (final String s : formatLines(msg, parms))
			this.logger.warn(s);
	}

	public void debug(@Nonnull final String msg, @Nullable final Object... parms) {
		if (this.DEBUGGING)
			for (final String s : formatLines(msg, parms))
				this.logger.info(s);
	}

	public void debug(final int mask, @Nonnull final String msg, @Nullable final Object... parms) {
		if (testTrace(mask))
			this.debug(msg, parms);
	}

	public void error(@Nonnull final String msg, @Nonnull final Throwable e) {
		for (final String s : formatLines(msg))
			this.logger.error(s);
		e.printStackTrace();
	}

	public void catching(@Nonnull final Throwable t) {
		this.logger.catching(t);
		t.printStackTrace();
	}

	public static final ModLog NULL_LOGGER = new ModLog(null) {

		@Override
		public ModLog setDebug(final boolean flag) {
			return this;
		}

		@Override
		public ModLog setTraceMask(final int mask) {
			return this;
		}

		@Override
		public boolean isDebugging() {
			return false;
		}

		@Override
		public void info(@Nonnull final String msg, @Nullable final Object... parms) {
		}

		@Override
		public void warn(@Nonnull final String msg, @Nullable final Object... parms) {
		}

		@Override
		public void debug(@Nonnull final String msg, @Nullable final Object... parms) {
		}

		@Override
		public void debug(final int mask, @Nonnull final String msg, @Nullable final Object... parms) {
		}

		@Override
		public void error(@Nonnull final String msg, @Nonnull final Throwable e) {
		}

		@Override
		public void catching(@Nonnull final Throwable t) {
		}
	};
}
