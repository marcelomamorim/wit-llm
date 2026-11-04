/*
 * @test
 * @summary Verify that Field.getInt on a final boolean field throws IllegalArgumentException, reaching UnsafeQualifiedBooleanFieldAccessorImpl.getInt through the public reflection API
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public final boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedBooleanFieldAccessorImplWitupTest holder =
            new UnsafeQualifiedBooleanFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getField("flag");

        boolean thrown = false;
        try {
            field.getInt(holder);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getInt on boolean field");
        }

        thrown = false;
        try {
            field.getInt(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getInt with null receiver");
        }
    }
}
