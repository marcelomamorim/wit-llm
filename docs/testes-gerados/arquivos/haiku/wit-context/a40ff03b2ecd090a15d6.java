/*
 * @test
 * @summary Verifies Class.getAnnotation null rejection and basic annotation presence/absence
 * @run main ClassWitupTest
 */
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ClassWitupTest {
    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {}

    @Marker
    static class Annotated {}

    static class Plain {}

    public static void main(String[] args) {
        try {
            ClassWitupTest.class.getAnnotation(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }

        if (Annotated.class.getAnnotation(Marker.class) == null) {
            throw new RuntimeException("annotation not found on annotated class");
        }

        if (Plain.class.getAnnotation(Marker.class) != null) {
            throw new RuntimeException("unexpected annotation on plain class");
        }
    }
}
