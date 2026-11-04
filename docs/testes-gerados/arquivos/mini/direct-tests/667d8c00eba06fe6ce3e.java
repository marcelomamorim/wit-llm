/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getFloat(Object) returns correct float value from byte field
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
        Field f = TestObj.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(f);

        // getFloat should return the byte value as float
        float val = accessor.getFloat(obj);
        if (val != (float) obj.b) {
            throw new AssertionError("Expected getFloat to return " + obj.b + " but got " + val);
        }

        // Changing the byte field and testing again
        obj.b = -10;
        val = accessor.getFloat(obj);
        if (val != (float) obj.b) {
            throw new AssertionError("Expected getFloat to return " + obj.b + " but got " + val);
        }

        // Test with null object should throw IllegalArgumentException
        boolean threw = false;
        try {
            accessor.getFloat(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected getFloat(null) to throw IllegalArgumentException");
        }
    }
}
