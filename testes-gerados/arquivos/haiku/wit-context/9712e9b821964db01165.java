/*
 * @test
 * @summary Verify that Field.getLong on a static volatile boolean field throws IllegalArgumentException through the qualified static boolean accessor
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    static volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        expectGetLongIllegalArgument(field, null);
        expectGetLongIllegalArgument(field, new Object());
    }

    private static void expectGetLongIllegalArgument(Field field, Object obj) throws Exception {
        try {
            long value = field.getLong(obj);
            throw new AssertionError("expected IllegalArgumentException but got " + value);
        } catch (IllegalArgumentException expected) {
        }
    }
}
