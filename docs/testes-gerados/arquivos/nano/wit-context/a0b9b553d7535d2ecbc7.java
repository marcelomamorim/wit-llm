/*
 * @test
 * @summary Test for getByte method in UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteWithInvalidObject();
        testGetByteWithValidObject();
    }

    static void testGetByteWithInvalidObject() throws Exception {
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Ignore, as we just need a Field object
        }
        // Create an instance of UnsafeQualifiedByteFieldAccessorImpl with dummy field
        jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl(dummyField, false);
        Object invalidObj = new Object();
        try {
            accessor.getByte(invalidObj);
            // Expecting an IllegalArgumentException due to class mismatch
            throw new AssertionError("Expected IllegalArgumentException for invalid object")
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testGetByteWithValidObject() throws Exception {
        class TestClass {
            public byte value = 42;
        }
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl(field, false);
        byte result = accessor.getByte(testObj);
        if (result != 42) {
            throw new AssertionError("Expected 42, got " + result);
        }
    }
}
