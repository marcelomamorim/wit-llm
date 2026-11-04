/*
 * @test
 * @summary Basic observable behavior of Field.getAnnotation
 * @run main FieldWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public class FieldWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Tag {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Other {
    }

    @Tag("alpha")
    public int annotated;

    public int plain;

    public static void main(String[] args) throws Exception {
        Field annotatedField = FieldWitupTest.class.getDeclaredField("annotated");
        Tag tag = annotatedField.getAnnotation(Tag.class);
        check(tag != null, "expected @Tag on annotated field");
        check("alpha".equals(tag.value()), "unexpected @Tag value");

        check(annotatedField.getAnnotation(Other.class) == null,
              "absent annotation type must return null");

        Field plainField = FieldWitupTest.class.getDeclaredField("plain");
        check(plainField.getAnnotation(Tag.class) == null,
              "unannotated field must return null");

        boolean npe = false;
        try {
            annotatedField.getAnnotation(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null annotation class must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
