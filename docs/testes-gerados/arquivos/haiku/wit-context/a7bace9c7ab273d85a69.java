/*
 * @test
 * @summary Witup-derived checks for Class.getDeclaredAnnotation: null argument rejection and declared-only lookup
 * @run main ClassWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {}

    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface Inh {}

    @Marker
    static class Annotated {}

    @Inh
    static class Parent {}

    static class Child extends Parent {}

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        boolean npeThrown = false;
        try {
            Annotated.class.getDeclaredAnnotation(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "expected NullPointerException for null annotationClass");

        Annotation present = Annotated.class.getDeclaredAnnotation(Marker.class);
        check(present != null, "declared annotation Marker should be present on Annotated");
        check(present instanceof Marker, "returned annotation should be an instance of Marker");

        check(Annotated.class.getDeclaredAnnotation(Inh.class) == null,
              "absent annotation should yield null");

        check(Parent.class.getDeclaredAnnotation(Inh.class) != null,
              "declared annotation Inh should be present on Parent");

        check(Child.class.getDeclaredAnnotation(Inh.class) == null,
              "getDeclaredAnnotation must not return inherited annotation for Child");

        check(Child.class.getAnnotation(Inh.class) != null,
              "getAnnotation should return inherited annotation for Child");
    }
}
