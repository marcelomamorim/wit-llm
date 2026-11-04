/*
 * @test
 * @summary Test UnsafeQualifiedIntegerFieldAccessorImpl.getChar throws expected exception
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedIntegerFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeQualifiedIntegerFieldAccessorImpl acc = new UnsafeQualifiedIntegerFieldAccessorImpl(dummyField, false);

            // The getChar method always throws IllegalArgumentException
            try {
                acc.getChar(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain a Field instance for constructor
    @SuppressWarnings("unused")
    private int dummyField;
}
