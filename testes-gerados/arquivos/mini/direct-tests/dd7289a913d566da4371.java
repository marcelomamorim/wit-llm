/*
 * @test
 * @summary Test getFloat method of UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class TestObj {
        volatile byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getDeclaredField("b");
        UnsafeQualifiedByteFieldAccessorImpl accessor = new UnsafeQualifiedByteFieldAccessorImpl(field, false);

        // getFloat should return the byte value as float
        float f = accessor.getFloat(obj);
        if (f != 42.0f) {
            throw new AssertionError("Expected 42.0f but got " + f);
        }

        // Test with different byte value
        obj.b = -10;
        f = accessor.getFloat(obj);
        if (f != -10.0f) {
            throw new AssertionError("Expected -10.0f but got " + f);
        }

        // Test with null object should throw IllegalArgumentException
        try {
            accessor.getFloat(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
