/*
 * @test
 * @summary Test UnsafeQualifiedFloatFieldAccessorImpl.getDouble(Object) behavior
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    static class TestClass {
        volatile float f = 123.456f;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getDeclaredField("f");

        UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(field, false);

        // Test getDouble returns the float value as double
        double val = accessor.getDouble(testObj);
        if (val != (double) testObj.f) {
            throw new RuntimeException("getDouble did not return expected value: " + val);
        }

        // Test getDouble throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
