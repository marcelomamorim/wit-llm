/*
 * @test
 * @summary Verifies Method.getAnnotation rejects a null annotation class with NullPointerException and returns null for absent annotations
 * @run main MethodWitupTest
 */

import java.lang.reflect.Method;

public class MethodWitupTest {

    public static class Holder {
        @Deprecated
        public void old() {
        }

        public void plain() {
        }
    }

    public static void main(String[] args) throws Exception {
        Method toString = Object.class.getMethod("toString");
        boolean thrown = false;
        try {
            toString.getAnnotation(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null annotationClass");
        }

        Method plain = Holder.class.getMethod("plain");
        if (plain.getAnnotation(Deprecated.class) != null) {
            throw new AssertionError("expected null for absent annotation");
        }

        Method old = Holder.class.getMethod("old");
        if (old.getAnnotation(Deprecated.class) == null) {
            throw new AssertionError("expected present @Deprecated annotation");
        }
    }
}
