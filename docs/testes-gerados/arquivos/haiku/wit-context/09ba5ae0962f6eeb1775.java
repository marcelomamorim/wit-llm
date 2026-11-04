/*
 * @test
 * @summary Checks ConstantCallSite.getTarget() for a fully constructed call site and for a call made during the protected constructor hook.
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ConstantCallSiteWitupTest {
    static boolean sawIllegalState;

    static final class Probe extends ConstantCallSite {
        Probe(MethodType targetType, MethodHandle hook) throws Throwable {
            super(targetType, hook);
        }
    }

    static MethodHandle hook(ConstantCallSite cs) {
        try {
            cs.getTarget();
        } catch (IllegalStateException e) {
            sawIllegalState = true;
        }
        return MethodHandles.constant(String.class, "partial");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandle mh = MethodHandles.constant(String.class, "ok");
        ConstantCallSite cs = new ConstantCallSite(mh);
        check(cs.getTarget() == mh, "getTarget must return the constructor target");
        check(cs.getTarget() == cs.getTarget(), "getTarget must be stable");
        String value = (String) cs.getTarget().invokeExact();
        check("ok".equals(value), "target must produce \"ok\"");

        MethodHandle h = MethodHandles.lookup().findStatic(
                ConstantCallSiteWitupTest.class, "hook",
                MethodType.methodType(MethodHandle.class, ConstantCallSite.class));
        MethodType mt = MethodType.methodType(String.class);
        Probe probe = new Probe(mt, h);
        check(sawIllegalState, "getTarget during hook must throw IllegalStateException");
        check(probe.type().equals(mt), "type must be available after construction");
        check(probe.getTarget().type().equals(mt), "final target must have the call site type");
        String partial = (String) probe.getTarget().invokeExact();
        check("partial".equals(partial), "hook result must become the target");
    }
}
