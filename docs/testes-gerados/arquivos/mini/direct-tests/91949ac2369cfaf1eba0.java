/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getDouble(Object) returns correct char value as double
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

        double val = accessor.getDouble(obj);
        if (val != 'A') {
            throw new AssertionError("Expected 'A' as double value, got: " + val);
        }

        // Change field value and test again
        obj.c = 'Z';
        val = accessor.getDouble(obj);
        if (val != 'Z') {
            throw new AssertionError("Expected 'Z' as double value, got: " + val);
        }

        // Test with null object should throw IllegalArgumentException
        boolean threw = false;
        try {
            accessor.getDouble(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when passing null to getDouble");
        }
    }
}
