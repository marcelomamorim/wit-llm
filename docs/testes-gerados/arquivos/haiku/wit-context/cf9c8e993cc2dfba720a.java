/*
 * @test
 * @summary Field.getBoolean on a static byte field must throw IllegalArgumentException, reaching UnsafeQualifiedStaticByteFieldAccessorImpl.getBoolean through the public reflection API
 * @run main UnsafeQualifiedStaticByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticByteFieldAccessorImplWitupTest {

    public static final byte FINAL_BYTE = 42;
    public static byte mutableByte = 7;

    public static void main(String[] args) throws Exception {
        testGetBooleanOnFinalStaticByteFieldThrows();
        testGetBooleanOnMutableStaticByteFieldThrows();
        testGetByteStillWorksOnFinalStaticByteField();
    }

    static void testGetBooleanOnFinalStaticByteFieldThrows() throws Exception {
        Field f = UnsafeQualifiedStaticByteFieldAccessorImplWitupTest.class.getField("FINAL_BYTE");
        try {
            f.getBoolean(null);
            throw new AssertionError("expected IllegalArgumentException for getBoolean on byte field");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testGetBooleanOnMutableStaticByteFieldThrows() throws Exception {
        Field f = UnsafeQualifiedStaticByteFieldAccessorImplWitupTest.class.getField("mutableByte");
        try {
            f.getBoolean(null);
            throw new AssertionError("expected IllegalArgumentException for getBoolean on byte field");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testGetByteStillWorksOnFinalStaticByteField() throws Exception {
        Field f = UnsafeQualifiedStaticByteFieldAccessorImplWitupTest.class.getField("FINAL_BYTE");
        byte v = f.getByte(null);
        if (v != 42) {
            throw new AssertionError("unexpected byte value: " + v);
        }
    }
}
