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

import com.github.benmanes.caffeine.cache.Cache;
import me.mrmacor.basil.cache.CacheSet;
import me.mrmacor.basil.cache.DelegationCacheSet;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * A {@link CacheSet} wrapper around {@link Cache} (Caffeine).
 *
 * @param <V> the type the cache stores
 * @since 1.0.0
 */
public class CaffeineCacheSet<V> implements DelegationCacheSet<V, Cache<V, Integer>> {

    private final Cache<V, Integer> delegate;

    CaffeineCacheSet(final Cache<V, Integer> delegate) {
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public Cache<V, Integer> delegate() {
        return this.delegate;
    }

    @Override
    public boolean contains(@Nonnull final V value) {
        return this.delegate().getIfPresent(value) != null;
    }

    @Override
    public void add(@Nonnull final V value) {
        this.delegate().put(value, 0);
    }

    @Override
    public void addAll(@Nonnull final Iterable<V> values) {
        for (final V value : values) {
            this.delegate().put(value, 0);
        }
    }

    @Override
    public void addAll(@Nonnull final V... values) {
        for (final V value : values) {
            this.delegate().put(value, 0);
        }
    }

    @Override
    public void invalidate(@Nonnull final V value) {
        this.delegate().invalidate(value);
    }

    @Override
    public void invalidateAll(@Nonnull final Iterable<V> values) {
        this.delegate().invalidateAll(values);
    }

    @Override
    public void invalidateAll() {
        this.delegate().invalidateAll();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().asMap().isEmpty();
    }

    @Nonnull
    @Override
    public Set<V> asSet() {
        return this.delegate().asMap().keySet();
    }

    @Override
    public long size() {
        return this.delegate().estimatedSize();
    }

    @Override
    public void cleanUp() {
        this.delegate().cleanUp();
    }
}
