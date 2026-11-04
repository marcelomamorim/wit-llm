/*
 * @test
 * @summary Field.getChar on a short field must throw IllegalArgumentException, which is the observable behavior of UnsafeShortFieldAccessorImpl.getChar
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    static class Holder {
        short s = 42;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("s");
        f.setAccessible(true);

        boolean thrown = false;
        try {
            f.getChar(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on short field");
        }

        if (f.getShort(h) != 42) {
            throw new RuntimeException("getShort should still return the short value");
        }
    }
}
