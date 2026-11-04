/*
 * @test
 * @summary Verify that Field.getChar on a static final float field is rejected by the qualified static float accessor
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static final float FLOAT_VALUE = 1.5f;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getField("FLOAT_VALUE");

        Object boxed = field.get(null);
        if (!Float.valueOf(1.5f).equals(boxed)) {
            throw new RuntimeException("Unexpected get() result: " + boxed);
        }

        float f = field.getFloat(null);
        if (f != 1.5f) {
            throw new RuntimeException("Unexpected getFloat() result: " + f);
        }

        double d = field.getDouble(null);
        if (d != 1.5d) {
            throw new RuntimeException("Unexpected getDouble() result: " + d);
        }

        try {
            char c = field.getChar(null);
            throw new RuntimeException("getChar() did not throw, returned: " + c);
        } catch (IllegalArgumentException expected) {
            // expected: the accessor rejects non-float conversions
        }
    }
}
