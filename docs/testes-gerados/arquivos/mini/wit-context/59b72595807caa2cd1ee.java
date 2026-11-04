/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getChar throws expected IllegalArgumentException
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeByteFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(dummyField);

            // Call getChar and expect IllegalArgumentException
            try {
                accessor.getChar(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

            System.out.println("Test passed.");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private byte dummyField;
}
