/*
 * @test
 * @summary Verifies Unsafe.shouldBeInitialized rejects null and returns normally for a loaded class
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        boolean npeThrown = false;
        try {
            unsafe.shouldBeInitialized(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null class");
        }

        boolean result = unsafe.shouldBeInitialized(Unsafe.class);
        if (result) {
            throw new RuntimeException("Unsafe class is already initialized; expected false");
        }
    }
}
