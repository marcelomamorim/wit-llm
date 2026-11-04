/*
 * @test
 * @summary Test getDouble(Object) method of UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestClass {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for TestClass.c
        Field field = TestClass.class.getField("c");

        // Create UnsafeQualifiedCharacterFieldAccessorImpl instance
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        TestClass obj = new TestClass();

        // Test getDouble returns the char value as double
        double val = accessor.getDouble(obj);
        if (val != 'A') {
            throw new RuntimeException("getDouble returned unexpected value: " + val);
        }

        // Test getDouble throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getDouble throws IllegalArgumentException if obj is null
        try {
            accessor.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
