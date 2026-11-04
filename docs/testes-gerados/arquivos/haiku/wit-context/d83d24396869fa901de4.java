/*
 * @test
 * @summary Verify that Field.getShort on a volatile long field throws IllegalArgumentException and getLong still works
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile long v = 42L;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("v");

        boolean thrown = false;
        try {
            f.getShort(h);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getShort on long field");
        }

        if (f.getLong(h) != 42L) {
            throw new AssertionError("getLong returned unexpected value");
        }
    }
}
