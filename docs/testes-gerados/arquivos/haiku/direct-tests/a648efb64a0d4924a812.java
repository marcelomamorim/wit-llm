/*
 * @test
 * @summary Executable.getAnnotationsByType returns directly and indirectly present annotations and rejects null
 * @run main ExecutableWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

public class ExecutableWitupTest {

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
        int value() default 0;
    }

    static class Target {
        @Tag("a") @Tag("b") @Single(3)
        public void repeated() {}

        public void plain() {}
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Method repeated = Target.class.getDeclaredMethod("repeated");
        Method plain = Target.class.getDeclaredMethod("plain");

        // null argument must be rejected
        try {
            repeated.getAnnotationsByType(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        // repeated annotation returns all occurrences in declaration order
        Tag[] tags = repeated.getAnnotationsByType(Tag.class);
        check(tags.length == 2, "expected 2 Tag, got " + tags.length);
        check(tags[0].value().equals("a"), "first Tag should be a");
        check(tags[1].value().equals("b"), "second Tag should be b");

        // single annotation is returned as an array of length 1
        Single[] singles = repeated.getAnnotationsByType(Single.class);
        check(singles.length == 1, "expected 1 Single, got " + singles.length);
        check(singles[0].value() == 3, "Single value should be 3");

        // container annotation is returned when it is the compiled form
        Tags[] containers = repeated.getAnnotationsByType(Tags.class);
        check(containers.length == 1, "expected 1 Tags container, got " + containers.length);
        check(containers[0].value().length == 2, "container should hold 2 Tag");

        // method without annotations of the requested type returns empty array
        Annotation[] none = plain.getAnnotationsByType(Tag.class);
        check(none.length == 0, "expected no Tag on plain, got " + none.length);

        // result array of the requested type is non-null and empty for absent annotations
        Single[] noSingle = plain.getAnnotationsByType(Single.class);
        check(noSingle != null && noSingle.length == 0, "expected empty Single array");
    }
}
