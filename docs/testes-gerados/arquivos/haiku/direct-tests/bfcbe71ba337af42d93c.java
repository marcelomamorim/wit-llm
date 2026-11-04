/*
 * @test
 * @summary Field.getByte on a static char field must throw IllegalArgumentException while get still returns the char value
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {
    public static final char FINAL_CHAR = 'x';
    public static volatile char VOLATILE_CHAR = 'y';

    public static void main(String[] args) throws Exception {
        check("FINAL_CHAR", 'x');
        check("VOLATILE_CHAR", 'y');
    }

    static void check(String name, char expected) throws Exception {
        Field f = UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest.class.getDeclaredField(name);
        boolean threw = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException(name + ": getByte did not throw IllegalArgumentException");
        }
        Object value = f.get(null);
        if (!(value instanceof Character) || ((Character) value).charValue() != expected) {
            throw new RuntimeException(name + ": unexpected value after getByte failure: " + value);
        }
    }
}
