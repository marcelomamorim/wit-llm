/* @test    @summary Teste do construtor BigInteger(String val) com entrada vazia para verificar NumberFormatException    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testEmptyStringInput();
        testValidNumber();
    }

    private static void testEmptyStringInput() {
        try {
            new BigInteger("");
            System.err.println("Falha: Esperado NumberFormatException para entrada vazia.");
            System.exit(1);
        } catch (NumberFormatException e) {
            // Esperado
        }
    }

    private static void testValidNumber() {
        try {
            BigInteger bi = new BigInteger("12345");
            if (bi.equals(BigInteger.valueOf(12345))) {
                System.out.println("Sucesso: BigInteger("12345")" + " criado corretamente.");
            } else {
                System.err.println("Falha: BigInteger("12345")" + " não corresponde ao valor esperado.");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.err.println("Falha: NumberFormatException inesperada para entrada válida.");
            System.exit(1);
        }
    }
}
