/*
 * @test
 * @summary Verifies MethodHandles.doWhileLoop executes the body once before testing the predicate
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    static int inc(int v) {
        return v + 1;
    }

    static boolean never(int v) {
        return false;
    }

    static boolean lessThanThree(int v) {
        return v < 3;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle init = MethodHandles.constant(int.class, 0);
        MethodHandle body = lookup.findStatic(MethodHandlesWitupTest.class,
                "inc", MethodType.methodType(int.class, int.class));
        MethodHandle predFalse = lookup.findStatic(MethodHandlesWitupTest.class,
                "never", MethodType.methodType(boolean.class, int.class));
        MethodHandle predLess = lookup.findStatic(MethodHandlesWitupTest.class,
                "lessThanThree", MethodType.methodType(boolean.class, int.class));

        MethodHandle loopOnce = MethodHandles.doWhileLoop(init, body, predFalse);
        int r1 = (int) loopOnce.invoke();
        check(r1 == 1, "expected body executed once, got " + r1);

        MethodHandle loopUntil3 = MethodHandles.doWhileLoop(init, body, predLess);
        int r2 = (int) loopUntil3.invoke();
        check(r2 == 3, "expected loop to stop at 3, got " + r2);
    }
}
