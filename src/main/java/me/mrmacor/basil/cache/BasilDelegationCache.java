package me.mrmacor.basil.cache;

import javax.annotation.Nonnull;

/**
 * A cache that delegates its operations to another cache.
 *
 * @param <V> the type the cache stores
 * @param <T> the type the cache wraps
 */
public interface BasilDelegationCache<V, T> extends BasilCache<V> {

    /**
     * Returns the cache this forwarding cache delegates its operations to.
     *
     * @return the cache this forwarding cache delegates its operations to
     */
    @Nonnull T delegate();
}
