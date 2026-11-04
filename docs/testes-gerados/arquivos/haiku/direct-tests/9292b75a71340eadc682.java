/*
 * @test
 * @summary Verify that Field.getShort on a volatile int field (qualified Unsafe int accessor) throws IllegalArgumentException, while Field.get still returns the int value
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile int v = 42;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("v");
        Holder h = new Holder();

        Object boxed = f.get(h);
        if (!(boxed instanceof Integer) || ((Integer) boxed).intValue() != 42) {
            throw new RuntimeException("Field.get returned unexpected value: " + boxed);
        }

        boolean thrown = false;
        try {
            f.getShort(h);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getShort on int field");
        }
    }
}
