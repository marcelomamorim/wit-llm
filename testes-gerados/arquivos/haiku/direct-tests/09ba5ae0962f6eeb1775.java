/*
 * @test
 * @summary Verifies that ConstantCallSite.getTarget returns the permanent target and that construction with a null target fails
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class ConstantCallSiteWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandle mh = MethodHandles.constant(String.class, "x");
        ConstantCallSite cs = new ConstantCallSite(mh);

        check(cs.getTarget() == mh, "getTarget() must return the same target passed to the constructor");
        check(cs.getTarget() == cs.getTarget(), "getTarget() must be stable across calls");
        check(cs.type().equals(mh.type()), "call site type must match target type");

        String result = (String) cs.getTarget().invoke();
        check("x".equals(result), "target invocation must return the constant value");

        boolean npe = false;
        try {
            new ConstantCallSite(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null target must cause NullPointerException");
    }
}
