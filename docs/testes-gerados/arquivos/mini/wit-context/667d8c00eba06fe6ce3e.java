/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getFloat(Object) behavior
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class TestObj {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestObj to = new TestObj();
        Field f = TestObj.class.getField("b");
        UnsafeByteFieldAccessorImpl acc = new UnsafeByteFieldAccessorImpl(f);

        // Test getFloat returns the byte value as float
        float val = acc.getFloat(to);
        if (val != (float) to.b) {
            throw new RuntimeException("getFloat returned " + val + ", expected " + to.b);
        }

        // Test getFloat throws IllegalArgumentException if obj is not instance of declaring class
        try {
            acc.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test getFloat throws IllegalArgumentException if obj is null
        try {
            acc.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
