/*
 * @test
 * @summary Field.getAnnotation returns present annotations, null for absent ones, and throws NullPointerException for a null annotation class
 * @run main FieldWitupTest
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class FieldWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Marker {
        String value() default "";
    }

    @Marker("x")
    public int annotated;

    public int plain;

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field annotatedField = FieldWitupTest.class.getDeclaredField("annotated");
        Field plainField = FieldWitupTest.class.getDeclaredField("plain");

        Marker m = annotatedField.getAnnotation(Marker.class);
        check(m != null, "expected annotation present on annotated field");
        check("x".equals(m.value()), "unexpected annotation value: " + m.value());

        check(plainField.getAnnotation(Marker.class) == null,
              "expected null for absent annotation");

        Class<Marker> nullClass = null;
        boolean npe = false;
        try {
            annotatedField.getAnnotation(nullClass);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "expected NullPointerException for null annotationClass");
    }
}
