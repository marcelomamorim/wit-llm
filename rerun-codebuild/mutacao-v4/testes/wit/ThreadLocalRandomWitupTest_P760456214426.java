/* @test
   @summary Testa o método nextInt com limites válidos e inválidos.
   @run main ThreadLocalRandomWitupTest_P760456214426
 */
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomWitupTest_P760456214426 {
    public static void main(String[] args) {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int validOrigin = 0;
        int validBound = 10;
        int result = rand.nextInt(validOrigin, validBound);
        if (result < validOrigin || result >= validBound) {
            throw new AssertionError("Resultado fora do intervalo esperado");
        }
        // Teste de limite inválido: origin >= bound
        try {
            rand.nextInt(10, 10);
            throw new AssertionError("Esperado IllegalArgumentException para limites iguais");
        } catch (IllegalArgumentException e) {
            // esperado
        }
        try {
            rand.nextInt(20, 10);
            throw new AssertionError("Esperado IllegalArgumentException para origin > bound");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
}
