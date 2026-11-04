/*
 * @test
 * @summary Test ConstantCallSite's setTarget method to ensure it throws UnsupportedOperationException
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.ConstantCallSite;

public class ConstantCallSiteWitupTest {

    public static void main(String[] args) {
        testSetTargetThrows();
    }

    private static void testSetTargetThrows() {
        MethodHandle dummyHandle = null;
        try {
            // Create a ConstantCallSite instance with a dummy MethodHandle
            ConstantCallSite callSite = new ConstantCallSite(dummyHandle);
            // Attempt to invoke setTarget, expecting UnsupportedOperationException
            try {
                callSite.setTarget(dummyHandle);
                // If no exception thrown, test fails
                throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
            } catch (UnsupportedOperationException e) {
                // Expected exception, test passes
            }
        } catch (Throwable t) {
            // Any other exception indicates test failure
            throw new AssertionError("Unexpected exception during test", t);
        }
    }
}
