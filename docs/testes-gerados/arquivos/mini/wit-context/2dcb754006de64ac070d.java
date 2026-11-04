/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getChar(Object) for correct behavior and IllegalArgumentException on invalid object
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;
import jdk.internal.reflect.UnsafeFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    static class TestClass {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        // Setup: get Field for TestClass.c
        Field field = TestClass.class.getField("c");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test normal getChar returns the correct char value
        char val = accessor.getChar(obj);
        if (val != 'A') {
            throw new AssertionError("Expected 'A', got: " + val);
        }

        // Test getChar throws IllegalArgumentException when passed object of wrong type
        try {
            accessor.getChar(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getChar throws IllegalArgumentException when passed null
        try {
            accessor.getChar(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
