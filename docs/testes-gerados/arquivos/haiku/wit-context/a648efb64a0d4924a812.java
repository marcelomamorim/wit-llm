/*
 * @test
 * @summary Verify Executable.getAnnotationsByType rejects null and returns annotations observably
 * @run main ExecutableWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

public class ExecutableWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Tag {
        String value();
    }

    @Tag("x")
    public void tagged() {
    }

    public void plain() {
    }

    public static void main(String[] args) throws Exception {
        Method tagged = ExecutableWitupTest.class.getDeclaredMethod("tagged");
        Method plain = ExecutableWitupTest.class.getDeclaredMethod("plain");

        boolean npe = false;
        try {
            tagged.getAnnotationsByType(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null annotationClass");
        }

        npe = false;
        try {
            plain.getAnnotationsByType(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null annotationClass on unannotated method");
        }

        Tag[] tags = tagged.getAnnotationsByType(Tag.class);
        if (tags == null || tags.length != 1) {
            throw new RuntimeException("expected exactly one Tag annotation");
        }
        if (!"x".equals(tags[0].value())) {
            throw new RuntimeException("unexpected Tag value: " + tags[0].value());
        }

        Tag[] none = plain.getAnnotationsByType(Tag.class);
        if (none == null || none.length != 0) {
            throw new RuntimeException("expected empty array for unannotated method");
        }
    }
}
