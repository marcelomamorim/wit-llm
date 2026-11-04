/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getChar(Object) method
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    static class TestClass {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("c");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(field);

        // Test getChar returns the correct char value
        char value = accessor.getChar(testObj);
        if (value != 'A') {
            throw new RuntimeException("Expected 'A' but got: " + value);
        }

        // Test getChar throws IllegalArgumentException when null is passed
        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getChar throws IllegalArgumentException when wrong object type is passed
        try {
            accessor.getChar(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
