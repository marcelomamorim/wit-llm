/*
 * @test
 * @summary Verify LocaleObjectCache(int, float, int) constructor configures a working cache and rejects invalid concurrency level
 * @modules java.base/sun.util.locale
 * @run main LocaleObjectCacheWitupTest
 */

import sun.util.locale.LocaleObjectCache;

public class LocaleObjectCacheWitupTest {

    static class Cache extends LocaleObjectCache<String, String> {
        int created;

        Cache(int initialCapacity, float loadFactor, int concurrencyLevel) {
            super(initialCapacity, loadFactor, concurrencyLevel);
        }

        @Override
        protected String createObject(String key) {
            created++;
            return new String(key);
        }

        void store(String key, String value) {
            put(key, value);
        }
    }

    static class NullCache extends LocaleObjectCache<String, String> {
        NullCache(int initialCapacity, float loadFactor, int concurrencyLevel) {
            super(initialCapacity, loadFactor, concurrencyLevel);
        }

        @Override
        protected String createObject(String key) {
            return null;
        }
    }

    public static void main(String[] args) {
        testGetCreatesAndCaches();
        testDistinctKeys();
        testPutThenGetUsesStoredValue();
        testNullCreatedValueReturnsNull();
        testInvalidConcurrencyLevelRejected();
    }

    static void testGetCreatesAndCaches() {
        Cache c = new Cache(8, 0.5f, 4);
        String v1 = c.get("abc");
        check(v1 != null, "first get must return a value");
        check("abc".equals(v1), "value must equal createObject result");
        check(c.created == 1, "createObject must be called once, was " + c.created);
        String v2 = c.get("abc");
        check(v1 == v2, "second get must return cached instance");
        check(c.created == 1, "createObject must not be called again on hit");
    }

    static void testDistinctKeys() {
        Cache c = new Cache(16, 0.75f, 16);
        check("abc".equals(c.get("abc")), "abc lookup");
        check("xyz".equals(c.get("xyz")), "xyz lookup");
        check(c.created == 2, "two distinct keys must create two objects, was " + c.created);
    }

    static void testPutThenGetUsesStoredValue() {
        Cache c = new Cache(16, 0.75f, 2);
        c.store("k", "stored");
        String v = c.get("k");
        check("stored".equals(v), "get must return previously stored value");
        check(c.created == 0, "createObject must not be called when value is stored");
    }

    static void testNullCreatedValueReturnsNull() {
        NullCache c = new NullCache(16, 0.75f, 16);
        check(c.get("k") == null, "null from createObject must yield null");
    }

    static void testInvalidConcurrencyLevelRejected() {
        try {
            new Cache(16, 0.75f, 0);
            throw new RuntimeException("expected IllegalArgumentException for concurrencyLevel 0");
        } catch (IllegalArgumentException expected) {
            // expected: ConcurrentHashMap rejects non-positive concurrencyLevel
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
