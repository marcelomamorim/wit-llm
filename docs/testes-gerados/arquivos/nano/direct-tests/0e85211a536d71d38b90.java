/* @test    @summary Test constructor of LocaleObjectCache with various parameters    @run main LocaleObjectCacheWitupTest */

import sun.util.locale.LocaleObjectCache;
import java.util.concurrent.ConcurrentHashMap;

public class LocaleObjectCacheWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testParameterizedConstructor();
    }

    public static void testDefaultConstructor() {
        try {
            LocaleObjectCache<Object, Object> cache = new LocaleObjectCache<>();
            if (cache == null) {
                throw new AssertionError("Default constructor returned null");
            }
            Object key = new Object();
            Object value = new Object();
            cache.put(key, value);
            Object retrieved = cache.get(key);
            if (retrieved != value) {
                throw new AssertionError("Retrieved value does not match inserted value");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testDefaultConstructor: " + e);
        }
    }

    public static void testParameterizedConstructor() {
        try {
            int initialCapacity = 32;
            float loadFactor = 0.5f;
            int concurrencyLevel = 8;
            LocaleObjectCache<Object, Object> cache = new LocaleObjectCache<>(initialCapacity, loadFactor, concurrencyLevel);
            if (cache == null) {
                throw new AssertionError("Parameterized constructor returned null");
            }
            Object key = new Object();
            Object value = new Object();
            cache.put(key, value);
            Object retrieved = cache.get(key);
            if (retrieved != value) {
                throw new AssertionError("Retrieved value does not match inserted value");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testParameterizedConstructor: " + e);
        }
    }
}
