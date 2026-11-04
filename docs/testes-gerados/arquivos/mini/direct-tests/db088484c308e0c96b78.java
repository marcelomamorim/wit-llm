/*
 * @test
 * @summary Test that getShort(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor
        Field dummyField = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(dummyField, false);

        try {
            accessor.getShort(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    @SuppressWarnings("unused")
    private char dummyField = 'a';
}
