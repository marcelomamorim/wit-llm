/*
 * @test
 * @summary Test ConstantCallSite.dynamicInvoker() behavior including IllegalStateException when not frozen
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ConstantCallSiteWitupTest {
    public static void main(String[] args) throws Throwable {
        testDynamicInvokerReturnsTarget();
        testDynamicInvokerThrowsIfNotFrozen();
    }

    static void testDynamicInvokerReturnsTarget() throws Throwable {
        MethodHandle mh = MethodHandles.identity(String.class);
        ConstantCallSite ccs = new ConstantCallSite(mh);
        MethodHandle invoker = ccs.dynamicInvoker();
        if (invoker == null) {
            throw new Error("dynamicInvoker() returned null");
        }
        if (invoker != mh) {
            throw new Error("dynamicInvoker() did not return the original target");
        }
        // invoke the returned MethodHandle to check it works
        Object result = invoker.invokeExact("test");
        if (!"test".equals(result)) {
            throw new Error("dynamicInvoker() target invocation returned unexpected result");
        }
    }

    static void testDynamicInvokerThrowsIfNotFrozen() throws Throwable {
        // Use the protected constructor to create a ConstantCallSite that is not frozen
        MethodType mt = MethodType.methodType(String.class, String.class);
        MethodHandle hook = MethodHandles.lookup().findStatic(ConstantCallSiteWitupTest.class, "createTargetHook", MethodType.methodType(MethodHandle.class, ConstantCallSite.class));

        ConstantCallSite ccs = null;
        try {
            ccs = new ConstantCallSite(mt, hook);
        } catch (Throwable t) {
            throw new Error("Unexpected exception during ConstantCallSite construction", t);
        }

        // The ConstantCallSite created by the hook is frozen, but during construction getTarget() throws
        // We simulate the partially constructed state by subclassing and overriding isFrozen to false
        ConstantCallSite ccsNotFrozen = new ConstantCallSite(ccs.type(), hook) {
            @Override
            public MethodHandle getTarget() {
                if (!isFrozen()) {
                    throw new IllegalStateException("CallSite not frozen");
                }
                return super.getTarget();
            }

            @Override
            public boolean isFrozen() {
                return false;
            }
        };

        boolean threw = false;
        try {
            ccsNotFrozen.dynamicInvoker();
        } catch (IllegalStateException ise) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Expected IllegalStateException when dynamicInvoker() called on not frozen ConstantCallSite");
        }
    }

    // Hook method used to create a target MethodHandle for the protected constructor
    public static MethodHandle createTargetHook(ConstantCallSite ccs) {
        return MethodHandles.identity(String.class);
    }
}
