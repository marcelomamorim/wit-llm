/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getInt(Object) method
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
        Field field = TestObj.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);

        // Test getInt returns the byte value as int
        int intValue = accessor.getInt(obj);
        if (intValue != 42) {
            throw new RuntimeException("Expected 42 but got " + intValue);
        }

        // Test getInt throws IllegalArgumentException when null passed
        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getInt throws IllegalArgumentException when wrong object type passed
        try {
            accessor.getInt(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
