package me.mrmacor.basil.cache.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.mrmacor.basil.cache.BasilCache;
import me.mrmacor.basil.wrapper.BasilCacheWrapper;
import me.mrmacor.basil.wrapper.BasilWrappedGuavaCache;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * This tests basic features of a {@link BasilWrappedGuavaCache}.
 */
public class BasilWrappedGuavaTest {

    @Test
    public void add() {
        BasilCache<String> stringCache = BasilCacheWrapper.wrap(CacheBuilder.newBuilder().build());
        stringCache.add("baz");

        assertTrue(stringCache.contains("baz"));
        assertEquals("baz", stringCache.asSet().toArray()[0]);
    }

    @Test
    public void addAll() {
        BasilCache<String> stringCache = BasilCacheWrapper.wrap(CacheBuilder.newBuilder().build());
        stringCache.addAll("uwu", "haha");

        assertTrue(stringCache.contains("uwu") && stringCache.contains("haha"));

        stringCache.invalidateAll();
        stringCache.addAll(Arrays.asList("foo", "bar"));

        assertTrue(stringCache.contains("foo") && stringCache.contains("bar"));
    }

    @Test
    public void invalidate() {
        BasilCache<String> stringCache = BasilCacheWrapper.wrap(CacheBuilder.newBuilder().build());
        stringCache.add("fff");

        stringCache.invalidate("fff");
        assertFalse(stringCache.contains("fff"));
    }

    @Test
    public void expires() throws InterruptedException {
        BasilCache<String> stringCache = BasilCacheWrapper.wrap(CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build());
        stringCache.add("owo");
        Thread.sleep(11000);

        assertFalse(stringCache.contains("owo"));
    }

    @Test
    public void cache() {
        // Essentially just check that the Guava cache that we delegate to doesn't somehow get mangled.
        Cache<String, Integer> cache = CacheBuilder.newBuilder().build();
        BasilCache<String> stringCache = BasilCacheWrapper.wrap(cache);
        BasilWrappedGuavaCache<String> wrappedCache = (BasilWrappedGuavaCache<String>) stringCache;

        assertEquals(wrappedCache.delegate(), cache);
    }
}
