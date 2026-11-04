/*
 * @test
 * @summary Test MethodHandles.unreflectSpecial behavior including null specialCaller causing NPE
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testUnreflectSpecialWithValidInputs();
        testUnreflectSpecialWithNullSpecialCaller();
    }

    private static void testUnreflectSpecialWithValidInputs() throws Throwable {
        Lookup lookup = MethodHandles.lookup();
        Method m = String.class.getDeclaredMethod("toString");
        // Use String.class as specialCaller, which is valid
        MethodHandle mh = lookup.unreflectSpecial(m, String.class);
        if (mh == null) {
            throw new AssertionError("unreflectSpecial returned null MethodHandle");
        }
    }

    private static void testUnreflectSpecialWithNullSpecialCaller() {
        try {
            Lookup lookup = MethodHandles.lookup();
            Method m = String.class.getDeclaredMethod("toString");
            // According to WIT expath, passing null specialCaller with allowedModes == -1 causes NPE
            // The current code calls Objects.requireNonNull on specialCaller in Lookup.in(specialCaller)
            lookup.unreflectSpecial(m, null);
            throw new AssertionError("Expected NullPointerException not thrown when specialCaller is null");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IllegalAccessException e) {
            throw new AssertionError("Unexpected IllegalAccessException", e);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("Unexpected NoSuchMethodException", e);
        }
    }
}
