/*
 * @test
 * @summary Test LocaleObjectCache constructor with various parameters including invalid ones
 * @run main LocaleObjectCacheWitupTest
 */

import sun.util.locale.LocaleObjectCache;
import java.util.concurrent.ConcurrentHashMap;

public class LocaleObjectCacheWitupTest {

    // A minimal concrete subclass to instantiate LocaleObjectCache
    static class TestCache extends LocaleObjectCache<String, String> {
        public TestCache() {
            super();
        }

        public TestCache(int initialCapacity, float loadFactor, int concurrencyLevel) {
            super(initialCapacity, loadFactor, concurrencyLevel);
        }

        @Override
        protected String createObject(String key) {
            return "value-" + key;
        }
    }

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidConstructor();
    }

    private static void testValidConstructor() {
        // Normal valid parameters
        TestCache cache = new TestCache(16, 0.75f, 16);
        String val = cache.get("test");
        if (val == null || !val.equals("value-test")) {
            throw new RuntimeException("Failed to get expected value from cache");
        }
    }

    private static void testInvalidConstructor() {
        // According to ConcurrentHashMap constructor docs, these invalid parameters throw IllegalArgumentException
        // We test that LocaleObjectCache constructor propagates this exception

        // initialCapacity < 0
        try {
            new TestCache(-1, 0.75f, 16);
            throw new RuntimeException("Expected IllegalArgumentException for initialCapacity < 0");
        } catch (IllegalArgumentException expected) {
        }

        // loadFactor <= 0.0f
        try {
            new TestCache(16, 0.0f, 16);
            throw new RuntimeException("Expected IllegalArgumentException for loadFactor <= 0.0f");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new TestCache(16, -0.1f, 16);
            throw new RuntimeException("Expected IllegalArgumentException for loadFactor <= 0.0f");
        } catch (IllegalArgumentException expected) {
        }

        // concurrencyLevel <= 0
        try {
            new TestCache(16, 0.75f, 0);
            throw new RuntimeException("Expected IllegalArgumentException for concurrencyLevel <= 0");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new TestCache(16, 0.75f, -1);
            throw new RuntimeException("Expected IllegalArgumentException for concurrencyLevel <= 0");
        } catch (IllegalArgumentException expected) {
        }
    }
}
