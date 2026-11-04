/*
 * @test
 * @summary Test UnsafeStaticByteFieldAccessorImpl.getChar method throws expected IllegalArgumentException
 * @run main UnsafeStaticByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticByteFieldAccessorImpl;

public class UnsafeStaticByteFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeStaticByteFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeStaticByteFieldAccessorImpl accessor = new UnsafeStaticByteFieldAccessorImpl(dummyField);

            try {
                accessor.getChar(null);
                throw new RuntimeException("Expected IllegalArgumentException was not thrown by getChar");
            } catch (IllegalArgumentException e) {
                // Expected exception caught, test passes
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static byte dummyField = 0;
}
