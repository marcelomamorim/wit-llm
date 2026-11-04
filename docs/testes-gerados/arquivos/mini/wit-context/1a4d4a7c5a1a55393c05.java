/*
 * @test
 * @summary Test UnsafeStaticCharacterFieldAccessorImpl.getByte throws IllegalArgumentException
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

            // Call getByte and expect IllegalArgumentException
            try {
                accessor.getByte(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    @SuppressWarnings("unused")
    private static char dummyField = 'a';
}
