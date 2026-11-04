/*
 * @test
 * @summary Field.getDouble on int fields reaches UnsafeIntegerFieldAccessorImpl.getDouble and widens the int value
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public int value;
        public int other = 7;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");
        Field g = Holder.class.getDeclaredField("other");
        Holder h = new Holder();

        h.value = -5;
        check(f.getDouble(h) == -5.0d, "negative int not widened to double");

        h.value = 0;
        check(f.getDouble(h) == 0.0d, "zero int not widened to double");

        h.value = Integer.MAX_VALUE;
        check(f.getDouble(h) == (double) Integer.MAX_VALUE, "MAX_VALUE not widened to double");

        h.value = 123456789;
        check(f.getDouble(h) == f.getInt(h), "getDouble differs from getInt value");

        check(g.getDouble(h) == 7.0d, "initialized field value not returned by getDouble");

        h.other = 42;
        check(g.getDouble(h) == 42.0d, "getDouble does not reflect updated field value");
    }
}
