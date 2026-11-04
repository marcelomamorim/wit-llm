/*
 * @test
 * @summary Verifies observable getFloat behavior of the Unsafe float field accessor through public java.lang.reflect.Field API
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    static class Holder {
        private float f = 1.5f;
    }

    public static void main(String[] args) throws Exception {
        Field field = Holder.class.getDeclaredField('f');
        field.setAccessible(true);
        Holder h = new Holder();

        if (field.getFloat(h) != 1.5f) {
            throw new RuntimeException('initial getFloat mismatch: ' + field.getFloat(h));
        }

        field.setFloat(h, -2.25f);
        if (field.getFloat(h) != -2.25f) {
            throw new RuntimeException('getFloat after setFloat mismatch: ' + field.getFloat(h));
        }

        Object boxed = field.get(h);
        if (!(boxed instanceof Float) || ((Float) boxed).floatValue() != -2.25f) {
            throw new RuntimeException('get() boxed value mismatch: ' + boxed);
        }

        field.setFloat(h, Float.NaN);
        if (!Float.isNaN(field.getFloat(h))) {
            throw new RuntimeException('NaN not preserved');
        }

        try {
            field.getFloat(new Object());
            throw new RuntimeException('expected IllegalArgumentException for wrong receiver type');
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
