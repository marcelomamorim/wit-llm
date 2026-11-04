/* @test    @summary Test for jdk.internal.misc.Unsafe.setMemory method with various inputs    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        Object obj = new Object();
        long offset = 0L;
        long bytes = 10L;
        byte value = 1;

        // Test with valid parameters
        try {
            unsafe.setMemory(obj, offset, bytes, value);
            System.out.println("setMemory with valid parameters executed successfully.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception with valid parameters: " + t);
        }

        // Test with bytes == 0 (should return early, no exception)
        try {
            unsafe.setMemory(obj, offset, 0L, value);
            System.out.println("setMemory with bytes=0 executed successfully.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception with bytes=0: " + t);
        }

        // Test with null object and positive offset (should throw invalidInput())
        try {
            unsafe.setMemory(null, offset, bytes, value);
            System.err.println("Expected exception not thrown for null object.");
        } catch (Throwable t) {
            if (t.getClass().getName().equals("java.lang.RuntimeException")) {
                System.out.println("Caught expected exception for null object.");
            } else {
                System.err.println("Unexpected exception type for null object: " + t);
            }
        }

        // Test with null object and offset that triggers exception (simulate large offset)
        try {
            unsafe.setMemory(null, (1L << 33), bytes, value); // large offset
            System.err.println("Expected exception not thrown for large offset with null object.");
        } catch (Throwable t) {
            if (t.getClass().getName().equals("java.lang.RuntimeException")) {
                System.out.println("Caught expected exception for large offset with null object.");
            } else {
                System.err.println("Unexpected exception type for large offset: " + t);
            }
        }

        // Test with object and negative offset (should throw invalidInput())
        try {
            unsafe.setMemory(obj, -1L, bytes, value);
            System.err.println("Expected exception not thrown for negative offset.");
        } catch (Throwable t) {
            if (t.getClass().getName().equals("java.lang.RuntimeException")) {
                System.out.println("Caught expected exception for negative offset.");
            } else {
                System.err.println("Unexpected exception type for negative offset: " + t);
            }
        }

        // Test with object and large bytes value (simulate large size)
        try {
            unsafe.setMemory(obj, offset, (1L << 33), value); // large size
            System.err.println("Expected exception not thrown for large size.");
        } catch (Throwable t) {
            if (t.getClass().getName().equals("java.lang.RuntimeException")) {
                System.out.println("Caught expected exception for large size.");
            } else {
                System.err.println("Unexpected exception type for large size: " + t);
            }
        }
    }
}
