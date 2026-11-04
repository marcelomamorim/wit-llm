/*
 * @test
 * @summary Field.getInt on a volatile Object field must throw IllegalArgumentException via the unsafe-qualified object accessor, while get still returns the reference
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public volatile Object volatileRef = "x";

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest holder = new UnsafeQualifiedObjectFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getField("volatileRef");

        Object value = f.get(holder);
        if (!"x".equals(value)) {
            throw new RuntimeException("get returned unexpected value: " + value);
        }

        boolean thrown = false;
        try {
            f.getInt(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException from getInt on Object field");
        }
    }
}
