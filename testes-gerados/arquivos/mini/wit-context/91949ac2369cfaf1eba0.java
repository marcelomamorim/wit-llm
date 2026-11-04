/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getDouble(Object) behavior
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

        // Test getDouble returns the char value as double
        double val = accessor.getDouble(testObj);
        if (val != 'A') {
            throw new AssertionError("Expected getDouble to return char value as double, got: " + val);
        }

        // Test getDouble throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new AssertionError("Expected IllegalArgumentException when passing incompatible object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getDouble throws IllegalArgumentException if obj is null
        try {
            accessor.getDouble(null);
            throw new AssertionError("Expected IllegalArgumentException when passing null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
