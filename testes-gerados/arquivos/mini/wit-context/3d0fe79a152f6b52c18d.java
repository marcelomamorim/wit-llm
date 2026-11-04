/*
 * @test
 * @summary Test java.lang.reflect.Field.getAnnotation(Class<T>) for null argument and normal usage
 * @run main FieldWitupTest
 */

import java.lang.reflect.Field;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Annotation;
import java.util.Objects;

public class FieldWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String value() default "test";
    }

    @TestAnnotation("present")
    private int annotatedField;

    public static void main(String[] args) throws Exception {
        FieldWitupTest test = new FieldWitupTest();
        test.testGetAnnotationWithNull();
        test.testGetAnnotationWithPresentAnnotation();
        test.testGetAnnotationWithAbsentAnnotation();
    }

    public void testGetAnnotationWithNull() throws Exception {
        Field f = FieldWitupTest.class.getDeclaredField("annotatedField");
        try {
            f.getAnnotation(null);
            throw new RuntimeException("Expected NullPointerException for null annotationClass");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public void testGetAnnotationWithPresentAnnotation() throws Exception {
        Field f = FieldWitupTest.class.getDeclaredField("annotatedField");
        TestAnnotation ann = f.getAnnotation(TestAnnotation.class);
        if (ann == null) {
            throw new RuntimeException("Expected non-null annotation instance");
        }
        if (!"present".equals(ann.value())) {
            throw new RuntimeException("Annotation value mismatch: " + ann.value());
        }
    }

    public void testGetAnnotationWithAbsentAnnotation() throws Exception {
        Field f = FieldWitupTest.class.getDeclaredField("annotatedField");
        Deprecated dep = f.getAnnotation(Deprecated.class);
        if (dep != null) {
            throw new RuntimeException("Expected null for absent annotation");
        }
    }
}
