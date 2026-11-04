/*
 * @test
 * @summary Verifies that AccessibleObject.getAnnotations() delegates to getDeclaredAnnotations()
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    static final Annotation[] EXPECTED = new Annotation[0];
    static Annotation[] returned = EXPECTED;

    static class Stub extends AccessibleObject {
        @Override
        public Annotation[] getDeclaredAnnotations() {
            return returned;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Stub stub = new Stub();

        Annotation[] result = stub.getAnnotations();
        check(result == EXPECTED, "getAnnotations() must return the array from getDeclaredAnnotations()");
        check(result.length == 0, "expected empty annotation array");

        Annotation[] other = new Annotation[0];
        returned = other;
        result = stub.getAnnotations();
        check(result == other, "getAnnotations() must reflect the current getDeclaredAnnotations() result");

        returned = null;
        result = stub.getAnnotations();
        check(result == null, "getAnnotations() must propagate a null result from getDeclaredAnnotations()");
    }
}
