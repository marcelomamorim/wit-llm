/*
 * @test
 * @summary Field.getBoolean on a double field must throw IllegalArgumentException via the unsafe double accessor
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static class Holder {
        public double d = 1.5;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("d");

        boolean thrown = false;
        try {
            f.getBoolean(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getBoolean on double field did not throw IllegalArgumentException");
        }

        if (f.getDouble(h) != 1.5) {
            throw new RuntimeException("getDouble returned unexpected value");
        }
    }
}
