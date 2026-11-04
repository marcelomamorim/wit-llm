/*
 * @test
 * @summary Verifies SecureRandom.generateSeed argument validation and observable seed output
 * @run main SecureRandomWitupTest
 */

public class SecureRandomWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeNumBytesThrows();
        testZeroNumBytesReturnsEmptyArray();
        testPositiveNumBytesReturnsArrayOfRequestedLength();
        System.out.println("PASSED");
    }

    static void testNegativeNumBytesThrows() {
        java.security.SecureRandom random = new java.security.SecureRandom();
        try {
            random.generateSeed(-1);
            throw new RuntimeException("Expected IllegalArgumentException for numBytes = -1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testZeroNumBytesReturnsEmptyArray() {
        java.security.SecureRandom random = new java.security.SecureRandom();
        byte[] seed = random.generateSeed(0);
        if (seed == null || seed.length != 0) {
            throw new RuntimeException("Expected empty seed array for numBytes = 0");
        }
    }

    static void testPositiveNumBytesReturnsArrayOfRequestedLength() {
        java.security.SecureRandom random = new java.security.SecureRandom();
        byte[] seed = random.generateSeed(32);
        if (seed == null || seed.length != 32) {
            throw new RuntimeException("Expected seed array of length 32");
        }
    }
}
