/*
 * @test
 * @summary Verifies that Field.getBoolean on a static long field throws IllegalArgumentException, reaching UnsafeStaticLongFieldAccessorImpl.getBoolean through the public reflection API
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {

    public static long value = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getBoolean on static long field did not throw IllegalArgumentException");
        }

        if (f.getLong(null) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }
        if (!Long.valueOf(42L).equals(f.get(null))) {
            throw new RuntimeException("get returned unexpected value");
        }
    }
}
