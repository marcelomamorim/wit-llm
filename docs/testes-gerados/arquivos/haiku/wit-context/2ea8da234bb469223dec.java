/*
 * @test
 * @summary Checks Field.getInt on a volatile byte field (qualified byte accessor path): sign-extended value and IllegalArgumentException for an object of the wrong type
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public volatile byte b;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedByteFieldAccessorImplWitupTest holder = new UnsafeQualifiedByteFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getField("b");

        f.setByte(holder, (byte) -5);
        check(f.getInt(holder) == -5, "getInt should sign-extend -5");

        f.setByte(holder, (byte) 127);
        check(f.getInt(holder) == 127, "getInt should return 127");

        boolean threw = false;
        try {
            f.getInt("not a holder");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(threw, "getInt with an object of the wrong type should throw IllegalArgumentException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
