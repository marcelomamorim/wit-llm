/*
 * @test
 * @summary Test UnsafeQualifiedFloatFieldAccessorImpl.getFloat(Object) for valid and invalid inputs
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    static class TestClass {
        volatile float f = 123.456f;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for TestClass.f
        Field field = TestClass.class.getDeclaredField("f");

        // Create instance of UnsafeQualifiedFloatFieldAccessorImpl via reflection
        // The constructor is package-private, so we use reflection
        UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(field, false);

        TestClass obj = new TestClass();

        // Test getFloat with correct object type
        float val = accessor.getFloat(obj);
        if (val != obj.f) {
            throw new RuntimeException("getFloat returned wrong value: " + val);
        }

        // Test getFloat with null object - expect IllegalArgumentException
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test getFloat with object of wrong type - expect IllegalArgumentException
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
