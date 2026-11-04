/*
 * @test
 * @summary Verify Field.getFloat on int fields via public reflection API, which delegates to the accessor's getFloat
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class Holder {
        volatile int v = 42;
        final int f = 7;
        int plain = -5;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field fv = Holder.class.getDeclaredField("v");
        float vf = fv.getFloat(h);
        if (vf != 42.0f) {
            throw new RuntimeException("volatile int getFloat expected 42.0 but got " + vf);
        }

        Field ff = Holder.class.getDeclaredField("f");
        float ffv = ff.getFloat(h);
        if (ffv != 7.0f) {
            throw new RuntimeException("final int getFloat expected 7.0 but got " + ffv);
        }

        Field fp = Holder.class.getDeclaredField("plain");
        float pf = fp.getFloat(h);
        if (pf != -5.0f) {
            throw new RuntimeException("plain int getFloat expected -5.0 but got " + pf);
        }

        h.plain = 1000;
        if (fp.getFloat(h) != 1000.0f) {
            throw new RuntimeException("getFloat should observe updated field value");
        }

        try {
            fv.getFloat("not a Holder");
            throw new RuntimeException("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
