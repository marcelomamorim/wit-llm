/*
 * @test
 * @summary Test UnsafeQualifiedStaticIntegerFieldAccessorImpl.getByte throws expected IllegalArgumentException
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Instantiate the class under test
        UnsafeQualifiedStaticIntegerFieldAccessorImpl accessor = new UnsafeQualifiedStaticIntegerFieldAccessorImpl(dummyField, false);

        // Test getByte(Object) throws IllegalArgumentException as per current implementation
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown by getByte(null)");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }

        // Also test getByte with non-null argument to confirm consistent behavior
        try {
            accessor.getByte(new Object());
            throw new RuntimeException("Expected IllegalArgumentException was not thrown by getByte(non-null)");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private int dummyField;
}
