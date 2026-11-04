/*
 * @test
 * @summary Test UnsafeQualifiedCharacterFieldAccessorImpl.getByte throws expected IllegalArgumentException
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(dummyField, false);

            // Call getByte and expect IllegalArgumentException
            try {
                accessor.getByte(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private char dummyField = 'a';
}
