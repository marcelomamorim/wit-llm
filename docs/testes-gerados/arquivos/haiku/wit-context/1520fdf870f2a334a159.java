/*
 * @test
 * @summary Verify Field.getDouble on public float fields, which dispatches to UnsafeFloatFieldAccessorImpl.getDouble, and reject objects of an unrelated type
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public static class Holder {
        public float value = 0.1f;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("value");

        double d = f.getDouble(h);
        if (d != (double) 0.1f) {
            throw new AssertionError("getDouble mismatch: " + d);
        }
        if (d != (double) f.getFloat(h)) {
            throw new AssertionError("getDouble does not widen getFloat");
        }

        h.value = -2.5f;
        if (f.getDouble(h) != -2.5d) {
            throw new AssertionError("getDouble did not reflect updated value");
        }

        try {
            f.getDouble("not a Holder");
            throw new AssertionError("expected IllegalArgumentException for wrong receiver type");
        } catch (IllegalArgumentException expected) {
        }
    }
}
