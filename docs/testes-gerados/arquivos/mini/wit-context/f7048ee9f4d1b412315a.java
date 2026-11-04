/*
 * @test
 * @summary Test UnsafeStaticBooleanFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeStaticBooleanFieldAccessorImpl accessor = new UnsafeStaticBooleanFieldAccessorImpl(dummyField);

            // Call getInt and expect IllegalArgumentException
            try {
                accessor.getInt(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getInt");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    private static boolean dummyField = false;
}
