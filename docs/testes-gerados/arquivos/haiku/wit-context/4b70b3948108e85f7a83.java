/*
 * @test
 * @summary Verify Field.getAnnotationsByType rejects null and returns directly present annotations
 * @run main FieldWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public class FieldWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {
        String value() default "";
    }

    static class Holder {
        @Marker("x")
        public int annotated;

        public int plain;
    }

    public static void main(String[] args) throws Exception {
        Field annotated = Holder.class.getDeclaredField("annotated");
        Field plain = Holder.class.getDeclaredField("plain");

        testNullArgumentThrowsNPE(annotated);
        testPresentAnnotation(annotated);
        testAbsentAnnotation(plain);
    }

    static void testNullArgumentThrowsNPE(Field field) {
        try {
            field.getAnnotationsByType(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("Expected NullPointerException for null annotationClass");
    }

    static void testPresentAnnotation(Field field) {
        Marker[] result = field.getAnnotationsByType(Marker.class);
        if (result == null) {
            throw new AssertionError("Result must not be null");
        }
        if (result.length != 1) {
            throw new AssertionError("Expected 1 annotation but was " + result.length);
        }
        if (!"x".equals(result[0].value())) {
            throw new AssertionError("Unexpected value: " + result[0].value());
        }
    }

    static void testAbsentAnnotation(Field field) {
        Marker[] result = field.getAnnotationsByType(Marker.class);
        if (result == null || result.length != 0) {
            throw new AssertionError("Expected empty array for unannotated field");
        }
    }
}
