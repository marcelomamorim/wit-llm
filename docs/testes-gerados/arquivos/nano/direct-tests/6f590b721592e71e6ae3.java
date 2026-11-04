/* @test    @summary Test BigInteger(int, Random) constructor for deterministic behavior    @run main BigIntegerWitupTest */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testBigIntegerConstructor();
    }

    public static void testBigIntegerConstructor() {
        // Test with fixed seed for deterministic output
        long seed = 123456789L;
        Random rnd = new Random(seed);
        int numBits = 64;
        BigInteger result1 = new BigInteger(numBits, rnd);

        // Reinitialize with same seed to verify determinism
        rnd = new Random(seed);
        BigInteger result2 = new BigInteger(numBits, rnd);

        // Verify that both results are equal, ensuring deterministic behavior
        if (!result1.equals(result2)) {
            throw new AssertionError("BigInteger(int, Random) constructor is not deterministic with fixed seed");
        }

        // Additional check: ensure the bit length is as expected
        if (result1.bitLength() > numBits || result1.bitLength() < 1) {
            throw new AssertionError("Resulting BigInteger has unexpected bit length");
        }
    }
}
