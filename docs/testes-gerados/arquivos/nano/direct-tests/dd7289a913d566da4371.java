/*
 * @test
 * @summary Test getFloat method of UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsByteValue();
    }

    private static void testGetFloatReturnsByteValue() throws Exception {
        class Dummy {
            public byte value = 42;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        UnsafeQualifiedByteFieldAccessorImpl accessor = new UnsafeQualifiedByteFieldAccessorImpl(field, false);
        float result = accessor.getFloat(dummy);
        if (result != (float) dummy.value) {
            throw new AssertionError("Expected " + (float) dummy.value + " but got " + result);
        }
    }
}
