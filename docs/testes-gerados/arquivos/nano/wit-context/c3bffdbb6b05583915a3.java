/* @test    @summary Testa o construtor BigInteger(int signum, byte[] magnitude) com entradas válidas e inválidas    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testValidSignumAndMagnitude();
        testInvalidSignum();
    }

    private static void testValidSignumAndMagnitude() {
        byte[] magnitude = {1, 2, 3};
        try {
            BigInteger bi = new BigInteger(1, magnitude);
            System.out.println("Valid input: " + bi);
        } catch (Exception e) {
            System.err.println("Unexpected exception for valid input: " + e);
        }
    }

    private static void testInvalidSignum() {
        byte[] magnitude = {1, 2, 3};
        try {
            BigInteger bi = new BigInteger(2, magnitude); // signum inválido
            System.err.println("Expected exception not thrown for signum=2");
        } catch (NumberFormatException e) {
            System.out.println("Correctly caught NumberFormatException for signum=2");
        } catch (Exception e) {
            System.err.println("Unexpected exception type: " + e);
        }
        try {
            BigInteger bi = new BigInteger(-2, magnitude); // signum inválido negativo
            System.err.println("Expected exception not thrown for signum=-2");
        } catch (NumberFormatException e) {
            System.out.println("Correctly caught NumberFormatException for signum=-2");
        }
    }
}
