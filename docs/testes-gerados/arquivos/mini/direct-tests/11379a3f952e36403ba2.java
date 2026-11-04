/*
 * @test
 * @summary Test jdk.internal.misc.Signal.raise(Signal) method behavior
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;
import java.lang.IllegalArgumentException;
import java.util.Objects;

public class SignalWitupTest {
    public static void main(String[] args) throws Exception {
        testRaiseWithNullSignal();
        testRaiseWithUnhandledSignal();
        testRaiseWithHandledSignal();
    }

    private static void testRaiseWithNullSignal() {
        try {
            Signal.raise(null);
            throw new RuntimeException("Expected NullPointerException for null signal");
        } catch (NullPointerException npe) {
            if (!"sig".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }

    private static void testRaiseWithUnhandledSignal() {
        Signal sig = new Signal("USR2"); // Assuming USR2 is not handled by default
        try {
            Signal.raise(sig);
            throw new RuntimeException("Expected IllegalArgumentException for unhandled signal");
        } catch (IllegalArgumentException iae) {
            String msg = iae.getMessage();
            if (msg == null || !msg.contains("Unhandled signal")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }

    private static void testRaiseWithHandledSignal() throws Exception {
        final Signal sig = new Signal("USR1");
        final boolean[] handlerCalled = {false};

        Signal.Handler handler = new Signal.Handler() {
            public void handle(Signal s) {
                if (s.equals(sig)) {
                    handlerCalled[0] = true;
                }
            }
        };

        Signal.handle(sig, handler);

        // Raise the signal; this should not throw
        Signal.raise(sig);

        // Wait briefly to allow handler thread to run
        Thread.sleep(100);

        if (!handlerCalled[0]) {
            throw new RuntimeException("Signal handler was not called after raise");
        }
    }
}
