/*
 * @test
 * @summary Verify that Field.getDouble on a volatile boolean field, served by UnsafeQualifiedBooleanFieldAccessorImpl, throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedBooleanFieldAccessorImplWitupTest target =
            new UnsafeQualifiedBooleanFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        expectIllegalArgument(field, target);
        expectIllegalArgument(field, null);

        if (!field.getBoolean(target)) {
            throw new RuntimeException("getBoolean returned unexpected value");
        }
    }

    private static void expectIllegalArgument(Field field, Object obj) {
        try {
            double value = field.getDouble(obj);
            throw new RuntimeException("Expected IllegalArgumentException but got " + value);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
