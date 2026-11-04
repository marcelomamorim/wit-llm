/*
 * @test
 * @summary Test UnsafeQualifiedStaticBooleanFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeQualifiedStaticBooleanFieldAccessorImpl accessor = new UnsafeQualifiedStaticBooleanFieldAccessorImpl(dummyField, false);

            // Call getChar and expect IllegalArgumentException
            try {
                accessor.getChar(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static boolean dummyField = false;
}
