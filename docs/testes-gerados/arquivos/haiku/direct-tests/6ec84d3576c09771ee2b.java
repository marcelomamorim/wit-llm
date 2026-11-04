/*
 * @test
 * @summary Verify MethodHandles.Lookup.accessClass returns accessible classes and throws IllegalAccessException for inaccessible ones
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    static class PkgPrivate {}

    private static class Priv {}

    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        if (lookup.accessClass(Priv.class) != Priv.class) {
            throw new RuntimeException("lookup().accessClass(private nested class) should return the class");
        }

        if (MethodHandles.publicLookup().accessClass(String.class) != String.class) {
            throw new RuntimeException("publicLookup().accessClass(String.class) should return String.class");
        }

        boolean thrown = false;
        try {
            MethodHandles.publicLookup().accessClass(PkgPrivate.class);
        } catch (IllegalAccessException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("publicLookup().accessClass(package-private class) should throw IllegalAccessException");
        }
    }
}
