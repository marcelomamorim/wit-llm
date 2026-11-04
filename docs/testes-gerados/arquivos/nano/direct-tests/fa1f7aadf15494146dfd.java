/* @test    @summary Test the handle method of Signal class for unsupported operation    @run main SignalWitupTest */

import jdk.internal.misc.Signal;

public class SignalWitupTest {
    public static void main(String[] args) {
        testHandleMethodThrowsUnsupported();
    }

    private static void testHandleMethodThrowsUnsupported() {
        Signal signal = new Signal("INT");
        try {
            Signal.handle(signal);
            System.out.println("Test failed: No exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected exception");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }
}
