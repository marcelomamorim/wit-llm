/*
 * @test
 * @summary Verifies that Field.getBoolean on a static volatile int field is rejected through the qualified static integer accessor
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static volatile int VALUE = 42;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("VALUE");

        boolean thrown = false;
        try {
            field.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean on int field");
        }

        Object boxed = field.get(null);
        if (!(boxed instanceof Integer) || ((Integer) boxed).intValue() != 42) {
            throw new RuntimeException("Expected Integer 42 from get, got: " + boxed);
        }
    }
}
