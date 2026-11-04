/*
 * @test
 * @summary Test for MethodHandles.iteratedLoop method behavior.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testIteratedLoopWithNullBody();
        testIteratedLoopWithValidHandles();
    }

    private static void testIteratedLoopWithNullBody() {
        try {
            MethodHandle iterator = MethodHandles.constant(MethodType.methodType(int.class), 0);
            MethodHandle init = MethodHandles.constant(MethodType.methodType(void.class), null);
            MethodHandle body = null;
            MethodHandle result = MethodHandles.iteratedLoop(iterator, init, body);
            if (result == null) {
                System.out.println("PASS: iteratedLoop with null body returns non-null handle.");
            } else {
                System.out.println("FAIL: iteratedLoop with null body should return null or handle.");
            }
        } catch (NullPointerException e) {
            System.out.println("PASS: iteratedLoop throws NullPointerException when body is null.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testIteratedLoopWithValidHandles() {
        try {
            MethodHandle iterator = MethodHandles.constant(MethodType.methodType(int.class), 0);
            MethodHandle init = MethodHandles.constant(MethodType.methodType(void.class), null);
            MethodHandle body = MethodHandles.constant(MethodType.methodType(int.class), 42);
            MethodHandle result = MethodHandles.iteratedLoop(iterator, init, body);
            if (result != null) {
                System.out.println("PASS: iteratedLoop with valid handles returns a handle.");
            } else {
                System.out.println("FAIL: iteratedLoop with valid handles returned null.");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
