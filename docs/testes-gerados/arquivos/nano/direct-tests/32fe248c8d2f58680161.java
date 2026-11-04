/* @test    @summary Test Signal constructor with valid and invalid names    @run main SignalWitupTest */

import jdk.internal.misc.Signal;

public class SignalWitupTest {
    public static void main(String[] args) {
        testValidSignalName();
        testInvalidSignalNameStartsWithSIG();
        testInvalidSignalNameUnknown();
    }

    static void testValidSignalName() {
        try {
            Signal sig = new Signal("INT");
            assert "INT".equals(sig.getName()) : "Expected name 'INT', got " + sig.getName();
            assert sig.getNumber() >= 0 : "Signal number should be non-negative";
        } catch (Exception e) {
            throw new AssertionError("Valid signal name threw exception", e);
        }
    }

    static void testInvalidSignalNameStartsWithSIG() {
        try {
            new Signal("SIGINT");
            throw new AssertionError("Expected IllegalArgumentException for name starting with SIG");
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Unknown signal") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testInvalidSignalNameUnknown() {
        try {
            new Signal("UNKNOWN_SIGNAL");
            throw new AssertionError("Expected IllegalArgumentException for unknown signal");
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Unknown signal") : "Unexpected message: " + e.getMessage();
        }
    }
}
