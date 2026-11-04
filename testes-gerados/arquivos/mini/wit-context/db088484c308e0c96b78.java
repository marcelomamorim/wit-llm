/*
 * @test
 * @summary Test getShort method of UnsafeQualifiedCharacterFieldAccessorImpl throws expected exception
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

            // Call getShort and expect IllegalArgumentException
            try {
                accessor.getShort(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    @SuppressWarnings("unused")
    private char dummyField = 'a';
}
