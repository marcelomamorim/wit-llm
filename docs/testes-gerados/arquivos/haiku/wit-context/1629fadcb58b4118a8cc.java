/*
 * @test
 * @summary Field.getBoolean on a volatile int field must throw IllegalArgumentException via the qualified integer accessor
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class Holder {
        public volatile int value = 7;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("value");

        if (f.getInt(h) != 7) {
            throw new RuntimeException("getInt sanity check failed");
        }

        boolean threw = false;
        try {
            f.getBoolean(h);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected IllegalArgumentException from getBoolean on int field");
        }
    }
}
