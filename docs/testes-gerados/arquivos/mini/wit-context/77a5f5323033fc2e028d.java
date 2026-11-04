/*
 * @test
 * @summary Test that getBoolean always throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedStaticShortFieldAccessorImplWitupTest.class.getDeclaredFields()[0];
            UnsafeQualifiedStaticShortFieldAccessorImpl accessor = new UnsafeQualifiedStaticShortFieldAccessorImpl(dummyField, false);

            try {
                accessor.getBoolean(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }

        } catch (Exception e) {
            throw new RuntimeException("Test failed with unexpected exception", e);
        }
    }

    // Dummy field to obtain a Field instance
    @SuppressWarnings("unused")
    private static short dummyField = 0;
}
