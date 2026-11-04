/*
 * @test
 * @summary Verify that Field.getBoolean on static Object fields throws IllegalArgumentException, exercising the UnsafeQualifiedStaticObjectFieldAccessorImpl.getBoolean path
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    static final Object FINAL_REF = "value";
    static Object ref = "value";

    public static void main(String[] args) throws Exception {
        Field finalField = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_REF");
        Field field = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("ref");

        expectIllegalArgument(finalField, null);
        expectIllegalArgument(finalField, new Object());
        expectIllegalArgument(field, null);
        expectIllegalArgument(field, "other");
    }

    private static void expectIllegalArgument(Field f, Object target) throws IllegalAccessException {
        try {
            f.getBoolean(target);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for getBoolean on field " + f.getName());
    }
}
