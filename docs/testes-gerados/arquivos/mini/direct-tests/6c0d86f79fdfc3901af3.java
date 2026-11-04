/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.objectFieldOffset(Field) method
 * @run main UnsafeWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test 1: Passing null should throw NullPointerException
        try {
            unsafe.objectFieldOffset(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: Passing a valid Field should return a non-negative offset
        Field field = Sample.class.getDeclaredField("value");
        long offset = unsafe.objectFieldOffset(field);
        if (offset < 0) {
            throw new RuntimeException("objectFieldOffset returned negative offset: " + offset);
        }

        // Test 3: Passing a Field from a primitive type wrapper class
        Field intField = Integer.class.getDeclaredField("value");
        long intOffset = unsafe.objectFieldOffset(intField);
        if (intOffset < 0) {
            throw new RuntimeException("objectFieldOffset returned negative offset for Integer.value: " + intOffset);
        }
    }

    static class Sample {
        int value;
    }
}
