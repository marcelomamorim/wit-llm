/*
 * @test
 * @summary Verifies Field.getDouble on volatile and final int fields, which are served by UnsafeQualifiedIntegerFieldAccessorImpl.getDouble
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile int v;
        public final int f;

        Holder(int f) {
            this.f = f;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder(-7);
        h.v = 42;

        Field fv = Holder.class.getField("v");
        check(fv.getDouble(h) == 42.0, "volatile int 42 should read as 42.0");

        h.v = Integer.MIN_VALUE;
        check(fv.getDouble(h) == (double) Integer.MIN_VALUE,
              "volatile int MIN_VALUE should widen to double");

        h.v = Integer.MAX_VALUE;
        check(fv.getDouble(h) == (double) Integer.MAX_VALUE,
              "volatile int MAX_VALUE should widen to double");

        h.v = 0;
        check(fv.getDouble(h) == 0.0, "volatile int 0 should read as 0.0");

        Field ff = Holder.class.getField("f");
        check(ff.getDouble(h) == -7.0, "final int -7 should read as -7.0");
    }
}
