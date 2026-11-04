/*
 * @test
 * @summary Verify Parameter.getDeclaredAnnotation returns the runtime annotation present on a parameter and null when absent
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

    public static void target(@Tag("first") String a, String b) {
    }

    public static void main(String[] args) throws Exception {
        Method m = ParameterWitupTest.class.getDeclaredMethod("target", String.class, String.class);
        Parameter[] ps = m.getParameters();

        Tag t = ps[0].getDeclaredAnnotation(Tag.class);
        if (t == null) {
            throw new RuntimeException("expected @Tag on first parameter");
        }
        if (!"first".equals(t.value())) {
            throw new RuntimeException("unexpected value: " + t.value());
        }

        if (ps[1].getDeclaredAnnotation(Tag.class) != null) {
            throw new RuntimeException("unannotated parameter must return null");
        }

        if (!t.equals(ps[0].getAnnotation(Tag.class))) {
            throw new RuntimeException("getDeclaredAnnotation must match getAnnotation for parameter");
        }
    }
}
