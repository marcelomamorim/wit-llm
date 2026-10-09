/* @test
   @summary Testa o método Objects.requireNonNull com entrada nula.
   @run main ObjectsWitupTest_P0b11e20a64d7
 */
import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsWitupTest_P0b11e20a64d7 {
    public static void main(String[] args) {
        testRequireNonNullWithNull();
        testRequireNonNullWithNonNull();
    }

    private static void testRequireNonNullWithNull() {
        try {
            Objects.requireNonNull(null, () -> "Mensagem de erro personalizada");
            throw new AssertionError("Esperado NullPointerException não lançado");
        } catch (NullPointerException e) {
            // Teste bem-sucedido: exceção lançada
            String message = e.getMessage();
            if (message != null && !message.equals("Mensagem de erro personalizada")) {
                throw new AssertionError("Mensagem de exceção não corresponde ao esperado");
            }
        }
    }

    private static void testRequireNonNullWithNonNull() {
        String obj = "valor";
        String result = Objects.requireNonNull(obj, () -> "Mensagem de erro personalizada");
        if (result != obj) {
            throw new AssertionError("O método não retornou o objeto esperado");
        }
    }
}
