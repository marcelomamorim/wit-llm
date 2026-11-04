/*
 * @test
 * @summary Verify that Field.getShort on a static int field throws IllegalArgumentException, as implemented by UnsafeQualifiedStaticIntegerFieldAccessorImpl.getShort
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {
    public static final int FINAL_INT = 42;
    public static int plainInt = 7;

    public static void main(String[] args) throws Exception {
        Field finalField = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_INT");
        Field plainField = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("plainInt");

        Object boxed = finalField.get(null);
        if (!(boxed instanceof Integer) || ((Integer) boxed).intValue() != 42) {
            throw new RuntimeException("Unexpected get() result: " + boxed);
        }

        try {
            finalField.getShort(null);
            throw new RuntimeException("getShort on static final int did not throw");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            plainField.getShort(null);
            throw new RuntimeException("getShort on static int did not throw");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
