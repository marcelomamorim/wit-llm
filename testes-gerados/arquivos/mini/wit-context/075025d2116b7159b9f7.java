/*
 * @test
 * @summary Test UnsafeStaticBooleanFieldAccessorImpl.getShort throws IllegalArgumentException
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

            // Call getShort and expect IllegalArgumentException
            try {
                accessor.getShort(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static short dummyField = 0;
}
