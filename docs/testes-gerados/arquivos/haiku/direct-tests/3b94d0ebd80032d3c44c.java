/*
 * @test
 * @summary Field.getLong on a static Object field must throw IllegalArgumentException via UnsafeStaticObjectFieldAccessorImpl.getLong
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static Object refField = "value";
    public static Object nullRefField = null;

    public static void main(String[] args) throws Exception {
        testGetLongOnStaticObjectFieldThrows();
        testGetLongOnStaticObjectFieldWithNullValueThrows();
        testGetLongIgnoresReceiverAndStillThrows();
    }

    static void testGetLongOnStaticObjectFieldThrows() throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("refField");
        boolean thrown = false;
        try {
            f.getLong(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getLong on static Object field");
        }
    }

    static void testGetLongOnStaticObjectFieldWithNullValueThrows() throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("nullRefField");
        boolean thrown = false;
        try {
            f.getLong(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getLong on null-valued static Object field");
        }
    }

    static void testGetLongIgnoresReceiverAndStillThrows() throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("refField");
        boolean thrown = false;
        try {
            f.getLong(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getLong with non-null receiver");
        }
    }
}
