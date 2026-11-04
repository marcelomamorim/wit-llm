/*
 * @test
 * @summary Test Class.getAnnotation() method for various annotations.
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.Class;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface SampleAnnotation {
        String value();
    }

    @SampleAnnotation("test")
    public static class AnnotatedClass {
    }

    public static void main(String[] args) {
        try {
            Class<?> cls = AnnotatedClass.class;
            Annotation annotation = cls.getAnnotation(SampleAnnotation.class);
            if (annotation == null) {
                throw new AssertionError("Annotation not found");
            }
            if (!(annotation instanceof SampleAnnotation)) {
                throw new AssertionError("Annotation type mismatch");
            }
            SampleAnnotation sa = (SampleAnnotation) annotation;
            if (!"test".equals(sa.value())) {
                throw new AssertionError("Annotation value mismatch: " + sa.value());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
