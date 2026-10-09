/* @test    @summary Test for BigInteger.probablePrime(int, Random) handling edge case with bitLength < 2   @run main BigIntegerWitupTest_P7279352076f4 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest_P7279352076f4 {

    public static void main(String[] args) {
        testProbablePrimeWithInvalidBitLength();
        testProbablePrimeWithValidBitLength();
    }

    private static void testProbablePrimeWithInvalidBitLength() {
        Random rnd = new Random(12345);
        int invalidBitLength = 1;
        try {
            BigInteger result = BigInteger.probablePrime(invalidBitLength, rnd);
            // Se não lançar exceção, falha o teste
            System.err.println("FAIL: Expected ArithmeticException for bitLength < 2, but got result: " + result);
        } catch (ArithmeticException e) {
            // Verifica se a mensagem é a esperada
            if ("bitLength < 2".equals(e.getMessage())) {
                System.out.println("PASS: Correct exception thrown for bitLength < 2");
            } else {
                System.err.println("FAIL: Unexpected message: " + e.getMessage());
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testProbablePrimeWithValidBitLength() {
        Random rnd = new Random(54321);
        int validBitLength = 5;
        try {
            BigInteger prime = BigInteger.probablePrime(validBitLength, rnd);
            if (prime.bitLength() >= validBitLength && prime.isProbablePrime(10)) {
                System.out.println("PASS: Generated probable prime with bitLength >= " + validBitLength);
            } else {
                System.err.println("FAIL: Generated number does not meet prime criteria: " + prime);
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
