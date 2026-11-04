/*
 * @test
 * @summary Test constructor Signal(String) for null and invalid names
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;

public class SignalWitupTest {
    public static void main(String[] args) {
        testNullNameThrows();
        testNameStartingWithSIGThrows();
        testUnknownSignalNameThrows();
        testValidSignalName();
    }

    private static void testNullNameThrows() {
        try {
            new Signal(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNameStartingWithSIGThrows() {
        try {
            new Signal("SIGINT");
            throw new RuntimeException("Expected IllegalArgumentException for name starting with SIG");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testUnknownSignalNameThrows() {
        try {
            new Signal("UNKNOWN_SIGNAL_NAME");
            throw new RuntimeException("Expected IllegalArgumentException for unknown signal name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testValidSignalName() {
        // Use a common valid signal name that should exist on all platforms
        // e.g. "INT" (interrupt signal)
        Signal sig = new Signal("INT");
        if (!"INT".equals(sig.getName())) {
            throw new RuntimeException("Signal name mismatch");
        }
        int num = sig.getNumber();
        if (num < 0) {
            throw new RuntimeException("Signal number should be non-negative");
        }
    }
}
