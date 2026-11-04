/*
 * @test
 * @summary Checks argument validation of the RC2ParameterSpec constructors that take an IV
 * @run main RC2ParameterSpecWitupTest
 */

public class RC2ParameterSpecWitupTest {

    public static void main(String[] args) {
        testNullIvRejectedWithOffset();
        testNullIvRejectedByTwoArgConstructor();
        testShortIvRejectedByTwoArgConstructor();
        testIvExactlyEightBytesAccepted();
        testOffsetLeavingExactlyEightBytesAccepted();
        testOffsetLeavingSevenBytesRejected();
        testOffsetBeyondArrayRejected();
        testNegativeOffsetRejected();
        testNoIvConstructorAccepted();
    }

    static void expectIllegalArgument(String name, byte[] iv, int offset) {
        try {
            new javax.crypto.spec.RC2ParameterSpec(64, iv, offset);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException(name + ": expected IllegalArgumentException");
    }

    static void testNullIvRejectedWithOffset() {
        expectIllegalArgument("null iv with offset 0", null, 0);
    }

    static void testNullIvRejectedByTwoArgConstructor() {
        try {
            new javax.crypto.spec.RC2ParameterSpec(64, null);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("null iv: expected IllegalArgumentException");
    }

    static void testShortIvRejectedByTwoArgConstructor() {
        try {
            new javax.crypto.spec.RC2ParameterSpec(64, new byte[7]);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("7-byte iv: expected IllegalArgumentException");
    }

    static void testIvExactlyEightBytesAccepted() {
        new javax.crypto.spec.RC2ParameterSpec(64, new byte[8]);
        new javax.crypto.spec.RC2ParameterSpec(64, new byte[8], 0);
    }

    static void testOffsetLeavingExactlyEightBytesAccepted() {
        new javax.crypto.spec.RC2ParameterSpec(64, new byte[10], 2);
    }

    static void testOffsetLeavingSevenBytesRejected() {
        expectIllegalArgument("length 10, offset 3", new byte[10], 3);
    }

    static void testOffsetBeyondArrayRejected() {
        expectIllegalArgument("length 8, offset 5", new byte[8], 5);
    }

    static void testNegativeOffsetRejected() {
        try {
            new javax.crypto.spec.RC2ParameterSpec(64, new byte[8], -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("negative offset: expected an exception");
    }

    static void testNoIvConstructorAccepted() {
        new javax.crypto.spec.RC2ParameterSpec(128);
    }
}
