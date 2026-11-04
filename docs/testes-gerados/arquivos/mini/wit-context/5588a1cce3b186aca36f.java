/*
 * @test
 * @summary Test getFloat(Object) method of UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestClass {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("c");

        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        // Test getFloat returns the char value as float
        float f = accessor.getFloat(testObj);
        if (f != 'A') {
            throw new RuntimeException("getFloat returned " + f + ", expected " + (float) 'A');
        }

        // Test getFloat throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for incompatible object");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
