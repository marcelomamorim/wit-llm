/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getInt(Object) behavior
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

        // Normal case: getInt returns the char value as int
        int val = accessor.getInt(testObj);
        if (val != 'A') {
            throw new RuntimeException("Expected getInt to return 'A' as int, got: " + val);
        }

        // IllegalArgumentException case: pass an object of incompatible class
        try {
            accessor.getInt(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for incompatible object class");
        } catch (IllegalArgumentException iae) {
            // Expected
        }

        System.out.println("Test passed.");
    }
}
