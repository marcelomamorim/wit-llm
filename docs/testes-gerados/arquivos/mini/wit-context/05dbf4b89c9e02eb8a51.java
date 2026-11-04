/*
 * @test
 * @summary Test UnsafeStaticFloatFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeStaticFloatFieldAccessorImpl accessor = new UnsafeStaticFloatFieldAccessorImpl(dummyField);

            // Call getInt and expect IllegalArgumentException
            try {
                accessor.getInt(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static int dummyField = 0;
}
