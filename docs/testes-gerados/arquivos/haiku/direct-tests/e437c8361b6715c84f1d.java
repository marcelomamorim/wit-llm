/*
 * @test
 * @summary Verify that Field.getDouble on a volatile short field widens the stored value to double and rejects objects of the wrong type
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile short s;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("s");
        Holder h = new Holder();

        short[] values = {0, 1, -1, 12345, Short.MAX_VALUE, Short.MIN_VALUE};
        for (short v : values) {
            h.s = v;
            double d = f.getDouble(h);
            if (d != (double) v) {
                throw new RuntimeException("getDouble mismatch for " + v + ": " + d);
            }
        }

        h.s = 7;
        double viaGet = ((Short) f.get(h)).doubleValue();
        if (f.getDouble(h) != viaGet) {
            throw new RuntimeException("getDouble differs from get for value 7");
        }

        try {
            f.getDouble("not a Holder");
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
