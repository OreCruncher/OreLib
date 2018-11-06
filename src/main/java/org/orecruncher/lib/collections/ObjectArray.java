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

package org.orecruncher.lib.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

public class ObjectArray<T> implements Collection<T> {

	private static final int DEFAULT_SIZE = 16;

	protected Object[] data;
	protected int insertionIdx;

	public ObjectArray() {
		this(DEFAULT_SIZE);
	}

	public ObjectArray(final int size) {
		this.data = new Object[size];
	}

	private void resize() {
		final Object[] t = new Object[this.data.length * 2];
		System.arraycopy(this.data, 0, t, 0, this.data.length);
		this.data = t;
	}

	@Override
	public int size() {
		return this.insertionIdx;
	}

	@SuppressWarnings("unchecked")
	public T get(final int idx) {
		if (idx >= 0 && idx < this.insertionIdx)
			return (T) this.data[idx];
		return null;
	}

	private void remove0(final int idx) {
		final Object m = this.data[--this.insertionIdx];
		this.data[this.insertionIdx] = null;
		if (idx < this.insertionIdx)
			this.data[idx] = m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean removeIf(@Nonnull final Predicate<? super T> pred) {
		boolean result = false;
		for (int i = this.insertionIdx - 1; i >= 0; i--) {
			if (pred.test((T) this.data[i])) {
				result = true;
				this.remove0(i);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void forEach(@Nonnull final Consumer<? super T> consumer) {
		for (int i = this.insertionIdx - 1; i >= 0; i--)
			consumer.accept((T) this.data[i]);
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	private int find(@Nonnull final Object o) {
		for (int i = 0; i < this.insertionIdx; i++)
			if (o.equals(this.data[i]))
				return i;
		return -1;
	}

	@Override
	public boolean contains(@Nonnull final Object o) {
		return find(o) != -1;
	}

	@Override
	@Nonnull
	public Object[] toArray() {
		final Object[] result = new Object[this.insertionIdx];
		System.arraycopy(this.data, 0, result, 0, this.insertionIdx);
		return result;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	@Nonnull
	public <T> T[] toArray(@Nonnull final T[] a) {
		// From ArrayList impl
		if (a.length < this.insertionIdx)
			// Make a new array of a's runtime type, but my contents:
			return (T[]) Arrays.copyOf(this.data, this.insertionIdx, a.getClass());
		System.arraycopy(this.data, 0, a, 0, this.insertionIdx);
		if (a.length > this.insertionIdx)
			a[this.insertionIdx] = null;
		return a;
	}

	@Override
	public boolean add(@Nonnull final T e) {
		if (e == null)
			return false;

		if (this.data.length == this.insertionIdx)
			resize();

		this.data[this.insertionIdx++] = e;
		return true;
	}

	@Override
	public boolean remove(@Nonnull final Object o) {
		final int idx = find(o);
		if (idx != -1)
			this.remove0(idx);
		return idx != -1;
	}

	@Override
	public boolean containsAll(@Nonnull final Collection<?> c) {
		for (final Object obj : c)
			if (!this.contains(obj))
				return false;
		return true;
	}

	@Override
	public boolean addAll(@Nonnull final Collection<? extends T> c) {
		boolean result = false;
		for (final T element : c)
			result |= this.add(element);
		return result;
	}

	public boolean addAll(@Nonnull final T[] list) {
		boolean result = false;
		for (int i = 0; i < list.length; i++)
			result |= this.add(list[i]);
		return result;
	}

	@Override
	public boolean removeAll(@Nonnull final Collection<?> c) {
		return this.removeIf(entry -> c.contains(entry));
	}

	@Override
	public boolean retainAll(@Nonnull final Collection<?> c) {
		return this.removeIf(entry -> !c.contains(entry));
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.insertionIdx; i++)
			this.data[i] = null;
		this.insertionIdx = 0;
	}

	@Override
	@Nonnull
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			private int idx = -1;

			@Override
			public boolean hasNext() {
				return (this.idx + 1) < ObjectArray.this.insertionIdx;
			}

			@SuppressWarnings("unchecked")
			@Override
			public T next() {
				return (T) ObjectArray.this.data[++this.idx];
			}

		};
	}

}
