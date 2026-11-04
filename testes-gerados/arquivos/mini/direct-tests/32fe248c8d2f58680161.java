/*
 * @test
 * @summary Test constructor of jdk.internal.misc.Signal for valid and invalid names
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;

public class SignalWitupTest {
    public static void main(String[] args) {
        testValidSignalName();
        testNullSignalName();
        testSignalNameWithSIGPrefix();
        testUnknownSignalName();
    }

    private static void testValidSignalName() {
        // Typical valid signal name (without SIG prefix)
        Signal s = new Signal("INT");
        if (!"INT".equals(s.getName())) {
            throw new RuntimeException("Signal name mismatch: expected INT but got " + s.getName());
        }
        int num = s.getNumber();
        if (num < 0) {
            throw new RuntimeException("Signal number should be non-negative for valid signal");
        }
    }

    private static void testNullSignalName() {
        try {
            new Signal(null);
            throw new RuntimeException("Expected NullPointerException for null signal name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testSignalNameWithSIGPrefix() {
        try {
            new Signal("SIGINT");
            throw new RuntimeException("Expected IllegalArgumentException for signal name starting with SIG");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testUnknownSignalName() {
        try {
            new Signal("UNKNOWN_SIGNAL_NAME");
            throw new RuntimeException("Expected IllegalArgumentException for unknown signal name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
