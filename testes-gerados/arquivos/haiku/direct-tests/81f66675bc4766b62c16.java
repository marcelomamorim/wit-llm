/*
 * @test
 * @summary Verify that Field.getDouble on float fields returns the widened float value
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static class Holder {
        public float f = 1.5f;
        public final float ff = 2.25f;
        public volatile float vf = -3.75f;
        public float nan = Float.NaN;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field f = Holder.class.getField("f");
        check(f.getDouble(h) == 1.5d, "getDouble on plain float field");
        check(f.getDouble(h) == ((Float) f.get(h)).doubleValue(),
              "getDouble must equal widened get() value");

        f.setFloat(h, 0.1f);
        check(f.getDouble(h) == (double) 0.1f, "getDouble after setFloat");

        Field ff = Holder.class.getField("ff");
        check(ff.getDouble(h) == 2.25d, "getDouble on final float field");

        Field vf = Holder.class.getField("vf");
        check(vf.getDouble(h) == -3.75d, "getDouble on volatile float field");

        Field nan = Holder.class.getField("nan");
        check(Double.isNaN(nan.getDouble(h)), "getDouble must preserve NaN");
    }
}
