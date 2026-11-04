/*
 * @test
 * @summary Verifies Field.getDouble on a volatile char field returns the widened char value and rejects objects of an unrelated type with IllegalArgumentException
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    volatile char c;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedCharacterFieldAccessorImplWitupTest holder =
                new UnsafeQualifiedCharacterFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getDeclaredField("c");

        holder.c = 'A';
        check(f.getDouble(holder) == 65.0, "getDouble after direct write");

        f.setChar(holder, 'Z');
        check(f.getDouble(holder) == 90.0, "getDouble after setChar");

        f.setChar(holder, Character.MAX_VALUE);
        check(f.getDouble(holder) == 65535.0, "getDouble at Character.MAX_VALUE");

        boolean thrown = false;
        try {
            f.getDouble("not a holder");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "getDouble with unrelated object must throw IllegalArgumentException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
