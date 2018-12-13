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

package org.orecruncher.lib.expression;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

public final class Dynamic {

	private Dynamic() {

	}

	public abstract static class DynamicNumber extends NumberValue implements IDynamicVariant<NumberValue> {
		private boolean needsSet = true;

		public DynamicNumber(@Nonnull final String name) {
			super(name);
		}

		@Override
		public void reset() {
			this.needsSet = true;
		}

		@Override
		public float asNumber() {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.asNumber();
		}

		@Override
		@Nonnull
		public String asString() {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.asString();
		}

		@Override
		public boolean asBoolean() {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.asBoolean();
		}

		// Operator support in case of strings
		@Override
		@Nonnull
		public Variant add(@Nonnull final Variant term) {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.add(term);
		}
	}

	public abstract static class DynamicString extends StringValue implements IDynamicVariant<StringValue> {
		private boolean needsSet = true;

		public DynamicString(@Nonnull final String name) {
			super(name, StringUtils.EMPTY);
		}

		@Override
		public void reset() {
			this.needsSet = true;
		}

		@Override
		public float asNumber() {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.asNumber();
		}

		@Override
		@Nonnull
		public String asString() {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.asString();
		}

		@Override
		public boolean asBoolean() {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.asBoolean();
		}

		// Operator support in case of strings
		@Override
		@Nonnull
		public Variant add(@Nonnull final Variant term) {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.add(term);
		}
	}

	public abstract static class DynamicBoolean extends BooleanValue implements IDynamicVariant<BooleanValue> {
		private boolean needsSet = true;

		public DynamicBoolean(@Nonnull final String name) {
			super(name);
		}

		@Override
		public void reset() {
			this.needsSet = true;
		}

		@Override
		public float asNumber() {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.asNumber();
		}

		@Override
		@Nonnull
		public String asString() {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.asString();
		}

		@Override
		public boolean asBoolean() {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.asBoolean();
		}

		// Operator support in case of strings
		@Override
		@Nonnull
		public Variant add(@Nonnull final Variant term) {
			if (this.needsSet) {
				update();
				this.needsSet = false;
			}
			return super.add(term);
		}
	}

}
