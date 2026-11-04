/*
 * @test
 * @summary Witup generated test for Executable.getAnnotation(Class): null guard and public annotation lookup
 * @run main ExecutableWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

public class ExecutableWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {}

    @Retention(RetentionPolicy.RUNTIME)
    @interface Other {}

    @Marker
    public static void annotated() {}

    public static void plain() {}

    public static void main(String[] args) throws Exception {
        Method annotated = ExecutableWitupTest.class.getMethod("annotated");
        Method plain = ExecutableWitupTest.class.getMethod("plain");

        boolean npeThrown = false;
        try {
            annotated.getAnnotation(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null annotationClass");
        }

        Marker m = annotated.getAnnotation(Marker.class);
        if (m == null) {
            throw new RuntimeException("Expected present @Marker on annotated()");
        }

        if (annotated.getAnnotation(Other.class) != null) {
            throw new RuntimeException("Expected null for absent annotation on annotated()");
        }

        if (plain.getAnnotation(Marker.class) != null) {
            throw new RuntimeException("Expected null for absent annotation on plain()");
        }
    }
}
