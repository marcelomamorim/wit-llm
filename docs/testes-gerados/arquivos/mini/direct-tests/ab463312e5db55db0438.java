/*
 * @test
 * @summary Test getBoolean method throws IllegalArgumentException
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor
        Field field = Dummy.class.getDeclaredField("c");
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        try {
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Test passed
        }
    }

    static class Dummy {
        public char c = 'a';
    }
}
