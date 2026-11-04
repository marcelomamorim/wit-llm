/*
 * @test
 * @summary Test getByte method of UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteReturnsCorrectValue();
    }

    private static void testGetByteReturnsCorrectValue() throws Exception {
        class Dummy {
            public byte value = 42;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        UnsafeQualifiedByteFieldAccessorImpl accessor = new UnsafeQualifiedByteFieldAccessorImpl(field, false);
        byte result = accessor.getByte(dummy);
        if (result != 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }
}
