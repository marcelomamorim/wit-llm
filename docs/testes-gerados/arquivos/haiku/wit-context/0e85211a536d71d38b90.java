/*
 * @test
 * @summary Verify LocaleObjectCache constructor argument validation and basic caching behavior
 * @modules java.base/sun.util.locale
 * @run main LocaleObjectCacheWitupTest
 */

import sun.util.locale.LocaleObjectCache;

public class LocaleObjectCacheWitupTest {

    static final class UpperCache extends LocaleObjectCache<String, String> {
        UpperCache() {
            super();
        }

        UpperCache(int initialCapacity, float loadFactor, int concurrencyLevel) {
            super(initialCapacity, loadFactor, concurrencyLevel);
        }

        @Override
        protected String createObject(String key) {
            return key.toUpperCase();
        }
    }

    public static void main(String[] args) {
        new UpperCache();
        new UpperCache(0, 0.75f, 1);
        new UpperCache(16, 0.75f, 16);

        UpperCache cache = new UpperCache(16, 0.75f, 16);
        String first = cache.get("ab");
        String second = cache.get("ab");
        if (!"AB".equals(first)) {
            throw new RuntimeException("unexpected value: " + first);
        }
        if (first != second) {
            throw new RuntimeException("expected cached instance");
        }

        expectIAE(-1, 0.75f, 16);
        expectIAE(16, 0.0f, 16);
        expectIAE(16, -0.5f, 16);
        expectIAE(16, Float.NaN, 16);
        expectIAE(16, 0.75f, 0);
        expectIAE(16, 0.75f, -1);
    }

    static void expectIAE(int initialCapacity, float loadFactor, int concurrencyLevel) {
        try {
            new UpperCache(initialCapacity, loadFactor, concurrencyLevel);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for "
                + initialCapacity + ", " + loadFactor + ", " + concurrencyLevel);
    }
}
