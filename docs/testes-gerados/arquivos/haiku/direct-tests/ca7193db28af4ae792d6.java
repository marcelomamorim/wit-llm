/*
 * @test
 * @summary Executable.getAnnotation returns declared runtime annotations and rejects null
 * @run main ExecutableWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ExecutableWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Marker {
        String value() default "d";
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Other {
    }

    static class Holder {
        @Marker("ctor")
        Holder() {
        }
    }

    @Marker("m")
    public void annotated() {
    }

    public void plain() {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Method annotated = ExecutableWitupTest.class.getDeclaredMethod("annotated");
        Marker m = annotated.getAnnotation(Marker.class);
        check(m != null, "expected Marker on annotated method");
        check("m".equals(m.value()), "unexpected Marker value on method: " + m.value());
        check(annotated.getAnnotation(Other.class) == null, "Other is not present on method");

        Method plain = ExecutableWitupTest.class.getDeclaredMethod("plain");
        check(plain.getAnnotation(Marker.class) == null, "plain method must have no Marker");

        Constructor<?> ctor = Holder.class.getDeclaredConstructor();
        Marker cm = ctor.getAnnotation(Marker.class);
        check(cm != null, "expected Marker on constructor");
        check("ctor".equals(cm.value()), "unexpected Marker value on constructor: " + cm.value());
        check(ctor.getAnnotation(Other.class) == null, "Other is not present on constructor");

        boolean npe = false;
        try {
            annotated.getAnnotation(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "getAnnotation(null) must throw NullPointerException");
    }
}
