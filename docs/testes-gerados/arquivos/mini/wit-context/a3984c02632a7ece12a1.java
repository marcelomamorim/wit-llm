/*
 * @test
 * @summary Test UnsafeQualifiedObjectFieldAccessorImpl.get(Object) for valid and invalid inputs
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;
import jdk.internal.reflect.UnsafeFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    static class Dummy {
        public Object value = "test";
    }

    public static void main(String[] args) throws Exception {
        // Prepare a Field instance for Dummy.value
        Field field = Dummy.class.getField("value");

        // Create an instance of UnsafeQualifiedObjectFieldAccessorImpl via UnsafeFieldAccessorImpl
        // UnsafeQualifiedObjectFieldAccessorImpl has no public constructor, but UnsafeFieldAccessorImpl is package-private
        // and UnsafeQualifiedObjectFieldAccessorImpl constructor is package-private, so we can instantiate directly here
        UnsafeQualifiedObjectFieldAccessorImpl accessor = new UnsafeQualifiedObjectFieldAccessorImpl(field, false);

        Dummy dummy = new Dummy();

        // Test normal get returns the correct value
        Object val = accessor.get(dummy);
        if (!"test".equals(val)) {
            throw new AssertionError("Expected 'test' but got: " + val);
        }

        // Test get with null throws IllegalArgumentException
        try {
            accessor.get(null);
            throw new AssertionError("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test get with incompatible object type throws IllegalArgumentException
        try {
            accessor.get(new Object());
            throw new AssertionError("Expected IllegalArgumentException for incompatible obj type");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
