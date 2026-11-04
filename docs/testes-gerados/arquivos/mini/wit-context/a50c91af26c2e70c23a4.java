/*
 * @test
 * @summary Test getFloat(Object) method of UnsafeQualifiedIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        public int intField = 42;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("intField");
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);

        // Test getFloat returns the int value as float
        float floatValue = accessor.getFloat(testObj);
        if (floatValue != 42.0f) {
            throw new AssertionError("Expected 42.0f but got " + floatValue);
        }

        // Test getFloat throws IllegalArgumentException if object is not instance of declaring class
        try {
            accessor.getFloat(new Object());
            throw new AssertionError("Expected IllegalArgumentException for incompatible object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
