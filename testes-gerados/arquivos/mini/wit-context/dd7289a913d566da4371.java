/*
 * @test
 * @summary Test UnsafeQualifiedByteFieldAccessorImpl.getFloat(Object) behavior
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class TestObj {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestObj to = new TestObj();
        Field f = TestObj.class.getField("b");

        UnsafeQualifiedByteFieldAccessorImpl accessor = new UnsafeQualifiedByteFieldAccessorImpl(f, false);

        // Normal case: getFloat returns the byte value as float
        float val = accessor.getFloat(to);
        if (val != 42.0f) {
            throw new RuntimeException("Expected 42.0f but got " + val);
        }

        // Test with null object: should throw IllegalArgumentException
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test with incompatible object type: should throw IllegalArgumentException
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for incompatible object");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
