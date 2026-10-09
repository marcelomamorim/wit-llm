/* @test
   @summary Testa o método statico of(int) da classe java.time.chrono.JapaneseEra para comportamentos válidos e inválidos.
   @run main JapaneseEraWitupTest_P3b8cb79bb9fd
 */
import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseEraWitupTest_P3b8cb79bb9fd {
    public static void main(String[] args) {
        testValidEras();
        testInvalidEras();
    }

    static void testValidEras() {
        // Testa alguns valores válidos conhecidos
        try {
            JapaneseEra era1 = JapaneseEra.of(1);
            if (era1 == null) {
                throw new AssertionError("Era 1 retornou null");
            }
        } catch (Exception e) {
            throw new AssertionError("Falha ao obter era válida 1: " + e);
        }
        try {
            JapaneseEra era2 = JapaneseEra.of(2);
            if (era2 == null) {
                throw new AssertionError("Era 2 retornou null");
            }
        } catch (Exception e) {
            throw new AssertionError("Falha ao obter era válida 2: " + e);
        }
    }

    static void testInvalidEras() {
        // Testa valores inválidos que devem lançar DateTimeException
        int[] invalidValues = { Integer.MIN_VALUE, -2, 0, -1, JapaneseEra.KNOWN_ERAS.length, JapaneseEra.KNOWN_ERAS.length + 1, Integer.MAX_VALUE };
        for (int val : invalidValues) {
            try {
                JapaneseEra.of(val);
                throw new AssertionError("Esperava exceção para valor: " + val);
            } catch (DateTimeException e) {
                // esperado
            } catch (Exception e) {
                throw new AssertionError("Exceção inesperada para valor " + val + ": " + e);
            }
        }
    }
}
