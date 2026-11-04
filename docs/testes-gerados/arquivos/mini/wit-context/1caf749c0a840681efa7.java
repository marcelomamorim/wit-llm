/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getShort(Object) for correct delegation and exception on invalid object
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class TestClass {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field for 'b' in TestClass
        Field field = TestClass.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);

        TestClass instance = new TestClass();

        // Test getShort returns the byte value as short
        short val = accessor.getShort(instance);
        if (val != 42) {
            throw new RuntimeException("getShort returned " + val + ", expected 42");
        }

        // Test getShort throws IllegalArgumentException if object is not instance of declaring class
        try {
            accessor.getShort(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for invalid object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
