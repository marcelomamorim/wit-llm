/*
 * @test
 * @summary Verify that Field.getByte on an Object-typed static field throws IllegalArgumentException, as the internal accessor getByte does
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static final Object FINAL_OBJ = "value";
    public static volatile Object VOLATILE_OBJ = "other";

    public static void main(String[] args) throws Exception {
        Field finalField = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_OBJ");
        expectIllegalArgument(finalField, null);
        expectIllegalArgument(finalField, new Object());

        Field volatileField = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("VOLATILE_OBJ");
        expectIllegalArgument(volatileField, null);
        expectIllegalArgument(volatileField, "x");
    }

    private static void expectIllegalArgument(Field field, Object obj) {
        try {
            field.getByte(obj);
        } catch (IllegalArgumentException e) {
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException for " + field.getName(), e);
        }
        throw new RuntimeException("expected IllegalArgumentException for " + field.getName());
    }
}
