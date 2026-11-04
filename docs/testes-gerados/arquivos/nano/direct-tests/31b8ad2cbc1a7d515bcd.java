/*
 * @test
 * @summary Test doWhileLoop method for correct loop behavior and exception handling
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testNormalLoop();
        testNullArguments();
        testExceptionInPred();
        testExceptionInBody();
    }

    static void testNormalLoop() {
        try {
            MethodHandle init = MethodHandles.constant(String.class, "start");
            MethodHandle body = MethodHandles.dropArguments(MethodHandles.constant(String.class, "body"), 0, String.class);
            MethodHandle pred = MethodHandles.dropArguments(MethodHandles.constant(boolean.class, true), 0, String.class);
            MethodHandle loopHandle = MethodHandles.doWhileLoop(init, body, pred);
            Object result = loopHandle.invoke();
            if (!"start".equals(result)) {
                throw new AssertionError("Expected 'start', got: " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError(t);
        }
    }

    static void testNullArguments() {
        try {
            MethodHandles.doWhileLoop(null, null, null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError(t);
        }
    }

    static void testExceptionInPred() {
        try {
            MethodHandle init = MethodHandles.constant(String.class, "start");
            MethodHandle body = MethodHandles.dropArguments(MethodHandles.constant(String.class, "body"), 0, String.class);
            MethodHandle pred = MethodHandles.throwException(boolean.class, RuntimeException.class);
            MethodHandles.doWhileLoop(init, body, pred).invoke();
            throw new AssertionError("Expected RuntimeException");
        } catch (RuntimeException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError(t);
        }
    }

    static void testExceptionInBody() {
        try {
            MethodHandle init = MethodHandles.constant(String.class, "start");
            MethodHandle body = MethodHandles.throwException(String.class, RuntimeException.class);
            MethodHandle pred = MethodHandles.constant(boolean.class, true);
            MethodHandles.doWhileLoop(init, body, pred).invoke();
            throw new AssertionError("Expected RuntimeException");
        } catch (RuntimeException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError(t);
        }
    }
}
