/*
 * @test
 * @summary Verifies getAnnotationsByType throws NullPointerException for a null annotation class and returns public observable results otherwise
 * @run main ClassWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {}

    @Retention(RetentionPolicy.RUNTIME)
    @interface Other {}

    @Marker
    static class Annotated {}

    static class Plain {}

    public static void main(String[] args) {
        try {
            Annotated.class.getAnnotationsByType(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }

        try {
            Plain.class.getAnnotationsByType(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }

        if (Annotated.class.getAnnotationsByType(Marker.class).length != 1) {
            throw new AssertionError("expected one Marker annotation");
        }

        if (Annotated.class.getAnnotationsByType(Other.class).length != 0) {
            throw new AssertionError("expected no Other annotation");
        }

        if (Plain.class.getAnnotationsByType(Marker.class).length != 0) {
            throw new AssertionError("expected no Marker annotation on Plain");
        }
    }
}
