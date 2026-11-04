/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.staticFieldBase(Field) method
 * @run main UnsafeWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test 1: Passing null should throw NullPointerException
        try {
            unsafe.staticFieldBase(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: Passing a valid static field should return a non-null base object
        Field staticField = TestClass.class.getDeclaredField("staticInt");
        Object base = unsafe.staticFieldBase(staticField);
        if (base == null) {
            throw new RuntimeException("staticFieldBase returned null for static field");
        }

        // Test 3: Passing a valid instance field should return a non-null base object
        Field instanceField = TestClass.class.getDeclaredField("instanceInt");
        Object base2 = unsafe.staticFieldBase(instanceField);
        if (base2 == null) {
            throw new RuntimeException("staticFieldBase returned null for instance field");
        }

        // Test 4: Passing a field from Object class
        Field objectField = Object.class.getDeclaredField("hashCode");
        Object base3 = unsafe.staticFieldBase(objectField);
        if (base3 == null) {
            throw new RuntimeException("staticFieldBase returned null for Object field");
        }

        System.out.println("All tests passed.");
    }

    static class TestClass {
        static int staticInt = 42;
        int instanceInt = 24;
    }
}
