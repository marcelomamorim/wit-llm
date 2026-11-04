/*
 * @test
 * @summary Test UnsafeQualifiedByteFieldAccessorImpl.getShort(Object) behavior
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

        // Normal case: getShort returns the byte value as short
        short val = acc.getShort(to);
        if (val != 42) {
            throw new RuntimeException("Expected 42 but got " + val);
        }

        // Test with null object: expect IllegalArgumentException due to ensureObj
        try {
            acc.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test with incompatible object type: expect IllegalArgumentException
        try {
            acc.getShort(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for incompatible obj");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
