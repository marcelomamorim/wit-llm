/*
 * @test
 * @summary Checks Field.getFloat on a long field, which dispatches to UnsafeLongFieldAccessorImpl.getFloat, and the IllegalArgumentException for a receiver of the wrong type
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static class Holder {
        public long value;
    }

    public static class Other {
        public long value;
    }

    public static void main(String[] args) throws Exception {
        Field field = Holder.class.getDeclaredField("value");

        Holder holder = new Holder();
        holder.value = (1L << 40) + 3L;
        float expected = (float) holder.value;
        float actual = field.getFloat(holder);
        if (actual != expected) {
            throw new AssertionError("getFloat returned " + actual + ", expected " + expected);
        }

        holder.value = -42L;
        if (field.getFloat(holder) != -42.0f) {
            throw new AssertionError("getFloat returned wrong value for negative long");
        }

        boolean thrown = false;
        try {
            field.getFloat(new Other());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for wrong receiver type");
        }
    }
}
