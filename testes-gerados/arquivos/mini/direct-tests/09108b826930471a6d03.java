/*
 * @test
 * @summary Test ConstantCallSite.dynamicInvoker() returns the target MethodHandle
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ConstantCallSiteWitupTest {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findVirtual(String.class, "toLowerCase", MethodType.methodType(String.class));
        ConstantCallSite ccs = new ConstantCallSite(mh);

        MethodHandle dynamicInvoker = ccs.dynamicInvoker();

        // Check that dynamicInvoker returns the same MethodHandle as getTarget
        if (dynamicInvoker != ccs.getTarget()) {
            throw new AssertionError("dynamicInvoker() did not return the target MethodHandle");
        }

        // Check that the dynamicInvoker works as expected
        String result = (String) dynamicInvoker.invokeExact("HELLO");
        if (!"hello".equals(result)) {
            throw new AssertionError("dynamicInvoker() returned MethodHandle did not invoke correctly");
        }
    }
}
