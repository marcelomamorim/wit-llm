/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getByte(Object) method behavior
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

        // Test getByte returns the correct byte value
        byte val = accessor.getByte(obj);
        if (val != 42) {
            throw new RuntimeException("Expected 42 but got " + val);
        }

        // Test getByte throws IllegalArgumentException when obj is null
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test getByte throws IllegalArgumentException when obj is of wrong type
        try {
            accessor.getByte(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong obj type");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
