/* @test    @summary Test for jdk.internal.misc.Signal.handle(Signal sig) method
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;

public class SignalWitupTest {
    public static void main(String[] args) {
        testHandleThrowsUnsupportedOperationException();
    }

    public static void testHandleThrowsUnsupportedOperationException() {
        Signal signal = new Signal("INT");
        try {
            signal.handle(signal);
            System.out.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }
}
