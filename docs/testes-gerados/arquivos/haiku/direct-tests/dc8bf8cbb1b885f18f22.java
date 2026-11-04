/*
 * @test
 * @summary Field.getBoolean on a volatile reference field must throw IllegalArgumentException, reaching UnsafeQualifiedObjectFieldAccessorImpl.getBoolean through the public reflection API
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile Object ref = "value";
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("ref");

        Object got = f.get(h);
        if (!"value".equals(got)) {
            throw new RuntimeException("Unexpected get result: " + got);
        }

        boolean thrown = false;
        try {
            f.getBoolean(h);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getBoolean on Object field did not throw IllegalArgumentException");
        }
    }
}
