/*
 * @test
 * @summary Verifies that Field.getChar on a static int field throws IllegalArgumentException via UnsafeStaticIntegerFieldAccessorImpl.getChar
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {

    static class Holder {
        static int value = 42;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");

        if (f.getInt(null) != 42) {
            throw new RuntimeException("getInt returned unexpected value");
        }

        try {
            char c = f.getChar(null);
            throw new RuntimeException("expected IllegalArgumentException, got char " + c);
        } catch (IllegalArgumentException expected) {
            // expected: getChar is not supported for int static fields
        }

        try {
            f.getChar(new Object());
            throw new RuntimeException("expected IllegalArgumentException for non-null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
