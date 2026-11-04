/*
 * @test
 * @summary Test getDouble(Object) method of UnsafeFloatFieldAccessorImpl
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {

    static class TestClass {
        public float f = 1.5f;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("f");
        UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(field);

        // Test normal behavior: getDouble returns the float value as double
        double val = accessor.getDouble(testObj);
        if (val != 1.5d) {
            throw new AssertionError("Expected 1.5 but got " + val);
        }

        // Test IllegalArgumentException when passing incompatible object
        try {
            accessor.getDouble(new Object());
            throw new AssertionError("Expected IllegalArgumentException for incompatible object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test IllegalArgumentException when passing null
        try {
            accessor.getDouble(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
