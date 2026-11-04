/*
 * @test
 * @summary Field.getByte on double fields must throw IllegalArgumentException and leave the value unchanged
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public volatile double volatileValue = 1.5;
    public final double finalValue = 2.5;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedDoubleFieldAccessorImplWitupTest t = new UnsafeQualifiedDoubleFieldAccessorImplWitupTest();

        Field vf = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getField("volatileValue");
        expectIAE(vf, t);
        if (vf.getDouble(t) != 1.5) {
            throw new RuntimeException("volatile double value changed after getByte");
        }

        Field ff = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getField("finalValue");
        expectIAE(ff, t);
        if (ff.getDouble(t) != 2.5) {
            throw new RuntimeException("final double value changed after getByte");
        }
    }

    private static void expectIAE(Field f, Object obj) {
        try {
            byte b = f.getByte(obj);
            throw new RuntimeException("expected IllegalArgumentException, got " + b);
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException", e);
        }
    }
}
