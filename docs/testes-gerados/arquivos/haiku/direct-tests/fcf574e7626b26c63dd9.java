/*
 * @test
 * @summary Verify that Field.getInt on an Object-typed static field throws IllegalArgumentException, and that get returns the stored value
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static final Object QUALIFIED = "value";
    public static Object plain = "other";

    public static void main(String[] args) throws Exception {
        Field qualified = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getField("QUALIFIED");
        Object v = qualified.get(null);
        if (!"value".equals(v)) {
            throw new RuntimeException("unexpected get value: " + v);
        }
        try {
            qualified.getInt(null);
            throw new RuntimeException("expected IllegalArgumentException for getInt on qualified static Object field");
        } catch (IllegalArgumentException expected) {
        }

        Field plainField = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getField("plain");
        try {
            plainField.getInt(null);
            throw new RuntimeException("expected IllegalArgumentException for getInt on static Object field");
        } catch (IllegalArgumentException expected) {
        }
    }
}
