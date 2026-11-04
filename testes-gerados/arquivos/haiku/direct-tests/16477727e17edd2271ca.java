/*
 * @test
 * @summary Verifies Field.getLong on byte fields, which dispatches to UnsafeByteFieldAccessorImpl.getLong
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static class Holder {
        public byte value;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");
        Holder h = new Holder();

        h.value = -5;
        check(f.getLong(h) == -5L, "negative byte should widen to long");

        h.value = Byte.MAX_VALUE;
        check(f.getLong(h) == 127L, "Byte.MAX_VALUE should widen to 127L");

        h.value = Byte.MIN_VALUE;
        check(f.getLong(h) == -128L, "Byte.MIN_VALUE should widen to -128L");

        h.value = 0;
        check(f.getLong(h) == 0L, "zero should yield 0L");

        h.value = 42;
        check(f.getLong(h) == 42L, "positive byte should widen to 42L");
        check(f.getLong(h) == ((Byte) f.get(h)).longValue(),
              "getLong should be consistent with get");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
