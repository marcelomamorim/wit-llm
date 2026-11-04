/*
 * @test
 * @summary Behavioral checks for MethodHandle.asVarargsCollector using public APIs only
 * @run main MethodHandleWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleWitupTest {

    public static int count(Object... a) {
        return a.length;
    }

    public static int fixed(Object[] a) {
        return a.length;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle varargs = lookup.findStatic(MethodHandleWitupTest.class, "count",
                MethodType.methodType(int.class, Object[].class));
        MethodHandle fixedHandle = lookup.findStatic(MethodHandleWitupTest.class, "fixed",
                MethodType.methodType(int.class, Object[].class));

        // null arrayType must be rejected with NullPointerException
        boolean npe = false;
        try {
            fixedHandle.asVarargsCollector(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "expected NullPointerException for null arrayType");

        // already-varargs handle with matching array type returns itself
        check(varargs.isVarargsCollector(), "count should be a varargs collector");
        check(varargs.asVarargsCollector(Object[].class) == varargs,
                "asVarargsCollector on matching varargs handle should return this");

        // fixed-arity handle yields a new varargs collector; original is unchanged
        check(!fixedHandle.isVarargsCollector(), "fixed handle should not be varargs");
        MethodHandle collector = fixedHandle.asVarargsCollector(Object[].class);
        check(collector != fixedHandle, "expected a new handle");
        check(collector.isVarargsCollector(), "result should be a varargs collector");
        check(!fixedHandle.isVarargsCollector(), "original handle must remain fixed-arity");

        // trailing arguments are collected into the array
        int two = (int) collector.invoke("a", "b");
        check(two == 2, "expected 2 but was " + two);

        int zero = (int) collector.invoke();
        check(zero == 0, "expected 0 but was " + zero);

        // asFixedArity restores fixed-arity behavior on the collector
        check(!collector.asFixedArity().isVarargsCollector(),
                "asFixedArity should produce a fixed-arity handle");
    }
}
