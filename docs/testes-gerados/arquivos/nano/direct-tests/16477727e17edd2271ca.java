/* @test    @summary Test getLong method of UnsafeByteFieldAccessorImpl for correct behavior and exception handling    @run main UnsafeByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsByteValue();
        testGetLongWithNullObject();
    }

    static void testGetLongReturnsByteValue() throws Exception {
        class Dummy {
            public byte value = 42;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);
        long result = accessor.getLong(dummy);
        assert result == (byte)42 : "Expected 42, got " + result;
    }

    static void testGetLongWithNullObject() throws Exception {
        class Dummy {
            public byte value = 42;
        }
        Field field = Dummy.class.getField("value");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);
        try {
            accessor.getLong(null);
            assert false : "Expected IllegalArgumentException for null object";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
