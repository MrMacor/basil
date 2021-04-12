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

import me.mrmacor.basil.cache.CacheSet;

import javax.annotation.Nonnull;

/**
 * Utility for wrapping {@link com.google.common.cache.Cache} and {@link com.github.benmanes.caffeine.cache.Cache}.
 *
 * @since 1.0.0
 */
public final class BasilCacheWrapper {

    private BasilCacheWrapper() {

    }

    /**
     * Wraps a {@link com.google.common.cache.Cache}, turning it into a {@link CacheSet}.
     *
     * @param cache to wrap
     * @return the wrapped cache
     * @since 1.0.0
     */
    @Nonnull public static <V> CacheSet<V> wrap(@Nonnull final com.google.common.cache.Cache<V, Integer> cache) {
        return new GuavaCacheSet<>(cache);
    }

    /**
     * Wraps a {@link com.github.benmanes.caffeine.cache.Cache}, turning it into a {@link CacheSet}.
     *
     * @param cache to wrap
     * @return the wrapped cache
     * @since 1.0.0
     */
    @Nonnull public static <V> CacheSet<V> wrap(@Nonnull final com.github.benmanes.caffeine.cache.Cache<V, Integer> cache) {
        return new CaffeineCacheSet<>(cache);
    }
}
