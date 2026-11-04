/*
 * @test
 * @summary Verify Field.getLong on a short field widens the value and rejects an object of an unrelated type
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static final class Holder {
        final short value;

        Holder(short v) {
            value = v;
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");
        f.setAccessible(true);

        Holder h = new Holder((short) -1234);
        long widened = f.getLong(h);
        if (widened != -1234L) {
            throw new AssertionError("Expected -1234 but got " + widened);
        }

        boolean rejected = false;
        try {
            f.getLong(new Object());
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        if (!rejected) {
            throw new AssertionError("Expected IllegalArgumentException for unrelated object");
        }
    }
}
