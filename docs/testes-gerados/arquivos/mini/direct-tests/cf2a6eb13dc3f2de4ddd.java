/*
 * @test
 * @summary Test UnsafeStaticCharacterFieldAccessorImpl.getShort throws IllegalArgumentException
 * @run main UnsafeStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticCharacterFieldAccessorImpl;

public class UnsafeStaticCharacterFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static char field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("staticChar");
        UnsafeStaticCharacterFieldAccessorImpl accessor = new UnsafeStaticCharacterFieldAccessorImpl(field);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static char staticChar = 'a';
    }
}
