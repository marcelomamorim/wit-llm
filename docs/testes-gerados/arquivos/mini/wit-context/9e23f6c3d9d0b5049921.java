/*
 * @test
 * @summary Test UnsafeQualifiedCharacterFieldAccessorImpl.getChar(Object) for correct behavior and IllegalArgumentException on invalid object
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestClass {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field for TestClass.c
        Field field = TestClass.class.getDeclaredField("c");

        // Create instance of UnsafeQualifiedCharacterFieldAccessorImpl
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        TestClass obj = new TestClass();

        // Test getChar returns the correct char value
        char value = accessor.getChar(obj);
        if (value != 'A') {
            throw new RuntimeException("getChar returned wrong value: " + value);
        }

        // Test getChar throws IllegalArgumentException when passed an incompatible object
        try {
            accessor.getChar(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown for incompatible object");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        // Test getChar throws IllegalArgumentException when passed null
        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null object");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
