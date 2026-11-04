/*
 * @test
 * @summary Testa o construtor IvParameterSpec(byte[] iv) com entrada nula.
 * @run main IvParameterSpecWitupTest
 */

import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidInput();
    }

    private static void testConstructorWithNull() {
        try {
            new IvParameterSpec(null);
            System.err.println("Falha: Esperado IllegalArgumentException ao passar null.");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: IllegalArgumentException lançada ao passar null.");
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada: " + e);
        }
    }

    private static void testConstructorWithValidInput() {
        byte[] validBytes = {1, 2, 3, 4};
        try {
            IvParameterSpec spec = new IvParameterSpec(validBytes);
            byte[] ivReturned = spec.getIV();
            boolean equals = java.util.Arrays.equals(validBytes, ivReturned);
            if (equals) {
                System.out.println("Sucesso: IV retornado corresponde ao input.");
            } else {
                System.err.println("Falha: IV retornado difere do input.");
            }
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada: " + e);
        }
    }
}
