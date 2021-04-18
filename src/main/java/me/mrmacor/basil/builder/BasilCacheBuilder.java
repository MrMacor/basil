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
