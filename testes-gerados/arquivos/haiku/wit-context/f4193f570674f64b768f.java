/*
 * @test
 * @summary Verify Field.getInt on a short field returns the stored value and rejects receivers of an unrelated type
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class Holder {
        private final short s = 42;
    }

    static class Other {
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("s");
        f.setAccessible(true);

        if (f.getInt(h) != 42) {
            throw new RuntimeException("getInt returned unexpected value");
        }
        if (f.getShort(h) != 42) {
            throw new RuntimeException("getShort returned unexpected value");
        }
        Object boxed = f.get(h);
        if (!(boxed instanceof Short) || ((Short) boxed).shortValue() != 42) {
            throw new RuntimeException("get returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getInt(new Other());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for unrelated receiver");
        }
    }
}
