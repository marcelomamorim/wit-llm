/*
 * @test
 * @summary Verify that Field.getFloat on a byte field returns the widened byte value through the Unsafe byte accessor
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static class Holder {
        public byte b;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("b");
        Holder h = new Holder();

        h.b = 0;
        check(f.getFloat(h) == 0.0f, "zero");

        h.b = 127;
        check(f.getFloat(h) == 127.0f, "max positive");

        h.b = -128;
        check(f.getFloat(h) == -128.0f, "min negative");

        h.b = -1;
        check(f.getFloat(h) == -1.0f, "minus one");

        f.setByte(h, (byte) 42);
        check(f.getFloat(h) == 42.0f, "after setByte");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
