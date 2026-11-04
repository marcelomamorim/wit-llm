/*
 * @test
 * @summary Tests Signal.raise null and unhandled-signal exception behavior
 * @modules java.base/jdk.internal.misc
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;

public class SignalWitupTest {
    public static void main(String[] args) throws Exception {
        testRaiseNullThrowsNPE();
        testRaiseUnhandledSignalThrowsIAE();
    }

    static void testRaiseNullThrowsNPE() {
        try {
            Signal.raise(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testRaiseUnhandledSignalThrowsIAE() {
        Signal sig = new Signal("INT");
        try {
            Signal.raise(sig);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }
}
