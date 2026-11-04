/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getDouble(Object) method
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class TestClass {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);

        // getDouble should return the byte value as double
        double val = accessor.getDouble(obj);
        if (val != 42.0) {
            throw new AssertionError("Expected 42.0 but got " + val);
        }

        // Test with null object should throw IllegalArgumentException
        boolean threw = false;
        try {
            accessor.getDouble(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for null object");
        }
    }
}
