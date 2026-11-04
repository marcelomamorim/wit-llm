/*
 * @test
 * @summary Verify SecretKeySpec(byte[], String) argument validation and defensive key copying
 * @run main SecretKeySpecWitupTest
 */

public class SecretKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullKeyRejected();
        testNullAlgorithmRejected();
        testEmptyKeyRejected();
        testSingleByteKeyAccepted();
        testAlgorithmPreserved();
        testKeyIsCopiedOnConstruction();
    }

    static void testNullKeyRejected() {
        expectIllegalArgument(null, "AES", "null key");
    }

    static void testNullAlgorithmRejected() {
        expectIllegalArgument(new byte[16], null, "null algorithm");
    }

    static void testEmptyKeyRejected() {
        expectIllegalArgument(new byte[0], "AES", "empty key");
    }

    static void testSingleByteKeyAccepted() {
        SecretKeySpec spec = new SecretKeySpec(new byte[]{0x01}, "AES");
        if (spec == null) {
            throw new RuntimeException("Single-byte key should be accepted");
        }
    }

    static void testAlgorithmPreserved() {
        SecretKeySpec spec = new SecretKeySpec(new byte[8], "DES");
        if (!"DES".equals(spec.getAlgorithm())) {
            throw new RuntimeException("Unexpected algorithm: " + spec.getAlgorithm());
        }
    }

    static void testKeyIsCopiedOnConstruction() {
        byte[] material = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        SecretKeySpec spec = new SecretKeySpec(material, "AES");
        material[0] = 99;
        byte[] encoded = spec.getEncoded();
        if (encoded[0] != 1) {
            throw new RuntimeException("Key was not defensively copied: " + encoded[0]);
        }
    }

    static void expectIllegalArgument(byte[] key, String algorithm, String label) {
        try {
            new SecretKeySpec(key, algorithm);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for " + label);
    }
}
