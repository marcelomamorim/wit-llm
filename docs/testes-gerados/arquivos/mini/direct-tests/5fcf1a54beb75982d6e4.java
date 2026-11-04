/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.getAndSetShortAcquire method
 * @run main UnsafeWitupTest
 */

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        jdk.internal.misc.Unsafe unsafe = jdk.internal.misc.Unsafe.getUnsafe();

        // Allocate a short array to test on
        short[] array = new short[1];
        long baseOffset = unsafe.arrayBaseOffset(short[].class);

        // Initial value
        array[0] = 12345;

        // Call getAndSetShortAcquire to set new value and get old value
        short oldValue = unsafe.getAndSetShortAcquire(array, baseOffset, (short) 54321);

        // Check that oldValue is the initial value
        if (oldValue != 12345) {
            throw new Error("Expected old value 12345 but got " + oldValue);
        }

        // Check that the new value was set
        short newValue = unsafe.getShortAcquire(array, baseOffset);
        if (newValue != 54321) {
            throw new Error("Expected new value 54321 but got " + newValue);
        }

        // Test with null object and offset 0 (likely to cause exception or undefined behavior)
        // We do not expect a specific exception as per instructions, so just call and ignore
        try {
            unsafe.getAndSetShortAcquire(null, 0L, (short) 0);
        } catch (Throwable t) {
            // Accept any throwable, no assert
        }
    }
}
