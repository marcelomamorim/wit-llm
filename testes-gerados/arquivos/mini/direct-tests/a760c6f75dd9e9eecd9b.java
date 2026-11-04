/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.getAndAddShortAcquire method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Use a simple holder object with a short field
        class Holder {
            volatile short value = 10;
        }

        Holder h = new Holder();

        // Get offset of the 'value' field
        long offset = unsafe.objectFieldOffset(Holder.class.getDeclaredField("value"));

        // Test getAndAddShortAcquire increments correctly and returns previous value
        short delta = 5;
        short prev = unsafe.getAndAddShortAcquire(h, offset, delta);

        if (prev != 10) {
            throw new Error("Expected previous value 10, got " + prev);
        }

        short newValue = unsafe.getShortAcquire(h, offset);
        if (newValue != 15) {
            throw new Error("Expected new value 15, got " + newValue);
        }

        // Test with negative delta
        delta = -3;
        prev = unsafe.getAndAddShortAcquire(h, offset, delta);

        if (prev != 15) {
            throw new Error("Expected previous value 15, got " + prev);
        }

        newValue = unsafe.getShortAcquire(h, offset);
        if (newValue != 12) {
            throw new Error("Expected new value 12, got " + newValue);
        }

        // Test with zero delta
        delta = 0;
        prev = unsafe.getAndAddShortAcquire(h, offset, delta);

        if (prev != 12) {
            throw new Error("Expected previous value 12, got " + prev);
        }

        newValue = unsafe.getShortAcquire(h, offset);
        if (newValue != 12) {
            throw new Error("Expected new value 12, got " + newValue);
        }

        System.out.println("All tests passed.");
    }
}
