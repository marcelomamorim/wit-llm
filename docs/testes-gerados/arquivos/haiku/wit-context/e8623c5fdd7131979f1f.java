/*
 * @test
 * @summary Verify observable Field.getByte behavior dispatched to UnsafeByteFieldAccessorImpl
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class Holder {
        private byte value;
        private String other;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");
        f.setAccessible(true);
        Holder h = new Holder();

        byte[] samples = {0, 1, -1, 42, Byte.MIN_VALUE, Byte.MAX_VALUE};
        for (byte b : samples) {
            f.setByte(h, b);
            check(f.getByte(h) == b, "getByte mismatch for " + b);
            check(f.getInt(h) == b, "getInt widening mismatch for " + b);
        }

        boolean wrongTypeRejected = false;
        try {
            f.getByte("not a holder");
        } catch (IllegalArgumentException expected) {
            wrongTypeRejected = true;
        }
        check(wrongTypeRejected, "getByte must reject object of unrelated type");

        boolean nullRejected = false;
        try {
            f.getByte(null);
        } catch (NullPointerException expected) {
            nullRejected = true;
        }
        check(nullRejected, "getByte must reject null receiver for instance field");
    }
}
