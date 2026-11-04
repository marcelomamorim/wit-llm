/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class TestObj {
        volatile int value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getDeclaredField("value");
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);

        // Test getDouble returns the int value as double
        double d = accessor.getDouble(obj);
        if (d != 12345.0) {
            throw new AssertionError("Expected 12345.0 but got " + d);
        }

        // Test getDouble throws IllegalArgumentException if null passed
        try {
            accessor.getDouble(null);
            throw new AssertionError("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getDouble throws IllegalArgumentException if wrong object type passed
        try {
            accessor.getDouble(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
