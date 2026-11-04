/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getDouble(Object) for correct behavior and exception on invalid object
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class TestObj {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        // Setup: get Field for byte b in TestObj
        Field field = TestObj.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);

        TestObj obj = new TestObj();

        // Test getDouble returns the byte value as double
        double val = accessor.getDouble(obj);
        if (val != (double) obj.b) {
            throw new AssertionError("getDouble returned " + val + ", expected " + (double) obj.b);
        }

        // Test getDouble throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new AssertionError("Expected IllegalArgumentException for invalid object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getDouble throws IllegalArgumentException if obj is null
        try {
            accessor.getDouble(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
