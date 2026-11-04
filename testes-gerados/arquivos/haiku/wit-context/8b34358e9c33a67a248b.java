/*
 * @test
 * @summary Verifies that Field.getBoolean on a qualified static int field throws IllegalArgumentException, reaching UnsafeQualifiedStaticIntegerFieldAccessorImpl.getBoolean through the public reflection API
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static volatile int value = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean on int field");
        }

        Object boxed = f.get(null);
        if (!Integer.valueOf(42).equals(boxed)) {
            throw new RuntimeException("Unexpected value from get: " + boxed);
        }
    }
}
