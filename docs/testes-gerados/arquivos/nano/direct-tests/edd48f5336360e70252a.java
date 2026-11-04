/* @test    @summary Test getInt method of UnsafeByteFieldAccessorImpl class    @run main UnsafeByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntReturnsByteValue();
        testGetIntWithNullObject();
    }

    static void testGetIntReturnsByteValue() throws Exception {
        class Dummy {
            public byte value = 42;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
        int result = accessor.getInt(dummy);
        if (result != 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }

    static void testGetIntWithNullObject() throws Exception {
        class Dummy {
            public byte value = 0;
        }
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
        try {
            accessor.getInt(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
