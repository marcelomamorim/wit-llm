/*
 * @test
 * @summary Field.getShort on a static boolean field must throw IllegalArgumentException (widening not allowed)
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getField("flag");

        checkGetShortThrowsIAE(f, null);
        checkGetShortThrowsIAE(f, new Object());
        checkGetShortThrowsIAE(f, "ignored");

        if (!f.getBoolean(null)) {
            throw new RuntimeException("getBoolean must still return the field value");
        }
    }

    private static void checkGetShortThrowsIAE(Field f, Object obj) throws Exception {
        boolean thrown = false;
        try {
            short s = f.getShort(obj);
            throw new RuntimeException("Expected IllegalArgumentException, got short " + s);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException not thrown");
        }
    }
}
