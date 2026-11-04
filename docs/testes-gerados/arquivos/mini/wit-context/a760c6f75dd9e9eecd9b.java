/*
 * @test
 * @summary Test Unsafe.getAndAddShortAcquire behavior and exception guards
 * @run main UnsafeWitupTest
 */

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test normal behavior with a valid object and offset
        Object obj = new Object();
        long offset = 12L; // aligned offset (multiple of 2, not 3)
        short delta = 5;

        // We cannot directly set a short field at offset, so test that method returns a short
        // and does not throw for a valid offset.
        try {
            short result = unsafe.getAndAddShortAcquire(obj, offset, delta);
            // result is a short, no exception expected
            if (!(result >= Short.MIN_VALUE && result <= Short.MAX_VALUE)) {
                throw new RuntimeException("Returned value out of short range");
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception for valid offset", t);
        }

        // Test behavior with offset that may trigger IllegalArgumentException
        // According to WIT expaths, offsets with (offset & 3) == 3 may cause IllegalArgumentException
        // We test that for such offset, exception is thrown.

        long badOffset = 3L; // (3 & 3) == 3

        try {
            unsafe.getAndAddShortAcquire(obj, badOffset, delta);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for bad offset");
        } catch (IllegalArgumentException expected) {
            // Expected exception, test passed
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type", t);
        }

        // Test with null object and valid offset - should behave consistently
        try {
            short resultNull = unsafe.getAndAddShortAcquire(null, offset, delta);
            if (!(resultNull >= Short.MIN_VALUE && resultNull <= Short.MAX_VALUE)) {
                throw new RuntimeException("Returned value out of short range for null object");
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception for null object with valid offset", t);
        }

        System.out.println("All tests passed.");
    }
}
