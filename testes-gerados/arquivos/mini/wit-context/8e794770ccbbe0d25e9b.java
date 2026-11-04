/*
 * @test
 * @summary Test UnsafeStaticBooleanFieldAccessorImpl.getLong throws IllegalArgumentException
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

            // Call getLong and expect IllegalArgumentException
            try {
                accessor.getLong(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getLong");
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
