/*
 * @test
 * @summary Verifies that ConstantCallSite.dynamicInvoker() returns the permanent target and delegates to it
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ConstantCallSiteWitupTest {

    static int add(int a, int b) {
        return a + b;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDynamicInvokerReturnsTarget() throws Throwable {
        MethodType type = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle target = MethodHandles.lookup().findStatic(ConstantCallSiteWitupTest.class, "add", type);
        ConstantCallSite cs = new ConstantCallSite(target);

        MethodHandle invoker = cs.dynamicInvoker();
        check(invoker != null, "dynamicInvoker() returned null");
        check(invoker == cs.getTarget(), "dynamicInvoker() must return the call site target");
        check(invoker.type().equals(cs.type()), "dynamicInvoker() type must match call site type");

        int r = (int) invoker.invokeExact(2, 3);
        check(r == 5, "unexpected result from dynamicInvoker(): " + r);

        int r2 = (int) invoker.invokeExact(10, -4);
        check(r2 == 6, "unexpected second result from dynamicInvoker(): " + r2);
    }

    static void testDynamicInvokerWithConstantTarget() throws Throwable {
        MethodHandle target = MethodHandles.constant(String.class, "witup");
        ConstantCallSite cs = new ConstantCallSite(target);

        MethodHandle invoker = cs.dynamicInvoker();
        check(invoker.type().equals(MethodType.methodType(String.class)),
              "unexpected invoker type: " + invoker.type());

        String s = (String) invoker.invokeExact();
        check("witup".equals(s), "unexpected constant result: " + s);
    }

    static void testDynamicInvokerIsStableAcrossCalls() throws Throwable {
        MethodHandle target = MethodHandles.constant(int.class, 42);
        ConstantCallSite cs = new ConstantCallSite(target);

        MethodHandle first = cs.dynamicInvoker();
        MethodHandle second = cs.dynamicInvoker();
        check(first == second, "dynamicInvoker() should return the same permanent target");
        check(first == target, "dynamicInvoker() should return the constructor target");

        int v = (int) first.invokeExact();
        check(v == 42, "unexpected value: " + v);
    }

    public static void main(String[] args) throws Throwable {
        testDynamicInvokerReturnsTarget();
        testDynamicInvokerWithConstantTarget();
        testDynamicInvokerIsStableAcrossCalls();
    }
}
