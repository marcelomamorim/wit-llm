/*
 * @test
 * @summary Checks Parameter.getDeclaredAnnotation for null argument, present and absent annotations
 * @run main ParameterWitupTest
 */
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Marker {
        String value() default "";
    }

    static void target(@Marker("x") String annotated, String plain) {
    }

    public static void main(String[] args) throws Exception {
        Method m = ParameterWitupTest.class.getDeclaredMethod("target", String.class, String.class);
        Parameter annotated = m.getParameters()[0];
        Parameter plain = m.getParameters()[1];

        boolean npe = false;
        try {
            annotated.getDeclaredAnnotation((Class<Marker>) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("expected NullPointerException for null annotationClass");
        }

        Marker present = annotated.getDeclaredAnnotation(Marker.class);
        if (present == null) {
            throw new AssertionError("expected Marker annotation on annotated parameter");
        }
        if (!"x".equals(present.value())) {
            throw new AssertionError("unexpected Marker value: " + present.value());
        }

        if (plain.getDeclaredAnnotation(Marker.class) != null) {
            throw new AssertionError("expected null for unannotated parameter");
        }

        if (annotated.getDeclaredAnnotation(Deprecated.class) != null) {
            throw new AssertionError("expected null for absent annotation type");
        }
    }
}
