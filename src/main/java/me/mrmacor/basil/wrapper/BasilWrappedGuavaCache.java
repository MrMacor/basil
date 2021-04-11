package me.mrmacor.basil.wrapper;

import com.google.common.cache.Cache;
import me.mrmacor.basil.cache.BasilCache;
import me.mrmacor.basil.cache.BasilDelegationCache;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * A {@link BasilCache} wrapper around {@link Cache} (Guava).
 *
 * @param <V> the type the cache stores
 */
public class BasilWrappedGuavaCache<V> implements BasilDelegationCache<V, Cache<V, Integer>> {

    private final Cache<V, Integer> delegate;

    BasilWrappedGuavaCache(Cache<V, Integer> delegate) {
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
        return delegate().size();
    }

    @Override
    public void cleanUp() {
        delegate().cleanUp();
    }
}
