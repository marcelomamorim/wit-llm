/* @test    @summary Testa o construtor MathContext(int) com valores válidos e inválidos    @run main MathContextWitupTest */

import java.math.*;

public class MathContextWitupTest {

    public static void main(String[] args) {
        testValidPrecision();
        testInvalidPrecision();
    }

    private static void testValidPrecision() {
        try {
            MathContext mc = new MathContext(10);
            if (mc == null) {
                throw new AssertionError("MathContext com precisão válida retornou null");
            }
        } catch (Exception e) {
            throw new AssertionError("Falha ao criar MathContext com precisão válida", e);
        }
    }

    private static void testInvalidPrecision() {
        try {
            new MathContext(-1);
            throw new AssertionError("Esperado IllegalArgumentException para precisão negativa")
        } catch (IllegalArgumentException e) {
            // esperado
        } catch (Exception e) {
            throw new AssertionError("Exceção inesperada ao passar precisão negativa", e);
        }
    }
}
