/*
 * @test
 * @summary Field.getLong on float fields must throw IllegalArgumentException via the Unsafe-qualified float accessor
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    volatile float vf = 1.5f;
    float f = 2.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest obj = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();

        Field volatileField = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("vf");
        expectIllegalArgument(volatileField, obj);

        Field plainField = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("f");
        expectIllegalArgument(plainField, obj);

        if (volatileField.getFloat(obj) != 1.5f) {
            throw new RuntimeException("volatile float value changed unexpectedly");
        }
        if (plainField.getFloat(obj) != 2.5f) {
            throw new RuntimeException("float value changed unexpectedly");
        }
    }

    static void expectIllegalArgument(Field field, Object obj) {
        try {
            long v = field.getLong(obj);
            throw new RuntimeException("expected IllegalArgumentException, got long " + v);
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException", e);
        }
    }
}
