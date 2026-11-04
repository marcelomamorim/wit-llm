/*
 * @test
 * @summary Test UnsafeCharacterFieldAccessorImpl.getInt(Object) returns correct int value from char field
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

        int intValue = accessor.getInt(obj);
        int expected = 'A';

        if (intValue != expected) {
            throw new AssertionError("Expected int value " + expected + " but got " + intValue);
        }

        // Also test that getInt throws IllegalArgumentException if null passed
        try {
            accessor.getInt(null);
            throw new AssertionError("Expected IllegalArgumentException when passing null to getInt");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
