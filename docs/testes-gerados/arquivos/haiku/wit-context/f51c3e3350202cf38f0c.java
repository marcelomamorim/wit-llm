/*
 * @test
 * @summary Verify Constructor.getAnnotation throws NullPointerException for a null annotation class and returns public annotation values otherwise
 * @run main ConstructorWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;

public class ConstructorWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Marker {
    }

    public static class Annotated {
        @Marker
        public Annotated() {
        }
    }

    public static void main(String[] args) throws Exception {
        testNullAnnotationClassThrowsNpe();
        testPresentAnnotationIsReturned();
        testAbsentAnnotationReturnsNull();
    }

    static void testNullAnnotationClassThrowsNpe() throws Exception {
        Constructor<Annotated> c = Annotated.class.getConstructor();
        boolean thrown = false;
        try {
            c.getAnnotation(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null annotationClass");
        }
    }

    static void testPresentAnnotationIsReturned() throws Exception {
        Constructor<Annotated> c = Annotated.class.getConstructor();
        Annotation a = c.getAnnotation(Marker.class);
        if (a == null) {
            throw new RuntimeException("expected @Marker to be present on constructor");
        }
        if (!(a instanceof Marker)) {
            throw new RuntimeException("returned annotation has wrong type: " + a);
        }
    }

    static void testAbsentAnnotationReturnsNull() throws Exception {
        Constructor<Object> c = Object.class.getConstructor();
        if (c.getAnnotation(Marker.class) != null) {
            throw new RuntimeException("expected null for absent annotation");
        }
    }
}
