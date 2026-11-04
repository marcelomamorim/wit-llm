/*
 * @test
 * @summary Behavioral tests for MethodHandles.whileLoop argument validation and loop semantics
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    static int zero() { return 0; }

    static int seven() { return 7; }

    static boolean lessThan5(int v) { return v < 5; }

    static boolean never(int v) { return false; }

    static int inc(int v) { return v + 1; }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle init0 = lookup.findStatic(MethodHandlesWitupTest.class, "zero",
                MethodType.methodType(int.class));
        MethodHandle init7 = lookup.findStatic(MethodHandlesWitupTest.class, "seven",
                MethodType.methodType(int.class));
        MethodHandle pred = lookup.findStatic(MethodHandlesWitupTest.class, "lessThan5",
                MethodType.methodType(boolean.class, int.class));
        MethodHandle predFalse = lookup.findStatic(MethodHandlesWitupTest.class, "never",
                MethodType.methodType(boolean.class, int.class));
        MethodHandle body = lookup.findStatic(MethodHandlesWitupTest.class, "inc",
                MethodType.methodType(int.class, int.class));

        int loopResult = (int) MethodHandles.whileLoop(init0, pred, body).invokeExact();
        check(loopResult == 5, "expected 5 but was " + loopResult);

        int immediateExit = (int) MethodHandles.whileLoop(init7, predFalse, body).invokeExact();
        check(immediateExit == 7, "expected 7 but was " + immediateExit);

        try {
            MethodHandles.whileLoop(init0, null, body);
            throw new RuntimeException("expected NullPointerException for null pred");
        } catch (NullPointerException expected) {
        }

        try {
            MethodHandles.whileLoop(init0, pred, null);
            throw new RuntimeException("expected NullPointerException for null body");
        } catch (NullPointerException expected) {
        }
    }
}
