/*
 * @test
 * @summary Test getFloat(Object) method of UnsafeCharacterFieldAccessorImpl
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

        // Test getFloat returns the char value as float
        float val = accessor.getFloat(testObj);
        if (val != 'A') {
            throw new RuntimeException("getFloat returned " + val + ", expected " + (float) 'A');
        }

        // Test getFloat throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for invalid object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test getFloat throws IllegalArgumentException if obj is null
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
