/*
 * @test
 * @summary Basic observable behavior of AccessibleObject.getDeclaredAnnotationsByType via public reflected objects
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

    @Marker("field")
    public String annotatedField;

    public String plainField;

    @Marker("method")
    public static void annotatedMethod() {
    }

    public static void plainMethod() {
    }

    public static void main(String[] args) throws Exception {
        Method annotated = AccessibleObjectWitupTest.class.getMethod("annotatedMethod");
        Method plain = AccessibleObjectWitupTest.class.getMethod("plainMethod");
        Field annotatedF = AccessibleObjectWitupTest.class.getField("annotatedField");
        Field plainF = AccessibleObjectWitupTest.class.getField("plainField");

        Marker[] fromMethod = annotated.getDeclaredAnnotationsByType(Marker.class);
        if (fromMethod.length != 1) {
            throw new AssertionError("expected 1 annotation on method, got " + fromMethod.length);
        }
        if (!"method".equals(fromMethod[0].value())) {
            throw new AssertionError("unexpected value: " + fromMethod[0].value());
        }

        Marker[] fromField = annotatedF.getDeclaredAnnotationsByType(Marker.class);
        if (fromField.length != 1 || !"field".equals(fromField[0].value())) {
            throw new AssertionError("unexpected field annotations");
        }

        Marker[] none = plain.getDeclaredAnnotationsByType(Marker.class);
        if (none == null || none.length != 0) {
            throw new AssertionError("expected empty array for unannotated method");
        }

        Marker[] noneField = plainF.getDeclaredAnnotationsByType(Marker.class);
        if (noneField == null || noneField.length != 0) {
            throw new AssertionError("expected empty array for unannotated field");
        }

        Marker[] viaGet = annotated.getAnnotationsByType(Marker.class);
        if (viaGet.length != fromMethod.length) {
            throw new AssertionError("declared and inherited lookup differ for method");
        }
    }
}
