/*
 * @test
 * @summary Checks MethodHandles.doWhileLoop normal loop result and NullPointerException for null body or pred
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    static int init() {
        return 0;
    }

    static int inc(int v) {
        return v + 1;
    }

    static boolean lessThanThree(int v) {
        return v < 3;
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup l = MethodHandles.lookup();
        MethodHandle initH = l.findStatic(MethodHandlesWitupTest.class, "init", MethodType.methodType(int.class));
        MethodHandle bodyH = l.findStatic(MethodHandlesWitupTest.class, "inc", MethodType.methodType(int.class, int.class));
        MethodHandle predH = l.findStatic(MethodHandlesWitupTest.class, "lessThanThree", MethodType.methodType(boolean.class, int.class));

        MethodHandle loop = MethodHandles.doWhileLoop(initH, bodyH, predH);
        int r = (int) loop.invoke();
        if (r != 3) {
            throw new RuntimeException("Expected 3 but got " + r);
        }

        expectNpe(initH, null, predH);
        expectNpe(initH, bodyH, null);
    }

    static void expectNpe(MethodHandle init, MethodHandle body, MethodHandle pred) {
        try {
            MethodHandles.doWhileLoop(init, body, pred);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected");
    }
}
