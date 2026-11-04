/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.staticFieldOffset(Field) method
 * @run main UnsafeWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test 1: Passing null should throw NullPointerException
        try {
            unsafe.staticFieldOffset(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: Passing a valid static field should return a non-negative offset
        Field field = TestClass.class.getDeclaredField("staticField");
        long offset = unsafe.staticFieldOffset(field);
        if (offset < 0) {
            throw new RuntimeException("staticFieldOffset returned negative offset: " + offset);
        }

        // Test 3: Passing an instance field should also return a non-negative offset
        Field instanceField = TestClass.class.getDeclaredField("instanceField");
        long offsetInstance = unsafe.staticFieldOffset(instanceField);
        if (offsetInstance < 0) {
            throw new RuntimeException("staticFieldOffset returned negative offset for instance field: " + offsetInstance);
        }
    }

    static class TestClass {
        static int staticField = 42;
        int instanceField = 24;
    }
}
