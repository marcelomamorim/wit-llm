/*
 * @test
 * @summary Test UnsafeQualifiedIntegerFieldAccessorImpl.getInt(Object) for correct behavior and IllegalArgumentException on invalid object
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        public int testField = 42;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for testField
        Field field = TestClass.class.getDeclaredField("testField");

        // Create UnsafeQualifiedIntegerFieldAccessorImpl instance via reflection workaround
        // The constructor is package-private, so instantiate via reflection
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);

        TestClass obj = new TestClass();

        // Test getInt returns the correct int value
        int value = accessor.getInt(obj);
        if (value != 42) {
            throw new AssertionError("Expected 42 but got " + value);
        }

        // Test getInt throws IllegalArgumentException when passed an object of incompatible class
        try {
            accessor.getInt(new Object());
            throw new AssertionError("Expected IllegalArgumentException for incompatible object");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        // Test getInt throws IllegalArgumentException when passed null
        try {
            accessor.getInt(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
