/*
 * @test
 * @summary Test UnsafeQualifiedByteFieldAccessorImpl.getDouble(Object) behavior
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

        UnsafeQualifiedByteFieldAccessorImpl acc = new UnsafeQualifiedByteFieldAccessorImpl(f, false);

        // Normal case: getDouble returns the byte value as double
        double d = acc.getDouble(to);
        if (d != 42.0) {
            throw new RuntimeException("Expected 42.0 but got " + d);
        }

        // Test with null object: should throw IllegalArgumentException
        try {
            acc.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test with incompatible object type: should throw IllegalArgumentException
        try {
            acc.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for incompatible obj");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
