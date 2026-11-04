/*
 * @test
 * @summary Verify Parameter.getDeclaredAnnotationsByType returns directly present and repeatable annotations on method parameters
 * @run main ParameterWitupTest
 */

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Plain {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Tags.class)
    @interface Tag {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Tags {
        Tag[] value();
    }

    static void target(@Plain("p") @Tag("a") @Tag("b") String s, int n) {
    }

    static void bare(String s) {
    }

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Parameter p0 = ParameterWitupTest.class
                .getDeclaredMethod("target", String.class, int.class)
                .getParameters()[0];
        Parameter p1 = ParameterWitupTest.class
                .getDeclaredMethod("target", String.class, int.class)
                .getParameters()[1];
        Parameter bp = ParameterWitupTest.class
                .getDeclaredMethod("bare", String.class)
                .getParameters()[0];

        Plain[] plain = p0.getDeclaredAnnotationsByType(Plain.class);
        check(plain != null, "plain result must not be null");
        check(plain.length == 1, "expected 1 Plain annotation, got " + plain.length);
        if (plain.length == 1) {
            check("p".equals(plain[0].value()), "Plain value should be p");
        }

        Tag[] tags = p0.getDeclaredAnnotationsByType(Tag.class);
        check(tags != null, "tags result must not be null");
        check(tags.length == 2, "expected 2 repeated Tag annotations, got " + tags.length);
        if (tags.length == 2) {
            check("a".equals(tags[0].value()), "first Tag value should be a");
            check("b".equals(tags[1].value()), "second Tag value should be b");
        }

        check(p1.getDeclaredAnnotationsByType(Plain.class).length == 0,
              "unannotated parameter should yield no Plain annotations");
        check(p1.getDeclaredAnnotationsByType(Tag.class).length == 0,
              "unannotated parameter should yield no Tag annotations");

        check(bp.getDeclaredAnnotationsByType(Tag.class).length == 0,
              "bare parameter should yield no Tag annotations");
        check(bp.getDeclaredAnnotationsByType(Plain.class).length == 0,
              "bare parameter should yield no Plain annotations");

        check(p0.getDeclaredAnnotationsByType(Tag.class).length
              == p0.getAnnotationsByType(Tag.class).length,
              "declared and general lookup should agree for parameters");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
