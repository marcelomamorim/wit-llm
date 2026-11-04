/*
 * @test
 * @summary Verify that Field.getChar on a long field throws IllegalArgumentException and that getLong/get still work
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    static class Holder {
        long value = 42L;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            f.getChar(h);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getChar on long field");
        }

        if (f.getLong(h) != 42L) {
            throw new AssertionError("getLong returned unexpected value");
        }

        Object boxed = f.get(h);
        if (!Long.valueOf(42L).equals(boxed)) {
            throw new AssertionError("get returned unexpected value: " + boxed);
        }
    }
}
