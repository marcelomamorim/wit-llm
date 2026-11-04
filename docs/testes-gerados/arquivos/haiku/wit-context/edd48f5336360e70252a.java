/*
 * @test
 * @summary Verifies Field.getInt on byte fields, which dispatches to UnsafeByteFieldAccessorImpl.getInt, and the IllegalArgumentException for a receiver of the wrong type
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static class Holder {
        public byte b = 42;
        public byte neg = -5;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field positive = Holder.class.getField("b");
        int v1 = positive.getInt(h);
        if (v1 != 42) {
            throw new AssertionError("expected 42 but got " + v1);
        }

        Field negative = Holder.class.getField("neg");
        int v2 = negative.getInt(h);
        if (v2 != -5) {
            throw new AssertionError("expected -5 but got " + v2);
        }

        boolean thrown = false;
        try {
            positive.getInt("not a Holder");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for wrong receiver type");
        }
    }
}
