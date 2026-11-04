/*
 * @test
 * @summary Verify that Field.getFloat on byte fields (volatile and final) widens the byte value to float
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class Holder {
        volatile byte vb;
        final byte fb;

        Holder(byte fb) {
            this.fb = fb;
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder((byte) -7);

        Field vf = Holder.class.getDeclaredField("vb");
        vf.setAccessible(true);

        vf.setByte(h, (byte) -128);
        check(vf.getFloat(h) == -128.0f, "min byte widened to float");

        vf.setByte(h, (byte) 127);
        check(vf.getFloat(h) == 127.0f, "max byte widened to float");

        vf.setByte(h, (byte) 0);
        check(vf.getFloat(h) == 0.0f, "zero widened to float");

        vf.setByte(h, (byte) 42);
        float f = vf.getFloat(h);
        int i = vf.getInt(h);
        check(f == (float) i, "getFloat consistent with getInt");
        check(f == 42.0f, "positive value widened");

        Field ff = Holder.class.getDeclaredField("fb");
        ff.setAccessible(true);
        check(ff.getFloat(h) == -7.0f, "final byte field widened to float");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
