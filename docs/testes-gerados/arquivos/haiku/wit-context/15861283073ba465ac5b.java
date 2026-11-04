/*
 * @test
 * @summary Checks Field.getFloat on a final long field (widening path that reaches UnsafeQualifiedLongFieldAccessorImpl.getFloat) and the IllegalArgumentException for an incompatible receiver.
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    static class Holder {
        final long value;

        Holder(long value) {
            this.value = value;
        }
    }

    static class Other {
    }

    public static void main(String[] args) throws Exception {
        Field field = Holder.class.getDeclaredField("value");

        long big = 1234567890123L;
        float fBig = field.getFloat(new Holder(big));
        if (fBig != (float) big) {
            throw new RuntimeException("getFloat widening mismatch: " + fBig);
        }

        float fNeg = field.getFloat(new Holder(-42L));
        if (fNeg != -42.0f) {
            throw new RuntimeException("getFloat negative mismatch: " + fNeg);
        }

        float fZero = field.getFloat(new Holder(0L));
        if (fZero != 0.0f) {
            throw new RuntimeException("getFloat zero mismatch: " + fZero);
        }

        try {
            field.getFloat(new Other());
            throw new RuntimeException("Expected IllegalArgumentException for incompatible receiver");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            field.getFloat("not a holder");
            throw new RuntimeException("Expected IllegalArgumentException for String receiver");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
