/*
 * @test
 * @summary Test UnsafeStaticFloatFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeStaticFloatFieldAccessorImpl accessor = new UnsafeStaticFloatFieldAccessorImpl(dummyField);

        // Test getByte always throws IllegalArgumentException
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static float dummyField = 0.0f;
}
