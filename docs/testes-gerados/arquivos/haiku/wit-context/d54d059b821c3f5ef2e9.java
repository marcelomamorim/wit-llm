/*
 * @test
 * @summary Witup generated test for Parameter.getDeclaredAnnotationsByType null handling and basic behavior
 * @run main ParameterWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Tag {
        String value() default "";
    }

    static void sample(@Tag("x") String s, String t) {}

    public static void main(String[] args) throws Exception {
        Method m = ParameterWitupTest.class.getDeclaredMethod("sample", String.class, String.class);
        Parameter[] params = m.getParameters();

        try {
            params[0].getDeclaredAnnotationsByType(null);
            throw new AssertionError("Expected NullPointerException for null annotationClass");
        } catch (NullPointerException expected) {
        }

        Tag[] annotated = params[0].getDeclaredAnnotationsByType(Tag.class);
        if (annotated.length != 1) {
            throw new AssertionError("Expected 1 Tag annotation, got " + annotated.length);
        }
        if (!"x".equals(annotated[0].value())) {
            throw new AssertionError("Unexpected Tag value: " + annotated[0].value());
        }

        Tag[] unannotated = params[1].getDeclaredAnnotationsByType(Tag.class);
        if (unannotated.length != 0) {
            throw new AssertionError("Expected no Tag annotations, got " + unannotated.length);
        }
    }
}
