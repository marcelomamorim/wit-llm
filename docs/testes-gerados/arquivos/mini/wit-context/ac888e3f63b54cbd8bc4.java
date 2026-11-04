/*
 * @test
 * @summary Test getShort method throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredFields()[0];
            UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new UnsafeQualifiedStaticObjectFieldAccessorImpl(dummyField, false);

            // Call getShort and expect IllegalArgumentException
            try {
                accessor.getShort(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

        } catch (Exception e) {
            throw new RuntimeException("Test failed with unexpected exception", e);
        }
    }

    // Dummy field to obtain a Field instance
    @SuppressWarnings("unused")
    private static int dummyField;
}
