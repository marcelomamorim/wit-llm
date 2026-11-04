/*
 * @test
 * @summary Test UnsafeQualifiedCharacterFieldAccessorImpl.getBoolean throws expected exception
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class Dummy {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        Field field = Dummy.class.getField("c");
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        // Test that getBoolean always throws IllegalArgumentException
        try {
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
