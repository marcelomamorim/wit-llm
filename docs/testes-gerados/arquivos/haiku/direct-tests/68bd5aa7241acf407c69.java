/*
 * @test
 * @summary Verifies public Method.getAnnotation(Class) behavior for RUNTIME, CLASS-retained, and absent annotations
 * @run main MethodWitupTest
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

public class MethodWitupTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface RuntimeMarker {
        String value() default "dflt";
    }

    @Retention(RetentionPolicy.CLASS)
    @interface ClassMarker {
    }

    static class Target {
        @RuntimeMarker("hello")
        public void explicitValue() {}

        @RuntimeMarker
        public void defaultValue() {}

        @ClassMarker
        public void classRetained() {}

        public void plain() {}
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Method explicit = Target.class.getMethod("explicitValue");
        RuntimeMarker r1 = explicit.getAnnotation(RuntimeMarker.class);
        check(r1 != null, "expected RUNTIME annotation on explicitValue");
        check("hello".equals(r1.value()), "unexpected value: " + r1.value());

        Method defaults = Target.class.getMethod("defaultValue");
        RuntimeMarker r2 = defaults.getAnnotation(RuntimeMarker.class);
        check(r2 != null, "expected RUNTIME annotation on defaultValue");
        check("dflt".equals(r2.value()), "unexpected default value: " + r2.value());

        Method classRet = Target.class.getMethod("classRetained");
        check(classRet.getAnnotation(ClassMarker.class) == null,
              "CLASS-retained annotation must not be visible at runtime");

        Method plain = Target.class.getMethod("plain");
        check(plain.getAnnotation(RuntimeMarker.class) == null,
              "absent annotation must yield null");
        check(plain.getAnnotation(ClassMarker.class) == null,
              "absent annotation must yield null");
    }
}
