/*
 * @test
 * @summary Witup-generated tests for jdk.internal.misc.Signal constructor
 * @modules java.base/jdk.internal.misc
 * @run main SignalWitupTest
 */
import jdk.internal.misc.Signal;

public class SignalWitupTest {
    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testSigPrefixRejected();
        testUnknownNameRejected();
        testValidNameIsAccepted();
        testSameNameSameNumber();
    }

    static void testNullNameThrowsNPE() {
        try {
            new Signal(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name");
    }

    static void testSigPrefixRejected() {
        try {
            new Signal("SIGINT");
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for SIG prefix");
    }

    static void testUnknownNameRejected() {
        try {
            new Signal("NOSUCHSIGNALNAME");
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for unknown signal");
    }

    static void testValidNameIsAccepted() {
        Signal s = new Signal("INT");
        if (!"INT".equals(s.getName())) {
            throw new RuntimeException("unexpected name: " + s.getName());
        }
        if (s.getNumber() <= 0) {
            throw new RuntimeException("unexpected number: " + s.getNumber());
        }
    }

    static void testSameNameSameNumber() {
        Signal a = new Signal("INT");
        Signal b = new Signal("INT");
        if (a.getNumber() != b.getNumber()) {
            throw new RuntimeException("same name produced different numbers");
        }
    }
}
