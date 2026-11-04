/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.getAndAddCharAcquire method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Define a simple holder class with a char field
        class Holder {
            public char value = 10;
        }

        Holder h = new Holder();

        // Get offset of the 'value' field
        long offset = unsafe.objectFieldOffset(Holder.class.getDeclaredField("value"));

        // Call getAndAddCharAcquire with delta 5
        char oldValue = unsafe.getAndAddCharAcquire(h, offset, (char)5);

        // Check that oldValue is initial value 10
        if (oldValue != 10) {
            throw new Error("Expected old value 10 but got " + oldValue);
        }

        // Check that the field was incremented by 5
        if (h.value != 15) {
            throw new Error("Expected new value 15 but got " + h.value);
        }

        // Call with delta 0, should return current value and not change
        oldValue = unsafe.getAndAddCharAcquire(h, offset, (char)0);
        if (oldValue != 15) {
            throw new Error("Expected old value 15 but got " + oldValue);
        }
        if (h.value != 15) {
            throw new Error("Expected value unchanged at 15 but got " + h.value);
        }

        System.out.println("All tests passed.");
    }
}
