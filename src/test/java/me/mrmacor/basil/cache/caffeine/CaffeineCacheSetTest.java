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
package me.mrmacor.basil.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import me.mrmacor.basil.cache.CacheSet;
import me.mrmacor.basil.wrapper.BasilCacheWrapper;
import me.mrmacor.basil.wrapper.CaffeineCacheSet;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This tests basic features of a {@link CaffeineCacheSet}.
 */
public class CaffeineCacheSetTest {

    @Test
    public void add() {
        CacheSet<String> stringCache = BasilCacheWrapper.wrap(Caffeine.newBuilder().build());
        stringCache.add("baz");

        assertTrue(stringCache.contains("baz"));
        assertEquals("baz", stringCache.asSet().toArray()[0]);
    }

    @Test
    public void addAll() {
        CacheSet<String> stringCache = BasilCacheWrapper.wrap(Caffeine.newBuilder().build());
        stringCache.addAll("uwu", "haha");

        assertTrue(stringCache.contains("uwu") && stringCache.contains("haha"));

        stringCache.invalidateAll();
        stringCache.addAll(Arrays.asList("foo", "bar"));

        assertTrue(stringCache.contains("foo") && stringCache.contains("bar"));
    }

    @Test
    public void invalidate() {
        CacheSet<String> stringCache = BasilCacheWrapper.wrap(Caffeine.newBuilder().build());
        stringCache.add("fff");

        stringCache.invalidate("fff");
        assertFalse(stringCache.contains("fff"));
    }

    @Test
    public void expires() throws InterruptedException {
        CacheSet<String> stringCache = BasilCacheWrapper.wrap(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build());
        stringCache.add("owo");
        Thread.sleep(11000);

        assertFalse(stringCache.contains("owo"));
    }

    @Test
    public void cache() {
        // Essentially just check that the Caffeine cache that we delegate to doesn't somehow get mangled.
        Cache<String, Integer> cache = Caffeine.newBuilder().build();
        CacheSet<String> stringCache = BasilCacheWrapper.wrap(cache);
        CaffeineCacheSet<String> wrappedCache = (CaffeineCacheSet<String>) stringCache;

        assertEquals(wrappedCache.delegate(), cache);
    }
}
