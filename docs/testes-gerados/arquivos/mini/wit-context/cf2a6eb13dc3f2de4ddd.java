/*
 * @test
 * @summary Test UnsafeStaticCharacterFieldAccessorImpl.getShort throws IllegalArgumentException
 * @run main UnsafeStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticCharacterFieldAccessorImpl;

public class UnsafeStaticCharacterFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeStaticCharacterFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeStaticCharacterFieldAccessorImpl accessor = new UnsafeStaticCharacterFieldAccessorImpl(dummyField);

            // Call getShort and expect IllegalArgumentException
            try {
                accessor.getShort(null);
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
    private static char dummyField = 'a';
}
