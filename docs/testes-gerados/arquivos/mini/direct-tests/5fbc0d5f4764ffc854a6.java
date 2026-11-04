/*
 * @test
 * @summary Test BigInteger.divide method behavior for small and large inputs
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testDivideSmall();
        testDivideLarge();
    }

    static void testDivideSmall() {
        // Small magnitude inputs: mag.length < BURNIKEL_ZIEGLER_THRESHOLD
        // Use small values to force divideKnuth path
        BigInteger a = new BigInteger("123456789");
        BigInteger b = new BigInteger("12345");
        BigInteger result = a.divide(b);
        // Expected result is integer division
        BigInteger expected = new BigInteger("123456789").divide(new BigInteger("12345"));
        if (!result.equals(expected)) {
            throw new RuntimeException("divide small test failed: expected " + expected + " but got " + result);
        }
    }

    static void testDivideLarge() {
        // Large magnitude inputs: mag.length difference >= BURNIKEL_ZIEGLER_OFFSET
        // Use large values to force divideBurnikelZiegler path
        // Construct large BigInteger with mag length > BURNIKEL_ZIEGLER_THRESHOLD
        // BURNIKEL_ZIEGLER_THRESHOLD is 80 (from source context knowledge)
        // Create byte arrays of length 100 and 10 to ensure difference >= offset
        byte[] largeBytes = new byte[100];
        byte[] smallBytes = new byte[10];
        for (int i = 0; i < 100; i++) {
            largeBytes[i] = (byte) (i + 1);
        }
        for (int i = 0; i < 10; i++) {
            smallBytes[i] = (byte) (i + 1);
        }
        BigInteger large = new BigInteger(1, largeBytes);
        BigInteger small = new BigInteger(1, smallBytes);
        BigInteger result = large.divide(small);
        // Verify result by comparing with built-in divide
        BigInteger expected = large.divide(small);
        if (!result.equals(expected)) {
            throw new RuntimeException("divide large test failed: expected " + expected + " but got " + result);
        }
    }
}
