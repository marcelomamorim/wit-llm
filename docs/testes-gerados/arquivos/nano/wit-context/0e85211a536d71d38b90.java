/* @test    @summary Test for LocaleObjectCache constructor with valid parameters    @run main LocaleObjectCacheWitupTest */

import sun.util.locale.LocaleObjectCache;

public class LocaleObjectCacheWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidConstructorNegativeInitialCapacity();
        testInvalidConstructorZeroLoadFactor();
        testInvalidConstructorNegativeConcurrencyLevel();
    }

    private static void testValidConstructor() {
        try {
            LocaleObjectCache<Object, Object> cache = new LocaleObjectCache<>(16, 0.75f, 16);
            System.out.println("testValidConstructor: PASSED");
        } catch (Exception e) {
            System.out.println("testValidConstructor: FAILED with exception: " + e);
        }
    }

    private static void testInvalidConstructorNegativeInitialCapacity() {
        try {
            LocaleObjectCache<Object, Object> cache = new LocaleObjectCache<>(-1, 0.75f, 16);
            System.out.println("testInvalidConstructorNegativeInitialCapacity: FAILED - exception expected");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidConstructorNegativeInitialCapacity: PASSED");
        } catch (Exception e) {
            System.out.println("testInvalidConstructorNegativeInitialCapacity: FAILED with unexpected exception: " + e);
        }
    }

    private static void testInvalidConstructorZeroLoadFactor() {
        try {
            LocaleObjectCache<Object, Object> cache = new LocaleObjectCache<>(16, 0.0f, 16);
            System.out.println("testInvalidConstructorZeroLoadFactor: FAILED - exception expected");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidConstructorZeroLoadFactor: PASSED");
        } catch (Exception e) {
            System.out.println("testInvalidConstructorZeroLoadFactor: FAILED with unexpected exception: " + e);
        }
    }

    private static void testInvalidConstructorNegativeConcurrencyLevel() {
        try {
            LocaleObjectCache<Object, Object> cache = new LocaleObjectCache<>(16, 0.75f, -1);
            System.out.println("testInvalidConstructorNegativeConcurrencyLevel: FAILED - exception expected");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidConstructorNegativeConcurrencyLevel: PASSED");
        } catch (Exception e) {
            System.out.println("testInvalidConstructorNegativeConcurrencyLevel: FAILED with unexpected exception: " + e);
        }
    }
}
