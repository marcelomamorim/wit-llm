/*
 * @test
 * @summary Field.getByte on a short field must reject the narrowing read handled by UnsafeShortFieldAccessorImpl.getByte
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static class Holder {
        public short s = 7;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("s");

        boolean thrown = false;
        try {
            f.getByte(h);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException from getByte on short field");
        }

        if (f.getShort(h) != 7) {
            throw new RuntimeException("getShort returned unexpected value");
        }
        if (f.getInt(h) != 7) {
            throw new RuntimeException("getInt widening returned unexpected value");
        }
    }
}
