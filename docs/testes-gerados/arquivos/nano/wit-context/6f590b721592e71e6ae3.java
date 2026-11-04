/* @test    @summary Test for BigInteger(int, Random) constructor with valid and invalid inputs    @run main BigIntegerWitupTest */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testValidInput();
        testNegativeNumBits();
    }

    private static void testValidInput() {
        Random rnd = new Random(12345L);
        int numBits = 10;
        BigInteger bi = new BigInteger(numBits, rnd);
        System.out.println("Valid input test: " + bi);
        // Verifica se o BigInteger não é nulo e tem o número de bits esperado ou menor
        assert bi != null : "BigInteger should not be null";
        assert bi.bitLength() <= numBits : "Bit length should be less than or equal to numBits";
    }

    private static void testNegativeNumBits() {
        Random rnd = new Random(12345L);
        int numBits = -5;
        try {
            BigInteger bi = new BigInteger(numBits, rnd);
            // Se não lançar exceção, o teste falha
            assert false : "Expected IllegalArgumentException for negative numBits";
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for numBits = " + numBits);
        }
    }
}
