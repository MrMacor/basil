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
package me.mrmacor.basil.wrapper;

import com.google.common.collect.ImmutableMap;
import me.mrmacor.basil.cache.BasilCache;
import me.mrmacor.basil.cache.DelegationCache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * Wrapper around a {@link com.google.common.cache.Cache} that implements {@link BasilCache}.
 *
 * @since 1.0.0
 */
public class WrappedGuavaCache<K, V> implements BasilCache<K, V>, DelegationCache<com.google.common.cache.Cache<K, V>> {

    private final com.google.common.cache.Cache<K, V> delegate;

    /**
     * Constructor for the Basil-wrapped {@link com.google.common.cache.Cache}.
     *
     * @param delegate to delegate to
     * @since 1.0.0
     */
    public WrappedGuavaCache(@Nonnull final com.google.common.cache.Cache<K, V> delegate) {
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public com.google.common.cache.Cache<K, V> delegate() {
        return this.delegate;
    }

    @Nonnull
    @Override
    public ConcurrentMap<K, V> asMap() {
        return this.delegate().asMap();
    }

    @Override
    public void cleanUp() {
        this.delegate().cleanUp();
    }

    @Nullable
    @Override
    public V get(@Nonnull final K key, @Nonnull final Callable<V> loader) throws ExecutionException {
        return this.delegate().get(key, () -> {
            try {
                return loader.call();
            } catch (final Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Nonnull
    @Override
    public ImmutableMap<K, V> allPresent(@Nonnull final Iterable<K> keys) {
        return this.delegate().getAllPresent(keys);
    }

    @Nullable
    @Override
    public V valueIfPresent(@Nonnull final K key) {
        return this.delegate().getIfPresent(key);
    }

    @Override
    public void invalidate(@Nonnull final K key) {
        this.delegate().invalidate(key);
    }

    @Override
    public void invalidateAll() {
        this.delegate().invalidateAll();
    }

    @Override
    public void invalidateAll(@Nonnull final Iterable<K> keys) {
        this.delegate().invalidateAll(keys);
    }

    @Override
    public void put(@Nonnull final K key, @Nonnull final V value) {
        this.delegate().put(key, value);
    }

    @Override
    public void putAll(@Nonnull final Map<K, V> map) {
        this.delegate().putAll(map);
    }

    @Override
    public long size() {
        return this.delegate().size();
    }
}
