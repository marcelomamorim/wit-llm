/*
 * @test
 * @summary Test getFloat method of UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class TestObj {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getField("c");
        UnsafeQualifiedCharacterFieldAccessorImpl accessor = new UnsafeQualifiedCharacterFieldAccessorImpl(field, false);

        // getFloat should return the char value as float
        float f = accessor.getFloat(obj);
        if (f != 'A') {
            throw new AssertionError("Expected getFloat to return 65.0f but got " + f);
        }

        // Changing the char field and testing again
        obj.c = 'Z';
        f = accessor.getFloat(obj);
        if (f != 'Z') {
            throw new AssertionError("Expected getFloat to return 90.0f but got " + f);
        }

        // Test with null object should throw IllegalArgumentException
        try {
            accessor.getFloat(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException e) {
            // expected
        }

        System.out.println("Test passed.");
    }
}
