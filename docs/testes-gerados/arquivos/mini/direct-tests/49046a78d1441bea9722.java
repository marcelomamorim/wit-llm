/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class TestObj {
        volatile byte value = 42;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getDeclaredField("value");
        UnsafeQualifiedByteFieldAccessorImpl accessor = new UnsafeQualifiedByteFieldAccessorImpl(field, false);

        // Test getDouble returns the byte value as double
        double d = accessor.getDouble(obj);
        if (d != 42.0) {
            throw new AssertionError("Expected 42.0 but got " + d);
        }

        // Test getDouble throws IllegalArgumentException when obj is null
        try {
            accessor.getDouble(null);
            throw new AssertionError("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getDouble throws IllegalArgumentException when obj is not instance of TestObj
        try {
            accessor.getDouble(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong obj type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
