/*
 * @test
 * @summary Verify that Field.getChar on a volatile boolean field throws IllegalArgumentException, reaching the UnsafeQualifiedBooleanFieldAccessorImpl.getChar path through the public reflection API
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        Field field = Holder.class.getDeclaredField("flag");
        Holder holder = new Holder();

        if (!field.getBoolean(holder)) {
            throw new RuntimeException("getBoolean should return the initial value true");
        }

        boolean thrown = false;
        try {
            field.getChar(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getChar on boolean volatile field must throw IllegalArgumentException");
        }
    }
}
