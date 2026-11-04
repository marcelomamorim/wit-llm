/* @test    @summary Test getAndAddShortAcquire method for correct behavior and regression protection    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testGetAndAddShortAcquire();
    }

    private static void testGetAndAddShortAcquire() {
        Unsafe unsafe = Unsafe.getUnsafe();
        class Dummy {
            short value = 10;
        }
        Dummy dummy = new Dummy();
        try {
            // Obtain the offset of the 'value' field
            java.lang.reflect.Field field = Dummy.class.getDeclaredField("value");
            long offset = unsafe.objectFieldOffset(field);
            // Initial value
            short initial = unsafe.getShortAcquire(dummy, offset);
            // Add delta
            short delta = 5;
            short previous = unsafe.getAndAddShortAcquire(dummy, offset, delta);
            // Verify previous value
            if (previous != initial) {
                throw new AssertionError("Previous value mismatch: expected " + initial + " but got " + previous);
            }
            // Verify new value
            short newVal = unsafe.getShortAcquire(dummy, offset);
            if (newVal != (short)(initial + delta)) {
                throw new AssertionError("New value mismatch: expected " + (initial + delta) + " but got " + newVal);
            }
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Failed to access field for offset calculation", e);
        }
    }
}
