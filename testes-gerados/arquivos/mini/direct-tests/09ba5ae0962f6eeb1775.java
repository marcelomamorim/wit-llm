/*
 * @test
 * @summary Test ConstantCallSite.getTarget() behavior
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ConstantCallSiteWitupTest {
    public static void main(String[] args) throws Throwable {
        testGetTargetReturnsTarget();
        testGetTargetThrowsWhenNotFrozen();
    }

    static void testGetTargetReturnsTarget() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = null;
        try {
            mh = lookup.findStatic(ConstantCallSiteWitupTest.class, "targetMethod", MethodType.methodType(String.class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ConstantCallSite ccs = new ConstantCallSite(mh);
        MethodHandle returned = ccs.getTarget();
        if (returned != mh) {
            throw new AssertionError("getTarget() did not return the original target MethodHandle");
        }
    }

    static void testGetTargetThrowsWhenNotFrozen() throws Throwable {
        MethodType mt = MethodType.methodType(String.class);
        MethodHandle hook = MethodHandles.lookup().findStatic(ConstantCallSiteWitupTest.class, "targetMethod", mt);
        ConstantCallSite ccs = new ConstantCallSite(mt, hook) {
            // Override isFrozen to false to simulate partially constructed state
            private final boolean isFrozen = false;
            @Override
            public MethodHandle getTarget() {
                if (!isFrozen) throw new IllegalStateException();
                return super.getTarget();
            }
        };
        boolean threw = false;
        try {
            ccs.getTarget();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getTarget() did not throw IllegalStateException when not frozen");
        }
    }

    public static String targetMethod() {
        return "target";
    }
}
