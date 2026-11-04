/*
 * @test
 * @summary Field.getInt on a double field must throw IllegalArgumentException via UnsafeDoubleFieldAccessorImpl.getInt
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

        if (f.getDouble(h) != 1.5) {
            throw new RuntimeException("getDouble returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getInt(h);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getInt on double field did not throw IllegalArgumentException");
        }
    }
}
