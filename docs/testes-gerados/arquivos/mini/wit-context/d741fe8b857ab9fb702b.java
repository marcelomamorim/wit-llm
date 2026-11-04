/*
 * @test
 * @summary Test UnsafeStaticFloatFieldAccessorImpl.getShort throws expected IllegalArgumentException
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeStaticFloatFieldAccessorImpl accessor = new UnsafeStaticFloatFieldAccessorImpl(dummyField);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown by getShort");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static short dummyField = 0;
}
