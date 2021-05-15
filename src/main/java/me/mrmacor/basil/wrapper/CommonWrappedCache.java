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

import me.mrmacor.basil.cache.BasilCache;
import me.mrmacor.basil.cache.DelegationCache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.List.of;

/**
 * A class that both {@link WrappedCaffeineCache} and {@link WrappedGuavaCache} inherit from that contains
 * common methods both classes require that can be called via reflection.
 *
 * @since 1.0.0
 */
public abstract class CommonWrappedCache<T, K, V> implements BasilCache<K, V>, DelegationCache<T> {

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public ConcurrentMap<K, V> asMap() {
        return (ConcurrentMap<K, V>) Objects.requireNonNullElse(this.invoke("asMap", of()), new ConcurrentHashMap<>());
    }

    @Override
    public void cleanUp() {
        this.invoke("cleanUp", of());
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public V get(@Nonnull final K key, @Nonnull final Callable<V> loader) {
        return (V) this.invoke("get", of(key, loader), Object.class, Callable.class);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public V getIfPresent(@Nonnull final K key) {
        return (V) this.invoke("getIfPresent", of(key), Object.class);
    }

    @Override
    public void invalidate(@Nonnull final K key) {
        this.invoke("invalidate", of(key), Object.class);
    }

    @Override
    public void invalidateAll() {
        this.invoke("invalidateAll", of());
    }

    @Override
    public void invalidateAll(@Nonnull final Iterable<K> keys) {
        this.invoke("invalidateAll", of(keys), Iterable.class);
    }

    @Override
    public void put(@Nonnull final K key, @Nonnull final V value) {
        this.invoke("put", of(key, value), Object.class, Object.class);
    }

    @Override
    public void putAll(@Nonnull final Map<K, V> map) {
        this.invoke("putAll", of(map), Map.class);
    }

    private Object invoke(@Nonnull final String methodName, @Nonnull final List<Object> args, @Nonnull final Class<?>... types) {
        try {
            final Method method = this.delegate().getClass().getMethod(methodName, types);
            method.setAccessible(true);
            return method.invoke(this.delegate(), args.toArray());
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
