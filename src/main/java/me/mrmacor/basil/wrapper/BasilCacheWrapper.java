package me.mrmacor.basil.wrapper;

import me.mrmacor.basil.cache.BasilCache;

import javax.annotation.Nonnull;

public class BasilCacheWrapper {
    @Nonnull public static <V> BasilCache<V> wrap(@Nonnull com.google.common.cache.Cache<V, Integer> cache) {
        return new BasilWrappedGuavaCache<>(cache);
    }

    @Nonnull public static <V> BasilCache<V> wrap(@Nonnull com.github.benmanes.caffeine.cache.Cache<V, Integer> cache) {
        return new BasilWrappedCaffeineCache<>(cache);
    }
}
