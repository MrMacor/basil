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
import me.mrmacor.basil.cache.BasilCache;
import me.mrmacor.basil.cache.BasilDelegationCache;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * A {@link BasilCache} wrapper around {@link Cache} (Caffeine).
 *
 * @param <V> the type the cache stores
 */
public class BasilWrappedCaffeineCache<V> implements BasilDelegationCache<V, Cache<V, Integer>> {

    private final Cache<V, Integer> delegate;

    BasilWrappedCaffeineCache(Cache<V, Integer> delegate) {
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public Cache<V, Integer> delegate() {
        return delegate;
    }

    @Override
    public boolean contains(@Nonnull V value) {
        return delegate().getIfPresent(value) != null;
    }

    @Override
    public void add(@Nonnull V value) {
        delegate().put(value, 0);
    }

    @Override
    public void addAll(@Nonnull Iterable<V> values) {
        for (V value : values) {
            delegate().put(value, 0);
        }
    }

    @Override
    public void addAll(@Nonnull V... values) {
        for (V value : values) {
            delegate().put(value, 0);
        }
    }

    @Override
    public void invalidate(@Nonnull V value) {
        delegate().invalidate(value);
    }

    @Override
    public void invalidateAll(@Nonnull Iterable<V> values) {
        delegate().invalidateAll(values);
    }

    @Override
    public void invalidateAll() {
        delegate().invalidateAll();
    }

    @Override
    public boolean isEmpty() {
        return delegate().asMap().isEmpty();
    }

    @Nonnull
    @Override
    public Set<V> asSet() {
        return delegate().asMap().keySet();
    }

    @Override
    public long size() {
        return delegate().estimatedSize();
    }

    @Override
    public void cleanUp() {
        delegate().cleanUp();
    }
}
