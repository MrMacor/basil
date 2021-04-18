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
package me.mrmacor.basil.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import me.mrmacor.basil.builder.BasilCacheBuilder;
import me.mrmacor.basil.wrapper.CacheSetImpl;
import me.mrmacor.basil.wrapper.WrappedCaffeineCache;
import me.mrmacor.basil.wrapper.WrappedGuavaCache;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * This tests basic features of a {@link CacheSetImpl}.
 */
public class CacheTest {

    @Test
    public void add() {
        // Guava
        CacheSet<String> stringCache = BasilCacheBuilder.wrap(CacheBuilder.newBuilder().build()).cacheSet();
        stringCache.add("baz");

        assertTrue(stringCache.contains("baz"));
        assertEquals("baz", stringCache.asSet().toArray()[0]);

        // Caffeine
        stringCache = BasilCacheBuilder.wrap(Caffeine.newBuilder().build()).cacheSet();
        stringCache.add("baz");
        assertTrue(stringCache.contains("baz"));
        assertEquals("baz", stringCache.asSet().toArray()[0]);
    }

    @Test
    public void addAll() {
        // Guava
        CacheSet<String> stringCache = BasilCacheBuilder.wrap(CacheBuilder.newBuilder().build()).cacheSet();
        stringCache.addAll("uwu", "haha");

        assertTrue(stringCache.contains("uwu") && stringCache.contains("haha"));

        stringCache.invalidateAll();
        stringCache.addAll(Arrays.asList("foo", "bar"));

        assertTrue(stringCache.contains("foo") && stringCache.contains("bar"));

        // Caffeine
        stringCache = BasilCacheBuilder.wrap(Caffeine.newBuilder().build()).cacheSet();
        stringCache.addAll("uwu", "haha");

        assertTrue(stringCache.contains("uwu") && stringCache.contains("haha"));

        stringCache.invalidateAll();
        stringCache.addAll(Arrays.asList("foo", "bar"));

        assertTrue(stringCache.contains("foo") && stringCache.contains("bar"));
    }

    @Test
    public void invalidate() {
        // Guava
        CacheSet<String> stringCache = BasilCacheBuilder.wrap(CacheBuilder.newBuilder().build()).cacheSet();
        stringCache.add("fff");

        stringCache.invalidate("fff");
        assertFalse(stringCache.contains("fff"));

        // Caffeine
        stringCache = BasilCacheBuilder.wrap(Caffeine.newBuilder().build()).cacheSet();
        stringCache.add("fff");

        stringCache.invalidate("fff");
        assertFalse(stringCache.contains("fff"));
    }

    @Test
    public void expires() throws InterruptedException {
        // Guava
        CacheSet<String> stringCache = BasilCacheBuilder.wrap(
                CacheBuilder.newBuilder()
                    .expireAfterWrite(10, TimeUnit.SECONDS)
                    .build())
                .cacheSet();
        stringCache.add("owo");
        Thread.sleep(11000);

        assertFalse(stringCache.contains("owo"));

        // Caffeine
        stringCache = BasilCacheBuilder.wrap(
                Caffeine.newBuilder()
                        .expireAfterWrite(10, TimeUnit.SECONDS)
                        .build())
                .cacheSet();
        stringCache.add("owo");
        Thread.sleep(11000);

        assertFalse(stringCache.contains("owo"));
    }

    @Test
    public void cache() { // Essentially just check that the cache that we delegate to doesn't somehow get mangled.
        // Guava
        com.google.common.cache.Cache<String, Integer> guavaCache = CacheBuilder.newBuilder().build();
        CacheSet<String> stringCache = BasilCacheBuilder.wrap(guavaCache).cacheSet();
        CacheSetImpl<String> wrappedCache = (CacheSetImpl<String>) stringCache;

        assertEquals(((WrappedGuavaCache<String, Integer>) wrappedCache.delegate()).delegate(), guavaCache);

        // Caffeine
        com.github.benmanes.caffeine.cache.Cache<String, Integer> caffeineCache = Caffeine.newBuilder().build();
        stringCache = BasilCacheBuilder.wrap(caffeineCache).cacheSet();
        wrappedCache = (CacheSetImpl<String>) stringCache;

        assertEquals(((WrappedCaffeineCache<String, Integer>) wrappedCache.delegate()).delegate(), caffeineCache);
    }
}
