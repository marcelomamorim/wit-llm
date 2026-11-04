/*
 * @test
 * @summary Verify Field.getDouble on a long field and the IllegalArgumentException for a receiver of the wrong type
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {
    private static class Holder {
        private long value;

        Holder(long v) {
            value = v;
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");
        f.setAccessible(true);

        long[] samples = {0L, 1L, -1L, 1234567890123L, Long.MAX_VALUE, Long.MIN_VALUE};
        for (long s : samples) {
            Holder h = new Holder(s);
            double d = f.getDouble(h);
            if (d != (double) s) {
                throw new RuntimeException("getDouble mismatch for " + s + ": " + d);
            }
        }

        boolean thrown = false;
        try {
            f.getDouble(new Object());
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for wrong receiver type");
        }
    }
}
