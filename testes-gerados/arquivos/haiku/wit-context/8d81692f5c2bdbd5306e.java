/*
 * @test
 * @summary Checks public Field.getBoolean behavior for a volatile boolean field, which reaches UnsafeQualifiedBooleanFieldAccessorImpl.getBoolean through the reflection API
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("flag");
        Holder h = new Holder();

        if (!f.getBoolean(h)) {
            throw new AssertionError("expected initial value true");
        }

        h.flag = false;
        if (f.getBoolean(h)) {
            throw new AssertionError("expected updated value false");
        }

        try {
            f.getBoolean("not a Holder");
            throw new AssertionError("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
        }
    }
}
