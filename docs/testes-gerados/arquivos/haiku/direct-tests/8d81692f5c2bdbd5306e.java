/*
 * @test
 * @summary Verify Field.getBoolean on volatile boolean fields, which reaches UnsafeQualifiedBooleanFieldAccessorImpl.getBoolean
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    static class Holder {
        public volatile boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("flag");

        if (!f.getBoolean(h)) {
            throw new AssertionError("expected initial value true");
        }

        h.flag = false;
        if (f.getBoolean(h)) {
            throw new AssertionError("expected false after direct write");
        }

        f.setBoolean(h, true);
        if (!h.flag) {
            throw new AssertionError("setBoolean did not write the field");
        }
        if (!f.getBoolean(h)) {
            throw new AssertionError("expected true after setBoolean");
        }

        Holder other = new Holder();
        other.flag = false;
        if (f.getBoolean(other)) {
            throw new AssertionError("getBoolean must read the given instance");
        }
        if (!f.getBoolean(h)) {
            throw new AssertionError("first instance changed unexpectedly");
        }

        try {
            f.getBoolean("notAHolder");
            throw new AssertionError("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected: ensureObj rejects an object not of the declaring class
        }
    }
}
