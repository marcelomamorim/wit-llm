/*
 * @test
 * @summary Test Signal.raise(Signal) method behavior including null and unhandled signals
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;

public class SignalWitupTest {
    public static void main(String[] args) {
        testRaiseNullSignal();
        testRaiseUnhandledSignal();
        // Note: cannot test successful raise due to native raise0 and handler setup complexity
    }

    private static void testRaiseNullSignal() {
        try {
            Signal.raise(null);
            throw new Error("Expected NullPointerException for null signal");
        } catch (NullPointerException npe) {
            if (!"sig".equals(npe.getMessage())) {
                throw new Error("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }

    private static void testRaiseUnhandledSignal() {
        // Create a Signal with a likely unused name to avoid conflicts
        Signal sig = null;
        try {
            sig = new Signal("WITUP_TEST_UNHANDLED");
        } catch (IllegalArgumentException iae) {
            // If signal name is invalid on platform, skip this test
            return;
        }

        try {
            Signal.raise(sig);
            throw new Error("Expected IllegalArgumentException for unhandled signal");
        } catch (IllegalArgumentException iae) {
            String msg = iae.getMessage();
            if (msg == null || !msg.contains("Unhandled signal")) {
                throw new Error("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}
