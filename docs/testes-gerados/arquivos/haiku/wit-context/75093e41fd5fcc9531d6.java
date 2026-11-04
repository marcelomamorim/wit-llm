/*
 * @test
 * @summary Witup-derived tests for RC5ParameterSpec IV validation in the 4-argument constructor
 * @run main RC5ParameterSpecWitupTest
 */

import javax.crypto.spec.RC5ParameterSpec;

public class RC5ParameterSpecWitupTest {

    public static void main(String[] args) {
        testNullIvRejected();
        testShortIvRejectedWordSize32();
        testShortIvRejectedWordSize64();
        testShortIvRejectedWordSize16();
        testExactIvAccepted();
        testLongerIvAccepted();
        testThreeArgConstructorAccepted();
    }

    static void testNullIvRejected() {
        expectIllegalArgument(12, 12, 32, null);
    }

    static void testShortIvRejectedWordSize32() {
        expectIllegalArgument(12, 12, 32, new byte[7]);
    }

    static void testShortIvRejectedWordSize64() {
        expectIllegalArgument(12, 12, 64, new byte[15]);
    }

    static void testShortIvRejectedWordSize16() {
        expectIllegalArgument(12, 12, 16, new byte[3]);
    }

    static void testExactIvAccepted() {
        expectAccepted(12, 12, 32, new byte[8]);
        expectAccepted(12, 12, 16, new byte[4]);
    }

    static void testLongerIvAccepted() {
        expectAccepted(12, 12, 32, new byte[20]);
    }

    static void testThreeArgConstructorAccepted() {
        RC5ParameterSpec spec = new RC5ParameterSpec(12, 12, 32);
        if (spec == null) {
            throw new RuntimeException("constructor returned null");
        }
    }

    static void expectIllegalArgument(int version, int rounds, int wordSize, byte[] iv) {
        try {
            new RC5ParameterSpec(version, rounds, wordSize, iv);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException");
    }

    static void expectAccepted(int version, int rounds, int wordSize, byte[] iv) {
        RC5ParameterSpec spec = new RC5ParameterSpec(version, rounds, wordSize, iv);
        if (spec == null) {
            throw new RuntimeException("constructor returned null");
        }
    }
}
