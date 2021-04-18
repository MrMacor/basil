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
package me.mrmacor.basil.builder;

import me.mrmacor.basil.cache.BasilCache;
import me.mrmacor.basil.cache.CacheSet;
import me.mrmacor.basil.wrapper.CacheSetImpl;
import me.mrmacor.basil.wrapper.WrappedCaffeineCache;
import me.mrmacor.basil.wrapper.WrappedGuavaCache;

import javax.annotation.Nonnull;

/**
 * Utility class to convert {@link com.google.common.cache.Cache} and {@link com.github.benmanes.caffeine.cache.Cache} cache implementations into their Basil counterparts.
 *
 * @since 1.0.0
 */
public final class BasilCacheBuilder {

    private BasilCacheBuilder() {

    }

    /**
     * Start the conversion process for a {@link com.github.benmanes.caffeine.cache.Cache}.
     *
     * @param cache to wrap
     * @return the next step in the building process
     * @since 1.0.0
     */
    @Nonnull
    public static <K, V> CacheProvider<K, V> wrap(@Nonnull final com.github.benmanes.caffeine.cache.Cache<K, V> cache) {
        return new CaffeineCacheWrapper<>(cache);
    }

    /**
     * Start the conversion process for a {@link com.google.common.cache.Cache}.
     *
     * @param cache to wrap
     * @return the next step in the building process
     * @since 1.0.0
     */
    @Nonnull
    public static <K, V> CacheProvider<K, V> wrap(@Nonnull final com.google.common.cache.Cache<K, V> cache) {
        return new GuavaCacheWrapper<>(cache);
    }

    /**
     * The last step in converting Guava and Caffeine caches to their Basil counterpart.
     *
     * @since 1.0.0
     */
    public interface CacheProvider<K, V> {

        /**
         * Returns the cache provided in earlier steps in the form of a {@link BasilCache}.
         *
         * @return the cache as a {@link BasilCache}
         * @since 1.0.0
         */
        BasilCache<K, V> basilCache();

        /**
         * Returns the cache provided in earlier steps in the form of a {@link CacheSet}.
         *
         * @return the cache as a {@link CacheSet}
         * @since 1.0.0
         */
        @Nonnull
        default <U> CacheSet<U> cacheSet() {
            return new CacheSetImpl<>((BasilCache<U, Integer>) this.basilCache());
        }
    }

    /**
     * Implementation of {@link CacheProvider}, Caffeine-flavored.
     *
     * @since 1.0.0
     */
    public static class CaffeineCacheWrapper<K, V> implements CacheProvider<K, V> {

        private final com.github.benmanes.caffeine.cache.Cache<K, V> cache;

        CaffeineCacheWrapper(final com.github.benmanes.caffeine.cache.Cache<K, V> cache) {
            this.cache = cache;
        }

        @Override
        @Nonnull
        public BasilCache<K, V> basilCache() {
            return new WrappedCaffeineCache<>(this.cache);
        }
    }

    /**
     * Implementation of {@link CacheProvider}, Guava-flavored.
     *
     * @since 1.0.0
     */
    public static class GuavaCacheWrapper<K, V> implements CacheProvider<K, V> {

        private final com.google.common.cache.Cache<K, V> cache;

        GuavaCacheWrapper(final com.google.common.cache.Cache<K, V> cache) {
            this.cache = cache;
        }

        @Override
        @Nonnull
        public BasilCache<K, V> basilCache() {
            return new WrappedGuavaCache<>(this.cache);
        }
    }
}
