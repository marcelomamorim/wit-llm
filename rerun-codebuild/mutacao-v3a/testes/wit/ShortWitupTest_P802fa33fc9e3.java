/* @test    @summary Teste do método java.lang.Short.decode(String) para verificar comportamentos observáveis e exceções.    @run main ShortWitupTest_P802fa33fc9e3 */

import java.lang.Short;

public class ShortWitupTest_P802fa33fc9e3 {

    public static void main(String[] args) {
        testDecodeValidValues();
        testDecodeOutOfRange();
        testDecodeInvalidFormat();
    }

    static void testDecodeValidValues() {
        try {
            // Teste com valor positivo dentro do range
            Short s1 = Short.decode("12345");
            assert s1 != null : "Decodificação de valor válido falhou";
            assert s1.shortValue() == 12345 : "Valor decodificado incorreto";

            // Teste com valor negativo dentro do range
            Short s2 = Short.decode("-12345");
            assert s2 != null : "Decodificação de valor negativo válido falhou";
            assert s2.shortValue() == -12345 : "Valor negativo decodificado incorretamente";

            // Teste com valor zero
            Short s3 = Short.decode("0");
            assert s3 != null : "Decodificação de zero falhou";
            assert s3.shortValue() == 0 : "Valor zero decodificado incorretamente";
        } catch (NumberFormatException e) {
            assert false : "Exceção inesperada ao decodificar valores válidos: " + e;
        }
    }

    static void testDecodeOutOfRange() {
        try {
            // Valor acima do máximo
            Short.decode("32768");
            assert false : "Esperada NumberFormatException para valor acima do máximo";
        } catch (NumberFormatException e) {
            assert e.getMessage().contains("out of range") : "Mensagem de exceção inesperada";
        }
        try {
            // Valor abaixo do mínimo
            Short.decode("-32769");
            assert false : "Esperada NumberFormatException para valor abaixo do mínimo";
        } catch (NumberFormatException e) {
            assert e.getMessage().contains("out of range") : "Mensagem de exceção inesperada";
        }
    }

    static void testDecodeInvalidFormat() {
        String[] invalidInputs = {"abc", "12.34", "", "+"};
        for (String input : invalidInputs) {
            try {
                Short.decode(input);
                assert false : "Esperada NumberFormatException para entrada inválida: '" + input + "'";
            } catch (NumberFormatException e) {
                // esperado
            }
        }
    }
}
