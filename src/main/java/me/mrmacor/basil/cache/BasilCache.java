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

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * A custom implementation of a Cache{@literal <}K, V>.
 * Used as a representation of either a {@link com.google.common.cache.Cache} or a {@link com.github.benmanes.caffeine.cache.Cache}.
 *
 * @since 1.0.0
 */
//TODO cache stats!
public interface BasilCache<K, V> {

    /**
     * Returns this cache as a map of its entries.
     *
     * @return a map of this cache's entries
     * @since 1.0.0
     */
    @Nonnull ConcurrentMap<K, V> asMap();

    /**
     * Performs any pending maintenance operations needed by the cache.
     *
     * @since 1.0.0
     */
    void cleanUp();

    /**
     * Returns the value associated with the provided key in this cache,
     * calling the loader to generate it if necessary.
     *
     * @param key to look for
     * @param loader to call if this cache does not contain the provided key
     * @return the value associated with the provided key in this cache
     * @since 1.0.0
     */
    @Nullable V get(@Nonnull final K key, @Nonnull final Callable<V> loader) throws ExecutionException;

    /**
     * Returns a map of values associated with the provided keys in this cache.
     *
     * @param keys to look for
     * @return a map of values associated with the provided key in this cache
     * @since 1.0.0
     */
    @Nonnull ImmutableMap<K, V> allPresent(@Nonnull final Iterable<K> keys);

    /**
     * Returns the value associated with the provided key in this cache,
     * or null if there is no cached value for the provided key.
     *
     * @param key to look for
     * @return the value associated with the provided key, null if there is no value for the provided key
     * @since 1.0.0
     */
    @SuppressWarnings("checkstyle:MethodName")
    @Nullable V getIfPresent(@Nonnull final K key);

    /**
     * Discards any cached value associated with the provided key.
     *
     * @param key to look for
     * @since 1.0.0
     */
    void invalidate(@Nonnull final K key);

    /**
     * Discards all entries in this cache.
     *
     * @since 1.0.0
     */
    void invalidateAll();

    /**
     * Discards all entries in this cache.
     *
     * @param keys to look for
     * @since 1.0.0
     */
    void invalidateAll(@Nonnull final Iterable<K> keys);

    /**
     * Associates the provided key with the provided value in this cache.
     *
     * @param key to associate the value with
     * @since 1.0.0
     */
    void put(@Nonnull final K key, @Nonnull final V value);

    /**
     * Copies all entries from the provided map to the cache.
     *
     * @param map to copy
     * @since 1.0.0
     */
    void putAll(@Nonnull final Map<K, V> map);

    /**
     * Returns the estimated size of this cache.
     *
     * @since 1.0.0
     */
    long size();
}
