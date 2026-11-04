/*
 * @test
 * @summary WITUP-derived checks for AccessibleObject.getAnnotations delegation and default failure
 * @run main AccessibleObjectWitupTest
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWitupTest {

    static class Plain extends AccessibleObject {
    }

    static class Stub extends AccessibleObject {
        static final Annotation[] RESULT = new Annotation[0];
        int calls;

        @Override
        public Annotation[] getDeclaredAnnotations() {
            calls++;
            return RESULT;
        }
    }

    public static void main(String[] args) {
        Stub stub = new Stub();
        Annotation[] result = stub.getAnnotations();
        if (result != Stub.RESULT) {
            throw new RuntimeException("getAnnotations did not return getDeclaredAnnotations result");
        }
        if (stub.calls != 1) {
            throw new RuntimeException("getAnnotations did not delegate exactly once");
        }

        Plain plain = new Plain();
        boolean thrown = false;
        try {
            plain.getAnnotations();
        } catch (AssertionError e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected AssertionError from default getDeclaredAnnotations");
        }
    }
}
