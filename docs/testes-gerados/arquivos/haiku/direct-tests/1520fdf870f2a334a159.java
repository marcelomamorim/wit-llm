/*
 * @test
 * @summary Field.getDouble on a float field exercises UnsafeFloatFieldAccessorImpl.getDouble and returns the widened float value
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static class Holder {
        public float value;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("value");

        h.value = 0.1f;
        double d1 = f.getDouble(h);
        check(d1 == (double) 0.1f, "getDouble must widen 0.1f exactly, got " + d1);
        check(d1 == f.getFloat(h), "getDouble must equal widened getFloat");

        h.value = -3.5f;
        check(f.getDouble(h) == -3.5d, "getDouble must return -3.5");

        h.value = Float.MAX_VALUE;
        check(f.getDouble(h) == (double) Float.MAX_VALUE, "getDouble must widen Float.MAX_VALUE");

        h.value = 0.0f;
        check(f.getDouble(h) == 0.0d, "getDouble must return 0.0");

        try {
            f.getDouble("not a Holder");
            throw new RuntimeException("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected per Field.getDouble contract
        }
    }
}
