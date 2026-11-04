/*
 * @test
 * @summary Test UnsafeQualifiedIntegerFieldAccessorImpl.getLong(Object) method
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        public int intField = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("intField");
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);

        // Test getLong returns the int value as long
        long longValue = accessor.getLong(testObj);
        if (longValue != testObj.intField) {
            throw new AssertionError("Expected " + testObj.intField + " but got " + longValue);
        }

        // Test getLong throws IllegalArgumentException when null object passed
        try {
            accessor.getLong(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getLong throws IllegalArgumentException when object of wrong type passed
        try {
            accessor.getLong(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
