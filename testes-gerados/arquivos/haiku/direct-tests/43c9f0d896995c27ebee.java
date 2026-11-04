/*
 * @test
 * @summary Field.getChar on a volatile long field must throw IllegalArgumentException
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    static class Holder {
        volatile long value;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("value");
        f.setLong(h, 42L);
        if (f.getLong(h) != 42L) {
            throw new RuntimeException("getLong sanity check failed");
        }
        expectIllegalArgument(f, h);
        expectIllegalArgument(f, null);
        if (f.getLong(h) != 42L) {
            throw new RuntimeException("value changed after getChar failure");
        }
    }

    static void expectIllegalArgument(Field f, Object obj) {
        try {
            char c = f.getChar(obj);
            throw new RuntimeException("no exception thrown, got char " + (int) c);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
