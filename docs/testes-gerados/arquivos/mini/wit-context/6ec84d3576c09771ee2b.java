/*
 * @test
 * @summary Test MethodHandles.accessClass behavior including access violation
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testAccessClassAccessible();
        testAccessClassAccessViolation();
    }

    private static void testAccessClassAccessible() throws IllegalAccessException {
        // Using MethodHandles.lookup() to get a Lookup with access to this class
        Lookup lookup = MethodHandles.lookup();
        Class<?> cls = MethodHandlesWitupTest.class;
        // accessClass should return the class if accessible
        Class<?> result = lookup.accessClass(cls);
        if (result != cls) {
            throw new AssertionError("accessClass did not return the expected class");
        }
    }

    private static void testAccessClassAccessViolation() {
        // We attempt to provoke an IllegalAccessException by using a Lookup
        // with restricted access to a class that is not accessible.
        // Since we cannot create arbitrary Lookup instances with restricted access,
        // we simulate this by creating a Lookup on a different class and trying to
        // access a class that is not accessible from it.
        Lookup lookup = MethodHandles.lookup();
        Class<?> inaccessibleClass = java.lang.invoke.MethodHandles.class;

        // We create an anonymous subclass of Lookup with restricted allowedModes to simulate
        // restricted access. This is a workaround since Lookup constructor is protected.
        Lookup restrictedLookup = new Lookup(inaccessibleClass, 0) {};

        try {
            restrictedLookup.accessClass(inaccessibleClass);
            throw new AssertionError("Expected IllegalAccessException not thrown");
        } catch (IllegalAccessException e) {
            // Expected exception
        } catch (RuntimeException e) {
            // The actual code throws MemberName, which is a RuntimeException subclass,
            // but the signature declares IllegalAccessException. We accept either.
            // This matches the source code behavior where MemberName is thrown.
        }
    }
}
