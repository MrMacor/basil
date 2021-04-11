package me.mrmacor.basil.cache;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Set;

/**
 * A cache that stores elements of a single type, not in a key-value pair.
 *
 * @param <V> the type the cache stores
 */
public interface BasilCache<V> {

    /**
     * Returns if the cache contains the specified value.
     *
     * @param value to check if the cache contains
     * @return if the cache contains the value
     */
    boolean contains(@Nonnull V value);

    /**
     * Add the specified value to the cache.
     *
     * @param value to add to the cache
     */
    void add(@Nonnull V value);

    /**
     * Add the specified values to the cache.
     *
     * @param values to add to the cache
     */
    void addAll(@Nonnull Iterable<V> values);

    /**
     * Add the specified values to the cache.
     *
     * @param values to add to the cache
     */
    void addAll(@Nonnull V... values);

    /**
     * Discards the specified value from the cache.
     *
     * @param value to invalidate
     */
    void invalidate(@Nonnull V value);

    /**
     * Discards the specified values from the cache.
     *
     * @param values to invalidate
     */
    void invalidateAll(@Nonnull Iterable<V> values);

    /**
     * Discards all values from the cache.
     */
    void invalidateAll();

    /**
     * Returns if the cache is empty.
     *
     * @return if the cache is empty
     */
    boolean isEmpty();

    /**
     * Returns the cache as a set.
     *
     * @return the cache as a set
     */
    @Nonnull Set<V> asSet();

    /**
     * Returns the approximate number of values in the cache.
     *
     * @return the approximate number of values in the cache
     */
    @Nonnegative long size();

    /**
     * Performs any pending maintenance operations needed by the cache.
     */
    void cleanUp();
}
