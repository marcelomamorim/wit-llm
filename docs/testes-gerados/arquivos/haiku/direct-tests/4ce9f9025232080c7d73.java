/*
 * @test
 * @summary Parameter.getAnnotationsByType returns directly and indirectly present annotations and rejects null
 * @run main ParameterWitupTest
 */

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {}

    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Tags.class)
    @interface Tag {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Tags {
        Tag[] value();
    }

    static void target(@Marker @Tag("a") @Tag("b") String s, int x) {}

    static void plain(int x) {}

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Method m = ParameterWitupTest.class.getDeclaredMethod("target", String.class, int.class);
        Parameter p0 = m.getParameters()[0];
        Parameter p1 = m.getParameters()[1];
        Method plainMethod = ParameterWitupTest.class.getDeclaredMethod("plain", int.class);
        Parameter q = plainMethod.getParameters()[0];

        Marker[] markers = p0.getAnnotationsByType(Marker.class);
        check(markers.length == 1, "expected one Marker on p0, got " + markers.length);
        check(markers.getClass().getComponentType() == Marker.class,
              "unexpected component type of returned array");

        Tag[] tags = p0.getAnnotationsByType(Tag.class);
        check(tags.length == 2, "expected two repeated Tag annotations, got " + tags.length);
        check("a".equals(tags[0].value()) && "b".equals(tags[1].value()),
              "unexpected Tag values order");

        check(p1.getAnnotationsByType(Marker.class).length == 0,
              "expected no Marker on parameter without annotations");
        check(p1.getAnnotationsByType(Tag.class).length == 0,
              "expected no Tag on parameter without annotations");
        check(q.getAnnotationsByType(Tag.class).length == 0,
              "expected no Tag on plain parameter");

        try {
            p0.getAnnotationsByType(null);
            throw new RuntimeException("expected NullPointerException for null annotation class");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull rejects null
        }
    }
}
