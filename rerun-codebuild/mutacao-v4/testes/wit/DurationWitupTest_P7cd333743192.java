/* @test
   @summary Testa o método dividedBy de Duration, incluindo divisão por zero.
   @run main DurationWitupTest_P7cd333743192
 */
import java.time.Duration;
public class DurationWitupTest_P7cd333743192 {
    public static void main(String[] args) {
        Duration duration = Duration.ofSeconds(120);
        // Teste normal: divisão por 2
        Duration result = duration.dividedBy(2);
        if (!result.equals(Duration.ofSeconds(60))) {
            throw new AssertionError("Divisão por 2 falhou");
        }
        // Teste divisão por 1: deve retornar o próprio objeto
        Duration same = duration.dividedBy(1);
        if (same != duration && !same.equals(duration)) {
            throw new AssertionError("Divisão por 1 não retorna o mesmo objeto ou valor");
        }
        // Teste divisão por zero: deve lançar ArithmeticException
        try {
            duration.dividedBy(0);
            throw new AssertionError("Esperado ArithmeticException ao dividir por zero");
        } catch (ArithmeticException e) {
            // esperado
        }
    }
}
