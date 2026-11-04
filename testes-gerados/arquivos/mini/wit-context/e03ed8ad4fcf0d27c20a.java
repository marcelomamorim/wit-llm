/*
 * @test
 * @summary Test UnsafeQualifiedLongFieldAccessorImpl.getLong(Object) for valid and invalid inputs
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    static class TestClass {
        volatile long value = 123456789L;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for TestClass.value
        Field field = TestClass.class.getDeclaredField("value");

        // Create instance of UnsafeQualifiedLongFieldAccessorImpl via reflection
        // The constructor is package-private, so we use reflection
        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(field, false);

        TestClass testObj = new TestClass();

        // Test getLong with correct object type
        long val = accessor.getLong(testObj);
        if (val != 123456789L) {
            throw new RuntimeException("getLong returned unexpected value: " + val);
        }

        // Test getLong with null object - expect IllegalArgumentException
        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test getLong with object of wrong type - expect IllegalArgumentException
        try {
            accessor.getLong(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
