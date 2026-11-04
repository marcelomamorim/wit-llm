/*
 * @test
 * @summary Field.getChar on a static byte field must throw IllegalArgumentException, reaching UnsafeStaticByteFieldAccessorImpl.getChar
 * @run main UnsafeStaticByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticByteFieldAccessorImplWitupTest {
    public static byte b = 7;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticByteFieldAccessorImplWitupTest.class.getField("b");

        if (f.getByte(null) != 7) {
            throw new RuntimeException("getByte returned unexpected value");
        }

        try {
            f.getChar(null);
            throw new RuntimeException("expected IllegalArgumentException from getChar");
        } catch (IllegalArgumentException expected) {
            // expected: byte field cannot be read as char
        }
    }
}
