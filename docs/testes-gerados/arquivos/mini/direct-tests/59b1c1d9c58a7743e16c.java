/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getLong(Object) returns correct char value as long
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

        long val = accessor.getLong(obj);
        if (val != 'A') {
            throw new AssertionError("Expected 'A' as long value, got: " + val);
        }

        // Change field value and test again
        obj.c = 'Z';
        val = accessor.getLong(obj);
        if (val != 'Z') {
            throw new AssertionError("Expected 'Z' as long value, got: " + val);
        }

        // Test with null object - should throw IllegalArgumentException
        boolean threw = false;
        try {
            accessor.getLong(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when passing null to getLong");
        }
    }
}
