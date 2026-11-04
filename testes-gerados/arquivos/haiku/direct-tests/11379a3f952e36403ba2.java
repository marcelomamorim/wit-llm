/*
 * @test
 * @summary Checks Signal.raise argument validation: null signal and unhandled signal.
 * @modules java.base/jdk.internal.misc
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;

public class SignalWitupTest {

    public static void main(String[] args) {
        testRaiseNullThrowsNPE();
        testRaiseUnhandledSignalThrowsIAE();
    }

    static void testRaiseNullThrowsNPE() {
        try {
            Signal.raise(null);
            throw new RuntimeException("Expected NullPointerException for null signal");
        } catch (NullPointerException e) {
            if (!"sig".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NPE message: " + e.getMessage(), e);
            }
        }
    }

    static void testRaiseUnhandledSignalThrowsIAE() {
        Signal sig = new Signal("INT");
        try {
            Signal.raise(sig);
            throw new RuntimeException("Expected IllegalArgumentException for unhandled signal");
        } catch (IllegalArgumentException e) {
            // expected: no handler registered for this signal
        }
    }
}
