/*
 * This file is licensed under the MIT license.
 *
 * Copyright (c) 2021 MrMacor
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.mrmacor.basil.cache;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Set;

/**
 * A cache that stores elements of a single type, not in a key-value pair.
 *
 * @param <V> the type the cache stores
 */
public interface BasilCache<V> {

    /**
     * Returns if the cache contains the specified value.
     *
     * @param value to check if the cache contains
     * @return if the cache contains the value
     */
    boolean contains(@Nonnull V value);

    /**
     * Add the specified value to the cache.
     *
     * @param value to add to the cache
     */
    void add(@Nonnull V value);

    /**
     * Add the specified values to the cache.
     *
     * @param values to add to the cache
     */
    void addAll(@Nonnull Iterable<V> values);

    /**
     * Add the specified values to the cache.
     *
     * @param values to add to the cache
     */
    void addAll(@Nonnull V... values);

    /**
     * Discards the specified value from the cache.
     *
     * @param value to invalidate
     */
    void invalidate(@Nonnull V value);

    /**
     * Discards the specified values from the cache.
     *
     * @param values to invalidate
     */
    void invalidateAll(@Nonnull Iterable<V> values);

    /**
     * Discards all values from the cache.
     */
    void invalidateAll();

    /**
     * Returns if the cache is empty.
     *
     * @return if the cache is empty
     */
    boolean isEmpty();

    /**
     * Returns the cache as a set.
     *
     * @return the cache as a set
     */
    @Nonnull Set<V> asSet();

    /**
     * Returns the approximate number of values in the cache.
     *
     * @return the approximate number of values in the cache
     */
    @Nonnegative long size();

    /**
     * Performs any pending maintenance operations needed by the cache.
     */
    void cleanUp();
}
