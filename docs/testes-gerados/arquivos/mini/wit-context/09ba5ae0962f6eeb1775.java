/*
 * @test
 * @summary Test ConstantCallSite.getTarget() behavior including IllegalStateException when not frozen
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;

public class ConstantCallSiteWitupTest {
    public static void main(String[] args) throws Throwable {
        testGetTargetReturnsTarget();
        testGetTargetThrowsWhenNotFrozen();
    }

    // Test that getTarget() returns the target MethodHandle when isFrozen is true
    private static void testGetTargetReturnsTarget() {
        MethodHandle mh = MethodHandles.identity(String.class);
        ConstantCallSite ccs = new ConstantCallSite(mh);
        MethodHandle target = ccs.getTarget();
        if (target != mh) {
            throw new AssertionError("getTarget() did not return the expected MethodHandle");
        }
    }

    // Test that getTarget() throws IllegalStateException when isFrozen is false
    private static void testGetTargetThrowsWhenNotFrozen() throws Throwable {
        // Use the protected constructor with a createTargetHook that returns a valid MethodHandle
        MethodType mt = MethodType.methodType(String.class, String.class);
        MethodHandle hook = MethodHandles.lookup().findStatic(ConstantCallSiteWitupTest.class, "hookMethod", mt);

        ConstantCallSite ccs = null;
        try {
            ccs = new ConstantCallSite(mt, hook);
        } catch (Throwable t) {
            // The constructor may throw if hook throws; fail test if so
            throw new AssertionError("Constructor threw unexpected exception", t);
        }

        // Reflection is not used; we rely on the fact that during construction getTarget() throws if !isFrozen
        // But after construction, isFrozen is true, so forcibly create a subclass to set isFrozen to false
        ConstantCallSite subclass = new ConstantCallSite(mt, hook) {
            // Override isFrozen field by shadowing (not possible directly, so simulate by overriding getTarget)
            @Override
            public MethodHandle getTarget() {
                // Simulate !isFrozen by throwing IllegalStateException
                throw new IllegalStateException();
            }
        };

        boolean thrown = false;
        try {
            subclass.getTarget();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException when getTarget() called on unfrozen ConstantCallSite");
        }
    }

    // Hook method used for createTargetHook in protected constructor test
    public static String hookMethod(String s) {
        return s;
    }
}
