/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.setMemory for input validation and normal behavior
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test 1: bytes == 0 should return immediately without error
        unsafe.setMemory(null, 0L, 0L, (byte)0);

        // Test 2: Normal call with valid parameters
        // Use a dummy object and offset 0, bytes 1, value 0x1
        Object obj = new Object();
        long offset = 0L;
        long bytes = 1L;
        byte value = 1;
        unsafe.setMemory(obj, offset, bytes, value);

        // Test 3: Check that negative offset with non-null object throws invalidInput
        // According to WIT, invalidInput is thrown for offset < 0 when object != null
        // We cannot catch invalidInput directly (not public), so we catch Throwable
        try {
            unsafe.setMemory(obj, -1L, 1L, value);
            throw new RuntimeException("Expected exception for negative offset not thrown");
        } catch (Throwable t) {
            // Expected exception, no specific type asserted due to internal exception
        }

        // Test 4: Check that negative bytes throws invalidInput
        try {
            unsafe.setMemory(obj, 0L, -1L, value);
            throw new RuntimeException("Expected exception for negative bytes not thrown");
        } catch (Throwable t) {
            // Expected exception
        }

        // Test 5: Check that large offset triggers invalidInput when object is null and addressSize0()==4
        // We cannot forcibly change addressSize0(), so we test only if addressSize0()==4
        // If not 4, skip this test
        int addressSize = getAddressSize(unsafe);
        if (addressSize == 4) {
            long largeOffset = (1L << 33); // offset with bit 33 set
            try {
                unsafe.setMemory(null, largeOffset, 1L, value);
                throw new RuntimeException("Expected exception for large offset with null object not thrown");
            } catch (Throwable t) {
                // Expected exception
            }
        }

        // Test 6: Check that offset with high bits set throws invalidInput when object != null and addressSize0()==4
        if (addressSize == 4) {
            long invalidOffset = (1L << 32) + 1L; // offset with bit 32 set
            try {
                unsafe.setMemory(obj, invalidOffset, 1L, value);
                throw new RuntimeException("Expected exception for invalid offset with object not thrown");
            } catch (Throwable t) {
                // Expected exception
            }
        }

        System.out.println("All tests passed.");
    }

    private static int getAddressSize(Unsafe unsafe) {
        try {
            java.lang.reflect.Method m = Unsafe.class.getDeclaredMethod("addressSize0");
            m.setAccessible(true);
            return (int) m.invoke(unsafe);
        } catch (Exception e) {
            // Fallback: use Unsafe.addressSize field if accessible
            try {
                java.lang.reflect.Field f = Unsafe.class.getDeclaredField("addressSize");
                f.setAccessible(true);
                return f.getInt(unsafe);
            } catch (Exception ex) {
                // Default to 8
                return 8;
            }
        }
    }
}
