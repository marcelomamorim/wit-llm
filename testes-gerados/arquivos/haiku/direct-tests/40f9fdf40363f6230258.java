/*
 * @test
 * @summary Verifies MethodHandles.filterArguments applies filters at the given positions, ignores null filters, and keeps the target type.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    static int add(int a, int b) { return a + b; }

    static int twice(int x) { return 2 * x; }

    static int failures = 0;

    static void check(String name, int expected, int actual) {
        if (expected != actual) {
            failures++;
            System.err.println(name + ": expected " + expected + " but was " + actual);
        }
    }

    static void checkType(String name, MethodType expected, MethodType actual) {
        if (!expected.equals(actual)) {
            failures++;
            System.err.println(name + ": expected type " + expected + " but was " + actual);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle add = lookup.findStatic(MethodHandlesWitupTest.class, "add",
                MethodType.methodType(int.class, int.class, int.class));
        MethodHandle twice = lookup.findStatic(MethodHandlesWitupTest.class, "twice",
                MethodType.methodType(int.class, int.class));

        MethodHandle adapter0 = MethodHandles.filterArguments(add, 0, twice);
        checkType("type pos0", add.type(), adapter0.type());
        check("pos0 single filter", 10, (int) adapter0.invoke(3, 4));

        MethodHandle adapter1 = MethodHandles.filterArguments(add, 1, twice);
        checkType("type pos1", add.type(), adapter1.type());
        check("pos1 single filter", 11, (int) adapter1.invoke(3, 4));

        MethodHandle adapterBoth = MethodHandles.filterArguments(add, 0, twice, twice);
        checkType("type both", add.type(), adapterBoth.type());
        check("two filters", 14, (int) adapterBoth.invoke(3, 4));

        MethodHandle adapterNull = MethodHandles.filterArguments(add, 0, new MethodHandle[] { null });
        checkType("type null filter", add.type(), adapterNull.type());
        check("null filter ignored", 7, (int) adapterNull.invoke(3, 4));

        MethodHandle adapterEmpty = MethodHandles.filterArguments(add, 0);
        checkType("type no filters", add.type(), adapterEmpty.type());
        check("no filters", 7, (int) adapterEmpty.invoke(3, 4));

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
