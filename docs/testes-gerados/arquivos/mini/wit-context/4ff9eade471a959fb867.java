/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getFloat(Object) for correct behavior and exception
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {

    static class TestClass {
        public float f = 3.14f;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for TestClass.f
        Field field = TestClass.class.getField("f");
        UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test normal getFloat returns the correct value
        float val = accessor.getFloat(obj);
        if (val != obj.f) {
            throw new RuntimeException("getFloat returned wrong value: " + val);
        }

        // Test getFloat with null throws IllegalArgumentException
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getFloat with incompatible object type throws IllegalArgumentException
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for incompatible object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
