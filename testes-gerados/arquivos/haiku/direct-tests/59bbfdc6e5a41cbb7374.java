/*
 * @test
 * @summary Verifies that Field.getChar on a volatile short field (served by UnsafeQualifiedShortFieldAccessorImpl) throws IllegalArgumentException, while getShort still reads the value.
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile short value = 7;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("value");

        if (f.getShort(h) != 7) {
            throw new RuntimeException("getShort returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getChar(h);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getChar did not throw IllegalArgumentException");
        }
    }
}
