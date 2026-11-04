/*
 * @test
 * @summary Test getBoolean method throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static char field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("c");
        UnsafeQualifiedStaticCharacterFieldAccessorImpl accessor = new UnsafeQualifiedStaticCharacterFieldAccessorImpl(field, false);

        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static char c = 'a';
    }
}
