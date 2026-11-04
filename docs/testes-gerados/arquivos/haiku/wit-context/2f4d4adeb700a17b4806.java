/*
 * @test
 * @summary Verify Field.getFloat/get on a volatile float field, which uses UnsafeQualifiedFloatFieldAccessorImpl, reject objects of incompatible type with IllegalArgumentException
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    volatile float value = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest t = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("value");

        if (f.getFloat(t) != 1.5f) {
            throw new AssertionError("getFloat returned unexpected value");
        }

        Object boxed = f.get(t);
        if (!(boxed instanceof Float) || ((Float) boxed).floatValue() != 1.5f) {
            throw new AssertionError("get returned unexpected value: " + boxed);
        }

        f.setFloat(t, 2.5f);
        if (f.getFloat(t) != 2.5f) {
            throw new AssertionError("getFloat after setFloat returned unexpected value");
        }

        try {
            f.getFloat(new Object());
            throw new AssertionError("expected IllegalArgumentException from getFloat");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.get(new Object());
            throw new AssertionError("expected IllegalArgumentException from get");
        } catch (IllegalArgumentException expected) {
        }
    }
}
