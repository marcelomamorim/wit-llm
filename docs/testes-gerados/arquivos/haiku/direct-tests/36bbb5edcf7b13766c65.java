/*
 * @test
 * @summary Verify that narrowing reads of a double field through the public Field API throw IllegalArgumentException, and that getDouble and get still return the stored value.
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static class Holder {
        public final double d = 1.5;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("d");

        if (f.getDouble(h) != 1.5) {
            throw new RuntimeException("getDouble returned unexpected value");
        }
        Object boxed = f.get(h);
        if (!(boxed instanceof Double) || ((Double) boxed).doubleValue() != 1.5) {
            throw new RuntimeException("get returned unexpected value: " + boxed);
        }

        expectIAE("getShort", () -> f.getShort(h));
        expectIAE("getInt", () -> f.getInt(h));
        expectIAE("getLong", () -> f.getLong(h));
        expectIAE("getByte", () -> f.getByte(h));
        expectIAE("getChar", () -> f.getChar(h));
        expectIAE("getBoolean", () -> f.getBoolean(h));
    }

    interface Call {
        void run() throws Exception;
    }

    static void expectIAE(String name, Call c) throws Exception {
        try {
            c.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException(name + " did not throw IllegalArgumentException");
    }
}
