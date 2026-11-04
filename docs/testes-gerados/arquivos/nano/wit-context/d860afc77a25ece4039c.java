/*
 * @test
 * @summary Test for MethodHandles.unreflectSpecial to verify behavior and exception handling.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testUnreflectSpecialWithNullSpecialCaller();
        testUnreflectSpecialWithValidMethodAndCaller();
    }

    private static void testUnreflectSpecialWithNullSpecialCaller() {
        try {
            MethodHandles mh = MethodHandles.lookup();
            Method method = Object.class.getMethod("toString");
            // Expect NullPointerException due to guard condition: allowedModes == -1 && null == specialCaller
            mh.unreflectSpecial(method, null);
            System.err.println("Failed: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testUnreflectSpecialWithValidMethodAndCaller() {
        try {
            MethodHandles mh = MethodHandles.lookup();
            Method method = String.class.getMethod("length");
            Class<?> callerClass = String.class;
            MethodHandle handle = mh.unreflectSpecial(method, callerClass);
            if (handle != null) {
                System.out.println("Passed: MethodHandle obtained successfully.");
            } else {
                System.err.println("Failed: MethodHandle is null.");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
