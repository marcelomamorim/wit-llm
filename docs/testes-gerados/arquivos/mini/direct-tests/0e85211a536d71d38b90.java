/*
 * @test
 * @summary Test LocaleObjectCache constructor with initialCapacity, loadFactor, concurrencyLevel
 * @run main LocaleObjectCacheWitupTest
 */

import sun.util.locale.LocaleObjectCache;

public class LocaleObjectCacheWitupTest {

    static class TestCache extends LocaleObjectCache<String, String> {
        public TestCache(int initialCapacity, float loadFactor, int concurrencyLevel) {
            super(initialCapacity, loadFactor, concurrencyLevel);
        }

        @Override
        protected String createObject(String key) {
            return key == null ? null : key.toUpperCase();
        }
    }

    public static void main(String[] args) {
        // Test with typical parameters
        TestCache cache = new TestCache(10, 0.75f, 4);
        String key = "test";
        String value = cache.get(key);
        if (!"TEST".equals(value)) {
            throw new RuntimeException("Expected value 'TEST' but got '" + value + "'");
        }

        // Test with zero initialCapacity (allowed by ConcurrentHashMap)
        TestCache cacheZero = new TestCache(0, 0.75f, 4);
        String valZero = cacheZero.get("zero");
        if (!"ZERO".equals(valZero)) {
            throw new RuntimeException("Expected value 'ZERO' but got '" + valZero + "'");
        }

        // Test with concurrencyLevel 1
        TestCache cacheConc1 = new TestCache(16, 0.75f, 1);
        String valConc1 = cacheConc1.get("conc1");
        if (!"CONC1".equals(valConc1)) {
            throw new RuntimeException("Expected value 'CONC1' but got '" + valConc1 + "'");
        }

        // Test with loadFactor at boundary 1.0f
        TestCache cacheLoad1 = new TestCache(16, 1.0f, 4);
        String valLoad1 = cacheLoad1.get("load1");
        if (!"LOAD1".equals(valLoad1)) {
            throw new RuntimeException("Expected value 'LOAD1' but got '" + valLoad1 + "'");
        }

        // Test with negative initialCapacity (ConcurrentHashMap throws IllegalArgumentException)
        boolean threw = false;
        try {
            new TestCache(-1, 0.75f, 4);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
        }

        // Test with zero concurrencyLevel (ConcurrentHashMap throws IllegalArgumentException)
        threw = false;
        try {
            new TestCache(16, 0.75f, 0);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for zero concurrencyLevel");
        }

        // Test with negative loadFactor (ConcurrentHashMap throws IllegalArgumentException)
        threw = false;
        try {
            new TestCache(16, -0.1f, 4);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative loadFactor");
        }

        System.out.println("All tests passed.");
    }
}
