/*
 * @test
 * @summary Smoke tests for jdk.internal.misc.Signal public constructor and name, and unknown-signal rejection
 * @modules java.base/jdk.internal.misc
 * @run main SignalWitupTest
 */

import jdk.internal.misc.Signal;

public class SignalWitupTest {

    public static void main(String[] args) {
        testKnownSignalName();
        testUnknownSignalRejected();
    }

    static void testKnownSignalName() {
        Signal sig = new Signal("INT");
        if (!"INT".equals(sig.getName())) {
            throw new RuntimeException("Unexpected name: " + sig.getName());
        }
    }

    static void testUnknownSignalRejected() {
        try {
            new Signal("NO_SUCH_SIGNAL_WITUP");
            throw new RuntimeException("IllegalArgumentException expected for unknown signal");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
