/*
 * @test
 * @summary Verify that Field.getShort on a static float field throws IllegalArgumentException, reaching the UnsafeQualifiedStaticFloatFieldAccessorImpl.getShort path
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static final float FINAL_F = 1.5f;

    public static float mutableF = 2.5f;

    public static void main(String[] args) throws Exception {
        Field finalField = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getField("FINAL_F");
        if (finalField.getFloat(null) != 1.5f) {
            throw new RuntimeException("unexpected float value for FINAL_F");
        }
        expectIllegalArgument(finalField, null);
        expectIllegalArgument(finalField, new Object());

        Field mutableField = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getField("mutableF");
        expectIllegalArgument(mutableField, null);
        expectIllegalArgument(mutableField, new Object());
    }

    static void expectIllegalArgument(Field field, Object obj) {
        try {
            field.getShort(obj);
        } catch (IllegalArgumentException e) {
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException", e);
        }
        throw new RuntimeException("expected IllegalArgumentException from getShort on float field");
    }
}
