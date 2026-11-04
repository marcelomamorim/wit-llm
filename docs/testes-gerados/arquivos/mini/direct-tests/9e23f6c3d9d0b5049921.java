/*
 * @test
 * @summary Test UnsafeQualifiedCharacterFieldAccessorImpl.getChar(Object) method
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestClass {
        public volatile char testChar = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field field = TestClass.class.getDeclaredField("testChar");
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        // Test getChar returns the correct char value
        char c = accessor.getChar(obj);
        if (c != 'A') {
            throw new AssertionError("Expected 'A' but got: " + c);
        }

        // Test getChar throws IllegalArgumentException when null is passed
        try {
            accessor.getChar(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getChar throws IllegalArgumentException when wrong object type is passed
        try {
            accessor.getChar(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
