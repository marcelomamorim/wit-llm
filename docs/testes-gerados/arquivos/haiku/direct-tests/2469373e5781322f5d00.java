/*
 * @test
 * @summary Verify AccessibleObject.getDeclaredAnnotation returns the annotation present on reflected members and null when absent
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AccessibleObjectWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {
        String value() default "";
    }

    @Marker("method")
    public void annotated() {
    }

    public void plain() {
    }

    @Marker("field")
    public int annotatedField;

    public int plainField;

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Method annotated = AccessibleObjectWitupTest.class.getDeclaredMethod("annotated");
        Method plain = AccessibleObjectWitupTest.class.getDeclaredMethod("plain");
        Field annotatedField = AccessibleObjectWitupTest.class.getDeclaredField("annotatedField");
        Field plainField = AccessibleObjectWitupTest.class.getDeclaredField("plainField");

        Marker m = annotated.getDeclaredAnnotation(Marker.class);
        check(m != null, "expected Marker on annotated method");
        check("method".equals(m.value()), "unexpected value on method: " + m.value());
        check(m.equals(annotated.getAnnotation(Marker.class)),
              "getDeclaredAnnotation should match getAnnotation for methods");

        check(plain.getDeclaredAnnotation(Marker.class) == null,
              "expected null for unannotated method");

        Marker f = annotatedField.getDeclaredAnnotation(Marker.class);
        check(f != null, "expected Marker on annotated field");
        check("field".equals(f.value()), "unexpected value on field: " + f.value());

        check(plainField.getDeclaredAnnotation(Marker.class) == null,
              "expected null for unannotated field");
    }
}
