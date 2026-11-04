/*
 * @test
 * @summary Field.getShort on a double field must throw IllegalArgumentException (WITUP expath for UnsafeQualifiedDoubleFieldAccessorImpl.getShort)
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    volatile double vd = 1.5;
    final double fd = 2.0;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedDoubleFieldAccessorImplWitupTest t = new UnsafeQualifiedDoubleFieldAccessorImplWitupTest();

        Field vf = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getDeclaredField("vd");
        vf.setAccessible(true);
        if (vf.getDouble(t) != 1.5) {
            throw new RuntimeException("unexpected getDouble value for volatile field");
        }
        expectIllegalArgument(vf, t);

        Field ff = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getDeclaredField("fd");
        ff.setAccessible(true);
        if (ff.getDouble(t) != 2.0) {
            throw new RuntimeException("unexpected getDouble value for final field");
        }
        expectIllegalArgument(ff, t);
    }

    static void expectIllegalArgument(Field f, Object o) {
        try {
            short s = f.getShort(o);
            throw new RuntimeException("no IllegalArgumentException for getShort, returned " + s);
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException", e);
        }
    }
}
