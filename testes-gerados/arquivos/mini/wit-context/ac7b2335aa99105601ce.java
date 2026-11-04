/*
 * @test
 * @summary Test that ConstantCallSite.setTarget always throws UnsupportedOperationException
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class ConstantCallSiteWitupTest {
    public static void main(String[] args) throws Throwable {
        MethodHandle mh = MethodHandles.constant(String.class, "test");
        ConstantCallSite ccs = new ConstantCallSite(mh);
        boolean thrown = false;
        try {
            ccs.setTarget(mh);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from setTarget");
        }
    }
}
