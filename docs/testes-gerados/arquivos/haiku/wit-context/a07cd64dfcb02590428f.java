/*
 * @test
 * @summary Verifies IllegalArgumentException behavior of RC2ParameterSpec(int, byte[]) for null, short, exact and longer IV buffers
 * @run main RC2ParameterSpecWitupTest
 */

import javax.crypto.spec.RC2ParameterSpec;

public class RC2ParameterSpecWitupTest {

    public static void main(String[] args) {
        testNullIvRejected();
        testEmptyIvRejected();
        testShortIvRejected();
        testExactEightByteIvAccepted();
        testLongerIvAccepted();
    }

    static void testNullIvRejected() {
        expectIllegalArgument(null, "null iv");
    }

    static void testEmptyIvRejected() {
        expectIllegalArgument(new byte[0], "empty iv");
    }

    static void testShortIvRejected() {
        expectIllegalArgument(new byte[7], "7-byte iv");
    }

    static void testExactEightByteIvAccepted() {
        new RC2ParameterSpec(64, new byte[8]);
    }

    static void testLongerIvAccepted() {
        new RC2ParameterSpec(128, new byte[16]);
    }

    static void expectIllegalArgument(byte[] iv, String name) {
        try {
            new RC2ParameterSpec(64, iv);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException(name + ": expected IllegalArgumentException");
    }
}
