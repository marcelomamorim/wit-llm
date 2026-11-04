/* @test    @summary Test getFloat method of UnsafeByteFieldAccessorImpl class    @run main UnsafeByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsByteValue();
        testGetFloatWithNullObject();
        testGetFloatWithInvalidObject();
    }

    private static void testGetFloatReturnsByteValue() throws Exception {
        class Dummy {
            public byte value = 42;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
        float result = accessor.getFloat(dummy);
        if (result != 42.0f) {
            throw new AssertionError("Expected 42.0f, but got " + result);
        }
    }

    private static void testGetFloatWithNullObject() {
        try {
            Field field = String.class.getField("value");
            jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
            accessor.getFloat(null);
            throw new AssertionError("Expected NullPointerException for null object");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testGetFloatWithInvalidObject() throws Exception {
        class Dummy {
            public byte value = 10;
        }
        Dummy dummy = new Dummy();
        Field field = String.class.getField("value");
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
        try {
            accessor.getFloat(dummy);
            throw new AssertionError("Expected IllegalArgumentException for invalid object type");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
