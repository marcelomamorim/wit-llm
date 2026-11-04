/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getFloat(Object) returns correct float value from char field
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    static class TestClass {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("c");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(field);

        // getFloat should return the char value as float
        float f = accessor.getFloat(obj);
        if (f != (float) obj.c) {
            throw new AssertionError("Expected getFloat to return " + (float) obj.c + " but got " + f);
        }

        // Also test getChar returns the same char
        char ch = accessor.getChar(obj);
        if (ch != obj.c) {
            throw new AssertionError("Expected getChar to return " + obj.c + " but got " + ch);
        }

        // Test getFloat with null should throw IllegalArgumentException
        boolean threw = false;
        try {
            accessor.getFloat(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected getFloat(null) to throw IllegalArgumentException");
        }
    }
}
