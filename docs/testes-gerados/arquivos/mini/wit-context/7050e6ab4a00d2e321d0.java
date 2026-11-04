/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getShort throws expected IllegalArgumentException
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeCharacterFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(dummyField);

            // The method getShort always throws IllegalArgumentException
            try {
                accessor.getShort(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private char dummyField = 'a';
}
