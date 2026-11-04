/*
 * @test
 * @summary ConstantCallSite.setTarget must always throw UnsupportedOperationException and keep the original target
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class ConstantCallSiteWitupTest {
    public static void main(String[] args) throws Throwable {
        MethodHandle first = MethodHandles.constant(Object.class, "first");
        MethodHandle second = MethodHandles.constant(Object.class, "second");
        ConstantCallSite site = new ConstantCallSite(first);
        CallSite cs = site;

        try {
            cs.setTarget(second);
            throw new AssertionError("setTarget did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            site.setTarget(first);
            throw new AssertionError("setTarget on ConstantCallSite did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        if (site.getTarget() != first) {
            throw new AssertionError("Target changed after rejected setTarget call");
        }
    }
}
