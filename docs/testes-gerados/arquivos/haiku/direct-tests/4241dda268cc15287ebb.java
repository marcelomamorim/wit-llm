/*
 * @test
 * @summary Field.getShort on a float field must throw IllegalArgumentException, which is the behavior of UnsafeQualifiedFloatFieldAccessorImpl.getShort
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    volatile float vf = 1.5f;
    float f = 2.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest t = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        checkGetShortThrows("vf", t);
        checkGetShortThrows("f", t);

        Field fv = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("vf");
        if (fv.getFloat(t) != 1.5f) {
            throw new RuntimeException("getFloat on volatile field returned unexpected value");
        }
    }

    static void checkGetShortThrows(String name, Object o) throws Exception {
        Field fld = o.getClass().getDeclaredField(name);
        try {
            short s = fld.getShort(o);
            throw new RuntimeException("getShort did not throw for field " + name + ", returned " + s);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
