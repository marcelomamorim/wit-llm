/*
 * @test
 * @summary Verifies AccessibleObject.getDeclaredAnnotationsByType on Field and Method objects
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AccessibleObjectWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Tag {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Marks.class)
    @interface Mark {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marks {
        Mark[] value();
    }

    static class Holder {
        @Tag("a")
        public int tagged;

        public int plain;

        @Mark("x")
        @Mark("y")
        public void marked() {
        }

        public void unmarked() {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field tagged = Holder.class.getDeclaredField("tagged");
        Tag[] tags = tagged.getDeclaredAnnotationsByType(Tag.class);
        check(tags.length == 1, "expected one Tag on field, got " + tags.length);
        check("a".equals(tags[0].value()), "unexpected Tag value: " + tags[0].value());

        Field plain = Holder.class.getDeclaredField("plain");
        check(plain.getDeclaredAnnotationsByType(Tag.class).length == 0,
              "expected no Tag on plain field");

        Method marked = Holder.class.getDeclaredMethod("marked");
        Mark[] marks = marked.getDeclaredAnnotationsByType(Mark.class);
        check(marks.length == 2, "expected two repeated Mark annotations, got " + marks.length);
        check("x".equals(marks[0].value()), "unexpected first Mark value: " + marks[0].value());
        check("y".equals(marks[1].value()), "unexpected second Mark value: " + marks[1].value());

        Method unmarked = Holder.class.getDeclaredMethod("unmarked");
        check(unmarked.getDeclaredAnnotationsByType(Mark.class).length == 0,
              "expected no Mark on unmarked method");

        Mark[] viaGetAnnotations = marked.getAnnotationsByType(Mark.class);
        check(viaGetAnnotations.length == marks.length,
              "getDeclaredAnnotationsByType should match getAnnotationsByType for methods");
    }
}
