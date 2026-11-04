/* @test    @summary Test getLong method of UnsafeQualifiedByteFieldAccessorImpl class    @run main UnsafeQualifiedByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsByteValue();
    }

    private static void testGetLongReturnsByteValue() throws Exception {
        class Dummy {
            public byte value = 42;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl(field, false);
        long result = accessor.getLong(dummy);
        assert result == (long) dummy.value : "Expected " + dummy.value + " but got " + result;
    }
}
