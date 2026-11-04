/*
 * @test
 * @summary Field.getBoolean on a byte field must throw IllegalArgumentException via UnsafeByteFieldAccessorImpl.getBoolean, while getByte still works
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class Holder {
        byte b = 7;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("b");
        Holder h = new Holder();

        boolean thrown = false;
        try {
            f.getBoolean(h);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getBoolean on byte field");
        }

        if (f.getByte(h) != 7) {
            throw new AssertionError("getByte returned unexpected value");
        }
    }
}
