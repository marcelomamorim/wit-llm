/*
 * @test
 * @summary Verifies that Field.getInt on object-typed fields throws IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public Object objectValue = "x";
    public final Object finalObjectValue = "y";

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest t =
            new UnsafeQualifiedObjectFieldAccessorImplWitupTest();

        Field f = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getField("objectValue");
        expectIllegalArgument(f, t);

        Field g = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getField("finalObjectValue");
        expectIllegalArgument(g, t);

        if (!"x".equals(f.get(t))) {
            throw new RuntimeException("get(Object) returned unexpected value");
        }
    }

    private static void expectIllegalArgument(Field field, Object target) {
        try {
            field.getInt(target);
        } catch (IllegalArgumentException expected) {
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected IllegalAccessException", e);
        }
        throw new RuntimeException("Expected IllegalArgumentException for field " + field.getName());
    }
}
