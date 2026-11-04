/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getLong(Object) method
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {
    static class TestObj {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);

        // Test getLong returns the byte value as long
        long val = accessor.getLong(obj);
        if (val != 42L) {
            throw new RuntimeException("Expected 42L but got " + val);
        }

        // Test getLong throws IllegalArgumentException when obj is null
        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test getLong throws IllegalArgumentException when obj is wrong type
        try {
            accessor.getLong(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong obj type");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
