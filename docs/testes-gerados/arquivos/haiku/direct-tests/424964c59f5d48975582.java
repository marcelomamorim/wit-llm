/*
 * @test
 * @summary Field.getByte on a volatile boolean field must throw IllegalArgumentException (qualified boolean accessor), while getBoolean still works
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    private volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedBooleanFieldAccessorImplWitupTest t =
            new UnsafeQualifiedBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        f.setAccessible(true);

        boolean threw = false;
        try {
            f.getByte(t);
        } catch (IllegalArgumentException expected) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getByte on boolean field must throw IllegalArgumentException");
        }

        if (!f.getBoolean(t)) {
            throw new AssertionError("getBoolean should return the current value true");
        }

        f.setBoolean(t, false);
        if (f.getBoolean(t)) {
            throw new AssertionError("getBoolean should observe the value set by setBoolean");
        }
    }
}
