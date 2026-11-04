/*
 * @test
 * @summary Verify Class.getDeclaredAnnotationsByType rejects null and returns declared annotations
 * @run main ClassWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {
    }

    @Marker
    static class Annotated {
    }

    static class Plain {
    }

    public static void main(String[] args) {
        testNullAnnotationClassThrowsNpe();
        testPlainClassReturnsEmptyArray();
        testAnnotatedClassReturnsDeclaredAnnotation();
    }

    static void testNullAnnotationClassThrowsNpe() {
        boolean thrown = false;
        try {
            Annotated.class.getDeclaredAnnotationsByType(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null annotationClass");
        }
    }

    static void testPlainClassReturnsEmptyArray() {
        Marker[] result = Plain.class.getDeclaredAnnotationsByType(Marker.class);
        if (result == null || result.length != 0) {
            throw new AssertionError("Expected empty array for class without annotation");
        }
    }

    static void testAnnotatedClassReturnsDeclaredAnnotation() {
        Marker[] result = Annotated.class.getDeclaredAnnotationsByType(Marker.class);
        if (result == null || result.length != 1) {
            throw new AssertionError("Expected exactly one declared Marker annotation");
        }
    }
}
