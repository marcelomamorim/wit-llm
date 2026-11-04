/*
 * @test
 * @summary Verifies Class.getAnnotationsByType: null argument rejection, empty result for unannotated classes, and repeatable annotation handling
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Tags.class)
    @interface Tag {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Tags {
        Tag[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Single {
    }

    @Tag("a")
    @Tag("b")
    @Single
    static class Annotated {
    }

    static class Plain {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentThrowsNPE() {
        boolean thrown = false;
        try {
            Annotated.class.getAnnotationsByType(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null annotationClass");
    }

    static void testUnannotatedClassReturnsEmptyArray() {
        Annotation[] result = Plain.class.getAnnotationsByType(Tag.class);
        check(result != null, "result must not be null");
        check(result.length == 0, "expected empty array for unannotated class, got " + result.length);
    }

    static void testRepeatableAnnotationsReturnedInOrder() {
        Tag[] result = Annotated.class.getAnnotationsByType(Tag.class);
        check(result.length == 2, "expected 2 Tag annotations, got " + result.length);
        check("a".equals(result[0].value()), "first Tag value should be a");
        check("b".equals(result[1].value()), "second Tag value should be b");
    }

    static void testSingleAnnotationReturnedOnce() {
        Single[] result = Annotated.class.getAnnotationsByType(Single.class);
        check(result.length == 1, "expected 1 Single annotation, got " + result.length);
    }

    static void testAbsentAnnotationTypeReturnsEmpty() {
        Single[] result = Plain.class.getAnnotationsByType(Single.class);
        check(result.length == 0, "expected empty array for absent annotation type");
    }

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testUnannotatedClassReturnsEmptyArray();
        testRepeatableAnnotationsReturnedInOrder();
        testSingleAnnotationReturnedOnce();
        testAbsentAnnotationTypeReturnsEmpty();
    }
}
