/*
 * @test
 * @summary Verify public Field.getDouble, Field.get and Field.setDouble on a public double field, and IllegalArgumentException for an unrelated receiver
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public static class Holder {
        public double value = 1.5;
    }

    public static class Other {
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");
        Holder h = new Holder();

        if (f.getDouble(h) != 1.5) {
            throw new AssertionError("getDouble returned unexpected initial value");
        }

        Object boxed = f.get(h);
        if (!(boxed instanceof Double)) {
            throw new AssertionError("get did not return a Double");
        }
        if (((Double) boxed).doubleValue() != 1.5) {
            throw new AssertionError("get returned unexpected value");
        }

        f.setDouble(h, 2.25);
        if (f.getDouble(h) != 2.25) {
            throw new AssertionError("getDouble did not observe setDouble");
        }

        boolean thrown = false;
        try {
            f.getDouble(new Other());
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for unrelated receiver");
        }
    }
}
