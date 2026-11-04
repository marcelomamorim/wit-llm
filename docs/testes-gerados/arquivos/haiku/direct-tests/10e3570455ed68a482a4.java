/*
 * @test
 * @summary Verifies Field.getDouble on a volatile double field, which is served by UnsafeQualifiedDoubleFieldAccessorImpl.getDouble
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    static class Holder {
        volatile double value;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");
        Holder h = new Holder();

        check(f.getDouble(h) == 0.0, "default volatile double value should be 0.0");

        h.value = 3.5;
        check(f.getDouble(h) == 3.5, "getDouble should observe the stored value 3.5");

        f.setDouble(h, -1.25);
        check(f.getDouble(h) == -1.25, "getDouble should observe value set by setDouble");

        f.setDouble(h, Double.NaN);
        check(Double.isNaN(f.getDouble(h)), "getDouble should return NaN after storing NaN");

        f.set(h, Double.valueOf(2.0));
        check(f.getDouble(h) == 2.0, "getDouble should observe value set via set(Object)");

        try {
            f.getDouble("not a holder");
            throw new RuntimeException("getDouble on wrong receiver type should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
