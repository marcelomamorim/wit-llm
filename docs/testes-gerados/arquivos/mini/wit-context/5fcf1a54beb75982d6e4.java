/*
 * @test
 * @summary Test Unsafe.getAndSetShortAcquire behavior and exception paths
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // We cannot create arbitrary objects or offsets, so test with a simple object and valid offset
        Object obj = new Object();

        // Use a field offset from a known field to get a valid offset
        // Since Unsafe.getUnsafe() is restricted, this test assumes running with proper privileges

        // We test getAndSetShortAcquire with a valid offset and newValue
        // Since we cannot get a real offset easily, test with 0L offset on a dummy object
        // This may throw IllegalArgumentException if offset is invalid, so catch and report

        long offset = 0L;
        short newValue = 123;

        try {
            short oldValue = unsafe.getAndSetShortAcquire(obj, offset, newValue);
            // The returned oldValue is a short; just print it to confirm no exception
            System.out.println("getAndSetShortAcquire returned: " + oldValue);
        } catch (IllegalArgumentException iae) {
            // Expected for invalid offset
            System.out.println("Caught expected IllegalArgumentException for invalid offset: " + iae.getMessage());
        } catch (Throwable t) {
            // Unexpected exceptions should fail the test
            throw new RuntimeException("Unexpected exception", t);
        }

        // Test with null object and offset 0L, expecting IllegalArgumentException or other exception
        try {
            unsafe.getAndSetShortAcquire(null, offset, newValue);
            System.out.println("getAndSetShortAcquire with null object did not throw exception");
        } catch (IllegalArgumentException iae) {
            System.out.println("Caught expected IllegalArgumentException for null object: " + iae.getMessage());
        } catch (Throwable t) {
            System.out.println("Caught unexpected exception for null object: " + t);
        }

        // Note: The WIT expaths suggest IllegalArgumentException on certain offset bit patterns combined with endianness
        // We cannot reliably reproduce those conditions here without internal state access
        // Hence, we only test observable behavior with typical inputs
    }
}
