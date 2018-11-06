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

package org.orecruncher.lib.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EmptyList<T> implements List<T> {

	private static final EmptyList<?> EMPTY = new EmptyList<Object>();

	@SuppressWarnings("unchecked")
	public static <T> EmptyList<T> empty() {
		return (EmptyList<T>) EMPTY;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean contains(@Nonnull final Object o) {
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				throw new IllegalStateException("Set is EMPTY");
			}
		};
	}

	@Override
	public Object[] toArray() {
		return new Object[] {};
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> T[] toArray(T[] a) {
		return (T[]) new Object[] {};
	}

	@Override
	public boolean add(@Nullable final T e) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public boolean remove(@Nullable final Object o) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public boolean containsAll(@Nonnull final Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(@Nonnull final Collection<? extends T> c) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public boolean addAll(final int index, @Nonnull final Collection<? extends T> c) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public boolean removeAll(@Nonnull final Collection<?> c) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public boolean retainAll(@Nonnull final Collection<?> c) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public void clear() {
		// Ignore since there is no data
	}

	@Override
	public T get(final int index) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public T set(final int index, @Nonnull final T element) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public void add(final int index, @Nonnull final T element) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public T remove(final int index) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public int indexOf(@Nonnull final Object o) {
		return -1;
	}

	@Override
	public int lastIndexOf(@Nonnull final Object o) {
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new ListIterator<T>() {

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				throw new IllegalStateException("Set is EMPTY");
			}

			@Override
			public boolean hasPrevious() {
				return false;
			}

			@Override
			public T previous() {
				throw new IllegalStateException("Set is EMPTY");
			}

			@Override
			public int nextIndex() {
				throw new IllegalStateException("Set is EMPTY");
			}

			@Override
			public int previousIndex() {
				throw new IllegalStateException("Set is EMPTY");
			}

			@Override
			public void remove() {
				throw new IllegalStateException("Set is EMPTY");
			}

			@Override
			public void set(T e) {
				throw new IllegalStateException("Set is EMPTY");
			}

			@Override
			public void add(T e) {
				throw new IllegalStateException("Set is EMPTY");
			}

		};
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new IllegalStateException("Set is EMPTY");
	}

}
