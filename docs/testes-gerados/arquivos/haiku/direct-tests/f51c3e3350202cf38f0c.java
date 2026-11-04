/*
 * @test
 * @summary Constructor.getAnnotation returns the runtime annotation present on a constructor and null when absent
 * @run main ConstructorWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;

public class ConstructorWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface Marker {
        String value() default "default";
    }

    static class Annotated {
        @Marker("ctor")
        public Annotated() {
        }
    }

    static class DefaultValued {
        @Marker
        public DefaultValued() {
        }
    }

    static class Plain {
        public Plain() {
        }
    }

    static class Deprecatedctor {
        @Deprecated
        public Deprecatedctor() {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Constructor<Annotated> annotated = Annotated.class.getDeclaredConstructor();
        Marker marker = annotated.getAnnotation(Marker.class);
        check(marker != null, "expected Marker on constructor");
        check("ctor".equals(marker.value()), "expected value 'ctor' but was " + marker.value());

        Constructor<DefaultValued> defaults = DefaultValued.class.getDeclaredConstructor();
        Marker defaultMarker = defaults.getAnnotation(Marker.class);
        check(defaultMarker != null, "expected Marker on DefaultValued constructor");
        check("default".equals(defaultMarker.value()),
              "expected default value but was " + defaultMarker.value());

        Constructor<Plain> plain = Plain.class.getDeclaredConstructor();
        check(plain.getAnnotation(Marker.class) == null, "expected null for absent annotation");

        Constructor<Deprecatedctor> dep = Deprecatedctor.class.getDeclaredConstructor();
        check(dep.getAnnotation(Deprecated.class) != null, "expected @Deprecated on constructor");

        check(dep.getAnnotation(Marker.class) == null,
              "expected null for annotation type not present on constructor");
    }
}
