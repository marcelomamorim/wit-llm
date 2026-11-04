/*
 * @test
 * @summary Verify that Field.getByte on a static volatile long field is rejected by the qualified static long accessor, while Field.get still returns the boxed long value
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    static volatile long value = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getByte(null) should throw IllegalArgumentException");
        }

        thrown = false;
        try {
            f.getByte(new Object());
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getByte(Object) should throw IllegalArgumentException");
        }

        Object boxed = f.get(null);
        if (!(boxed instanceof Long) || ((Long) boxed).longValue() != 42L) {
            throw new AssertionError("get(null) should return Long 42, got " + boxed);
        }
    }
}
