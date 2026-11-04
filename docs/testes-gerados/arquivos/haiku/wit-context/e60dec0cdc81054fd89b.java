/*
 * @test
 * @summary Verify that Field.getChar on a short field rejects the call with IllegalArgumentException, reaching UnsafeShortFieldAccessorImpl.getChar through the public reflection API
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static class Holder {
        public short value = 5;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");
        Holder h = new Holder();

        if (f.getShort(h) != 5) {
            throw new RuntimeException("getShort returned unexpected value");
        }

        try {
            f.getChar(h);
            throw new RuntimeException("expected IllegalArgumentException for getChar on short field");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getChar(null);
            throw new RuntimeException("expected IllegalArgumentException for getChar with null receiver");
        } catch (IllegalArgumentException expected) {
        }
    }
}
