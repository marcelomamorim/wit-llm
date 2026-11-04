/*
 * @test
 * @summary Verifies Lookup.accessClass returns accessible classes and throws IllegalAccessException for classes in non-exported packages
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Class<?> result = lookup.accessClass(String.class);
        if (result != String.class) {
            throw new AssertionError("expected String.class but got " + result);
        }

        result = lookup.accessClass(java.util.List.class);
        if (result != java.util.List.class) {
            throw new AssertionError("expected List.class but got " + result);
        }

        Class<?> hidden = Class.forName("jdk.internal.misc.Unsafe");
        boolean thrown = false;
        try {
            lookup.accessClass(hidden);
        } catch (IllegalAccessException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalAccessException for non-exported class");
        }
    }
}
