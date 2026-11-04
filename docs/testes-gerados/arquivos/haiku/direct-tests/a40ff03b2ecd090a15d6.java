/*
 * @test
 * @summary Tests observable behavior of Class.getAnnotation(Class)
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {
        String value() default "x";
    }

    @Retention(RetentionPolicy.CLASS)
    @interface ClassOnly {
    }

    @Marker("hello")
    @ClassOnly
    static class Annotated {
    }

    @Marker
    static class Defaulted {
    }

    static class Plain {
    }

    public static void main(String[] args) throws Exception {
        testNullArgumentThrows();
        testPresentAnnotationReturned();
        testDefaultValueObserved();
        testAbsentAnnotationReturnsNull();
        testNonRuntimeAnnotationNotVisible();
    }

    static void testNullArgumentThrows() {
        try {
            Annotated.class.getAnnotation(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null annotationClass");
    }

    static void testPresentAnnotationReturned() {
        Marker m = Annotated.class.getAnnotation(Marker.class);
        check(m != null, "Marker should be present on Annotated");
        check("hello".equals(m.value()), "Marker.value() should be \"hello\"");
        check(m.annotationType() == Marker.class, "annotationType should be Marker");
        Annotation a = m;
        check(a instanceof Marker, "returned annotation should be instance of Marker");
    }

    static void testDefaultValueObserved() {
        Marker m = Defaulted.class.getAnnotation(Marker.class);
        check(m != null, "Marker should be present on Defaulted");
        check("x".equals(m.value()), "default value should be \"x\"");
    }

    static void testAbsentAnnotationReturnsNull() {
        check(Plain.class.getAnnotation(Marker.class) == null,
              "absent annotation should return null");
    }

    static void testNonRuntimeAnnotationNotVisible() {
        check(Annotated.class.getAnnotation(ClassOnly.class) == null,
              "CLASS-retention annotation should not be visible at runtime");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
