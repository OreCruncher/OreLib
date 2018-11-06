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
import java.util.Set;

import javax.annotation.Nonnull;

/**
 * Simple class to represent an empty set.  Goal is to minimize
 * processing if there are no elements.
 * @param <T>
 */
public class EmptySet<T> implements Set<T> {

	private static final EmptySet<?> EMPTY = new EmptySet<Object>();
	
	@SuppressWarnings("unchecked")
	public static <T> EmptySet<T> empty() {
		return (EmptySet<T>) EMPTY;
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
	public boolean add(@Nonnull final T e) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public boolean remove(@Nonnull final Object o) {
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
	public boolean retainAll(@Nonnull final Collection<?> c) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new IllegalStateException("Set is EMPTY");
	}

	@Override
	public void clear() {
		// Ignore since there is no data
	}

}
