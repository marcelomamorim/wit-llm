/*
 * @test
 * @summary Test UnsafeQualifiedCharacterFieldAccessorImpl.getLong(Object) behavior
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestObj {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        // Prepare a Field for the 'c' char field
        Field field = TestObj.class.getField("c");

        // Create an instance of UnsafeQualifiedCharacterFieldAccessorImpl
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        TestObj obj = new TestObj();

        // Test getLong returns the char value as long
        long val = accessor.getLong(obj);
        if (val != 'A') {
            throw new RuntimeException("getLong returned unexpected value: " + val);
        }

        // Test getLong throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getLong(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getLong throws IllegalArgumentException if obj is null
        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
