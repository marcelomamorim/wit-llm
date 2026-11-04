/* @test    @summary Test for ConstantCallSite.setTarget method to verify UnsupportedOperationException is thrown.    @run main ConstantCallSiteWitupTest */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.ConstantCallSite;

public class ConstantCallSiteWitupTest {

    public static void main(String[] args) {
        testSetTargetThrowsException();
    }

    public static void testSetTargetThrowsException() {
        try {
            MethodHandle dummyHandle = null; // Using null as dummy, since actual handle is not invoked
            ConstantCallSite callSite = new ConstantCallSite(dummyHandle);
            callSite.setTarget(dummyHandle);
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was correctly thrown.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
