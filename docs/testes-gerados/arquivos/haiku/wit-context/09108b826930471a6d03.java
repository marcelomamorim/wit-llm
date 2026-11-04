/*
 * @test
 * @summary Witup generated checks for ConstantCallSite.dynamicInvoker and related public behavior
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ConstantCallSiteWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static MethodHandle hook(ConstantCallSite cs) {
        cs.dynamicInvoker();
        return null;
    }

    static class Hooked extends ConstantCallSite {
        Hooked(MethodType type, MethodHandle hook) throws Throwable {
            super(type, hook);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandle mh = MethodHandles.constant(String.class, "x");
        ConstantCallSite cs = new ConstantCallSite(mh);

        check(cs.getTarget() == mh, "getTarget must return the constructor target");
        check(cs.dynamicInvoker() == mh, "dynamicInvoker must return the permanent target");
        check(cs.type().equals(MethodType.methodType(String.class)), "type must match target type");

        try {
            new ConstantCallSite((MethodHandle) null);
            check(false, "null target must throw NullPointerException");
        } catch (NullPointerException expected) {
        }

        MethodHandle hookMh = MethodHandles.lookup().findStatic(
                ConstantCallSiteWitupTest.class, "hook",
                MethodType.methodType(MethodHandle.class, ConstantCallSite.class));
        try {
            new Hooked(MethodType.methodType(String.class), hookMh);
            check(false, "dynamicInvoker during construction must throw IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }
}
