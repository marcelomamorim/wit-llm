/*
 * @test
 * @summary Test UnsafeQualifiedObjectFieldAccessorImpl.get(Object) method
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    static class TestClass {
        public Object field = "testValue";
    }

    public static void main(String[] args) throws Exception {
        TestClass testInstance = new TestClass();
        Field field = TestClass.class.getField("field");

        UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(field, false);

        // Test get with valid object
        Object value = accessor.get(testInstance);
        if (!"testValue".equals(value)) {
            throw new RuntimeException("Expected 'testValue' but got: " + value);
        }

        // Test get with null object - expect IllegalArgumentException
        try {
            accessor.get(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test get with object of wrong type - expect IllegalArgumentException
        try {
            accessor.get(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
