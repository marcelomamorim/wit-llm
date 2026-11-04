/* @test    @summary Test getAndSetShortAcquire method for correct behavior    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testGetAndSetShortAcquire();
    }

    private static void testGetAndSetShortAcquire() {
        Unsafe unsafe = Unsafe.getUnsafe();
        class TestObject {
            public short value = 42;
        }
        TestObject obj = new TestObject();
        try {
            // Obtain the offset of the 'value' field
            java.lang.reflect.Field field = TestObject.class.getField("value");
            long offset = unsafe.objectFieldOffset(field);
            // Set a new value atomically and get the previous value
            short previous = unsafe.getAndSetShortAcquire(obj, offset, (short) 100);
            // Verify that the previous value was 42
            if (previous != 42) {
                throw new AssertionError("Expected previous value 42, but got " + previous);
            }
            // Verify that the current value is now 100
            short current = unsafe.getShortAcquire(obj, offset);
            if (current != 100) {
                throw new AssertionError("Expected current value 100, but got " + current);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
