/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getByte(Object) for correct behavior and IllegalArgumentException on invalid object
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class TestClass {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field for TestClass.b
        Field field = TestClass.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test getByte returns the correct byte value
        byte val = accessor.getByte(obj);
        if (val != obj.b) {
            throw new RuntimeException("getByte returned " + val + ", expected " + obj.b);
        }

        // Test getByte throws IllegalArgumentException when passed an object of wrong type
        try {
            accessor.getByte(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getByte throws IllegalArgumentException when passed null
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
