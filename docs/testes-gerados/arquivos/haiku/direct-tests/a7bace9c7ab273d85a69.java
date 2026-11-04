/*
 * @test
 * @summary Checks Class.getDeclaredAnnotation for null, present, absent and inherited annotations
 * @run main ClassWitupTest
 */
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {
    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {}

    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface Inh {}

    @Marker static class Annotated {}
    @Inh static class Base {}
    static class Child extends Base {}
    static class Plain {}

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    public static void main(String[] args) {
        try {
            Annotated.class.getDeclaredAnnotation(null);
            check(false, "null annotationClass should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

        check(Annotated.class.getDeclaredAnnotation(Marker.class) != null,
              "declared annotation should be returned");
        check(Annotated.class.getDeclaredAnnotation(Marker.class) != null
              && Annotated.class.getDeclaredAnnotation(Marker.class).annotationType() == Marker.class,
              "returned annotation should have requested type");
        check(Plain.class.getDeclaredAnnotation(Marker.class) == null,
              "absent annotation should return null");
        check(Base.class.getDeclaredAnnotation(Inh.class) != null,
              "annotation declared on Base should be returned for Base");
        check(Child.class.getDeclaredAnnotation(Inh.class) == null,
              "inherited annotation should not be returned by getDeclaredAnnotation");
        check(Child.class.getAnnotation(Inh.class) != null,
              "inherited annotation should be visible via getAnnotation");

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }
}
