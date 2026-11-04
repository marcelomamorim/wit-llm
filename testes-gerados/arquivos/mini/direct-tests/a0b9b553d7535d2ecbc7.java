/*
 * @test
 * @summary Test UnsafeQualifiedByteFieldAccessorImpl.getByte(Object) method
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class TestObj {
        volatile byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getDeclaredField("b");
        UnsafeQualifiedByteFieldAccessorImpl accessor = new UnsafeQualifiedByteFieldAccessorImpl(field, false);

        // Test getByte returns the correct byte value
        byte value = accessor.getByte(obj);
        if (value != 42) {
            throw new AssertionError("Expected 42 but got " + value);
        }

        // Test getByte throws IllegalArgumentException when obj is null
        try {
            accessor.getByte(null);
            throw new AssertionError("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getByte throws IllegalArgumentException when obj is not instance of declaring class
        try {
            accessor.getByte(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong obj type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
