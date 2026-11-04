/*
 * @test
 * @summary Tests Unsafe.arrayIndexScale for null and array classes
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        boolean npeThrown = false;
        try {
            unsafe.arrayIndexScale(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, arrayIndexScale null must throw NullPointerException);

        int byteScale = unsafe.arrayIndexScale(byte[].class);
        int intScale = unsafe.arrayIndexScale(int[].class);
        int objScale = unsafe.arrayIndexScale(Object[].class);

        check(byteScale > 0, byte[] scale must be positive);
        check(intScale > 0, int[] scale must be positive);
        check(objScale > 0, Object[] scale must be positive);
        check(intScale >= byteScale, int[] scale must be at least byte[] scale);

        check(unsafe.arrayIndexScale(int[].class) == intScale,
              repeated call must return same int[] scale);
        check(unsafe.arrayIndexScale(byte[].class) == byteScale,
              repeated call must return same byte[] scale);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
