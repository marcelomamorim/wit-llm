/*
 * @test
 * @summary Test that getShort throws IllegalArgumentException as expected
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy field to pass to the constructor
        Field dummyField = Dummy.class.getDeclaredField("c");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(dummyField);

        try {
            accessor.getShort(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        char c = 'a';
    }
}
