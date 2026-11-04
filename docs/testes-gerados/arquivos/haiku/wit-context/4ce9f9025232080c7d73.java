/*
 * @test
 * @summary Witup tests for Parameter.getAnnotationsByType: null argument rejection and annotation lookup
 * @run main ParameterWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Tag {
        String value();
    }

    public void annotated(@Tag("x") String a, String b) {
    }

    public static void main(String[] args) throws Exception {
        Method m = ParameterWitupTest.class.getMethod("annotated", String.class, String.class);
        Parameter[] ps = m.getParameters();
        Parameter withTag = ps[0];
        Parameter withoutTag = ps[1];

        try {
            withTag.getAnnotationsByType((Class<Tag>) null);
            throw new AssertionError("expected NullPointerException for null annotationClass");
        } catch (NullPointerException expected) {
        }

        Tag[] tags = withTag.getAnnotationsByType(Tag.class);
        if (tags.length != 1) {
            throw new AssertionError("expected 1 Tag annotation, got " + tags.length);
        }
        if (!"x".equals(tags[0].value())) {
            throw new AssertionError("unexpected Tag value: " + tags[0].value());
        }

        Tag[] none = withoutTag.getAnnotationsByType(Tag.class);
        if (none.length != 0) {
            throw new AssertionError("expected no Tag annotations, got " + none.length);
        }
    }
}
