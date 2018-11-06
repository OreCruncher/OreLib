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

package org.orecruncher.lib;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;

public final class JsonUtils {

	private JsonUtils() {

	}

	public static class JsonValidationFailed extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public JsonValidationFailed(@Nonnull final String cause, @Nonnull final Throwable t) {
			super(cause, t);
		}

	}

	@Nullable
	public static <T> T load(@Nonnull final InputStream stream, @Nonnull final Class<T> clazz) throws Exception {
		try (final InputStreamReader reader = new InputStreamReader(stream)) {
			return new Gson().fromJson(reader, clazz);
		} catch (final Throwable t) {
			LibLog.log().error("Unable to process Json from stream", t);
			;
		}
		return clazz.newInstance();
	}

}
