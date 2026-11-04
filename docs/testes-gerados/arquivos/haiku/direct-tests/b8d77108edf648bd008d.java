/*
 * @test
 * @summary Behavioral checks for MethodHandles.whileLoop with simple int state loops
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    static int inc(int i) {
        return i + 1;
    }

    static boolean lessThan5(int i) {
        return i < 5;
    }

    static boolean lessThan0(int i) {
        return i < 0;
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle init = MethodHandles.constant(int.class, 0);
        MethodHandle body = lookup.findStatic(MethodHandlesWitupTest.class, "inc",
                MethodType.methodType(int.class, int.class));
        MethodHandle pred = lookup.findStatic(MethodHandlesWitupTest.class, "lessThan5",
                MethodType.methodType(boolean.class, int.class));

        MethodHandle loop = MethodHandles.whileLoop(init, pred, body);
        int result = (int) loop.invokeExact(0);
        if (result != 5) {
            throw new AssertionError("expected 5 but was " + result);
        }

        MethodHandle predFalse = lookup.findStatic(MethodHandlesWitupTest.class, "lessThan0",
                MethodType.methodType(boolean.class, int.class));
        MethodHandle loopNoIter = MethodHandles.whileLoop(init, predFalse, body);
        int noIter = (int) loopNoIter.invokeExact(0);
        if (noIter != 0) {
            throw new AssertionError("expected 0 when predicate is initially false but was " + noIter);
        }

        MethodHandle loopType = MethodHandles.whileLoop(init, pred, body);
        if (!loopType.type().equals(MethodType.methodType(int.class, int.class))) {
            throw new AssertionError("unexpected loop type: " + loopType.type());
        }
    }
}
