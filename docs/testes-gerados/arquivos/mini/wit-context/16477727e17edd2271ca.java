/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getLong(Object) for correct behavior and exception
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class TestClass {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);

        // Test getLong returns the byte value as long
        long val = accessor.getLong(testObj);
        if (val != 42L) {
            throw new RuntimeException("getLong returned " + val + ", expected 42");
        }

        // Test getLong throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getLong(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test getLong throws IllegalArgumentException if obj is null
        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
