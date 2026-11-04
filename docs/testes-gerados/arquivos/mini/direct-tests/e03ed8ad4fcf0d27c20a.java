/*
 * @test
 * @summary Test UnsafeQualifiedLongFieldAccessorImpl.getLong(Object) method
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    static class TestClass {
        volatile long value = 123456789L;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(field, false);

        // Test getLong returns the correct long value
        long val = accessor.getLong(testObj);
        if (val != 123456789L) {
            throw new AssertionError("Expected 123456789L but got " + val);
        }

        // Test getLong throws IllegalArgumentException when obj is null
        try {
            accessor.getLong(null);
            throw new AssertionError("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getLong throws IllegalArgumentException when obj is of wrong type
        try {
            accessor.getLong(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong obj type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
