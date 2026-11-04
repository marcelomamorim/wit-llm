/*
 * @test
 * @summary Field.getLong on a static boolean field must throw IllegalArgumentException
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        boolean thrown = false;
        try {
            long v = f.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException, got " + v);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getLong did not throw IllegalArgumentException");
        }
        if (!f.getBoolean(null)) {
            throw new RuntimeException("getBoolean returned unexpected value");
        }
    }
}
