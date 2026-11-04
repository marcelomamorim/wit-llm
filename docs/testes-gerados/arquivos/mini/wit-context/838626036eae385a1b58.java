/*
 * @test
 * @summary Test UnsafeQualifiedByteFieldAccessorImpl.getLong(Object) behavior
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

        // Normal case: getLong returns the byte value as long
        long val = acc.getLong(to);
        if (val != 42L) {
            throw new RuntimeException("Expected 42L but got " + val);
        }

        // IllegalArgumentException expected if obj is not instance of declaring class
        try {
            acc.getLong(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // IllegalArgumentException expected if obj is null
        try {
            acc.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
