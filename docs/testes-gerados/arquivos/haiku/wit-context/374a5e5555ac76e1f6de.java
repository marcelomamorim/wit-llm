/*
 * @test
 * @summary Field.getDouble on a reference-typed field must throw IllegalArgumentException, exercising the Object field accessor path
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public volatile Object ref = "value";
    public final Object finalRef = "final";

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest t = new UnsafeQualifiedObjectFieldAccessorImplWitupTest();

        Field volatileField = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getDeclaredField("ref");
        Field finalField = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getDeclaredField("finalRef");

        check("value".equals(volatileField.get(t)), "get on volatile Object field must return the stored value");
        check("final".equals(finalField.get(t)), "get on final Object field must return the stored value");

        expectIAE(volatileField, t, "getDouble on volatile Object field");
        expectIAE(finalField, t, "getDouble on final Object field");
    }

    private static void expectIAE(Field f, Object target, String what) {
        try {
            f.getDouble(target);
        } catch (IllegalArgumentException expected) {
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(what + ": unexpected IllegalAccessException", e);
        }
        throw new RuntimeException(what + ": expected IllegalArgumentException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
