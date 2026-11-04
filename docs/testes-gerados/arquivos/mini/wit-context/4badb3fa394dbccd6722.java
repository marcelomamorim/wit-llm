/*
 * @test
 * @summary Test Unsafe.staticFieldOffset(Field) for null argument and valid field
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
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: Passing a valid static field should return a non-negative offset
        Field field = TestClass.class.getDeclaredField("staticField");
        long offset = unsafe.staticFieldOffset(field);
        if (offset < 0) {
            throw new Error("staticFieldOffset returned negative offset");
        }

        // Test 3: Passing a non-static field should also return a non-negative offset
        Field instanceField = TestClass.class.getDeclaredField("instanceField");
        long offset2 = unsafe.staticFieldOffset(instanceField);
        if (offset2 < 0) {
            throw new Error("staticFieldOffset returned negative offset for instance field");
        }

        System.out.println("All tests passed.");
    }

    static class TestClass {
        static int staticField = 42;
        int instanceField = 24;
    }
}
