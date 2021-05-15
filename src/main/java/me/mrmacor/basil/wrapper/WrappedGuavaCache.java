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

import javax.annotation.Nonnull;

/**
 * Wrapper around a {@link com.google.common.cache.Cache} that implements {@link BasilCache}.
 *
 * @since 1.0.0
 */
public class WrappedGuavaCache<K, V> extends CommonWrappedCache<com.google.common.cache.Cache<K, V>, K, V> {

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
    public ImmutableMap<K, V> allPresent(@Nonnull final Iterable<K> keys) {
        return this.delegate().getAllPresent(keys);
    }

    @Override
    public long size() {
        return this.delegate().size();
    }
}
