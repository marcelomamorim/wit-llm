/*
 * @test
 * @summary Test UnsafeQualifiedByteFieldAccessorImpl.getByte(Object) for correct behavior and IllegalArgumentException on invalid object
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class TestClass {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field for TestClass.b
        Field field = TestClass.class.getDeclaredField("b");

        // Create instance of UnsafeQualifiedByteFieldAccessorImpl via reflection since constructor is package-private
        UnsafeQualifiedByteFieldAccessorImpl accessor = new UnsafeQualifiedByteFieldAccessorImpl(field, false);

        TestClass obj = new TestClass();

        // Test getByte returns the correct byte value
        byte value = accessor.getByte(obj);
        if (value != obj.b) {
            throw new AssertionError("Expected byte value " + obj.b + " but got " + value);
        }

        // Test getByte throws IllegalArgumentException when passed an object of incompatible class
        try {
            accessor.getByte(new Object());
            throw new AssertionError("Expected IllegalArgumentException for incompatible object class");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        // Test getByte throws IllegalArgumentException when passed null
        try {
            accessor.getByte(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
