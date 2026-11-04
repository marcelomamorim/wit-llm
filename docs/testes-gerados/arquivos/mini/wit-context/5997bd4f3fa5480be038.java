/*
 * @test
 * @summary Test UnsafeQualifiedShortFieldAccessorImpl.getShort(Object) for valid and invalid inputs
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class TestClass {
        public short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for TestClass.value
        Field field = TestClass.class.getDeclaredField("value");

        // Create instance of UnsafeQualifiedShortFieldAccessorImpl with isReadOnly = false
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);

        TestClass obj = new TestClass();

        // Test getShort with valid object
        short val = accessor.getShort(obj);
        if (val != obj.value) {
            throw new RuntimeException("getShort returned unexpected value: " + val);
        }

        // Test getShort with null object - expect IllegalArgumentException
        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test getShort with object of incompatible class - expect IllegalArgumentException
        try {
            accessor.getShort(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for incompatible object class");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
