/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getShort(Object) method
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class TestClass {
        public byte b = 123;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);

        // getShort should return the byte value as short
        short val = accessor.getShort(testObj);
        if (val != testObj.b) {
            throw new AssertionError("Expected getShort to return " + testObj.b + " but got " + val);
        }

        // Test with null object should throw IllegalArgumentException
        try {
            accessor.getShort(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
