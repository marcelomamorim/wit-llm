/*
 * @test
 * @summary Test UnsafeQualifiedIntegerFieldAccessorImpl.getInt(Object) method
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        volatile int intField = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getDeclaredField("intField");
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);

        // Test getInt returns the correct int value
        int value = accessor.getInt(testObj);
        if (value != 12345) {
            throw new AssertionError("Expected 12345 but got " + value);
        }

        // Test getInt throws IllegalArgumentException when obj is null
        try {
            accessor.getInt(null);
            throw new AssertionError("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getInt throws IllegalArgumentException when obj is of wrong type
        try {
            accessor.getInt(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong obj type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
