/*
 * @test
 * @summary Verify that Field.getShort on a volatile long field is rejected by the qualified long field accessor
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public volatile long value = 42L;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedLongFieldAccessorImplWitupTest holder = new UnsafeQualifiedLongFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedLongFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            field.getShort(holder);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getShort on long field did not throw IllegalArgumentException");
        }

        Object boxed = field.get(holder);
        if (!(boxed instanceof Long) || ((Long) boxed).longValue() != 42L) {
            throw new AssertionError("get returned unexpected value: " + boxed);
        }

        thrown = false;
        try {
            field.getShort(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getShort with null receiver did not throw IllegalArgumentException");
        }
    }
}
