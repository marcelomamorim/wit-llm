/* @test
   @summary Testa o método ints() de java.util.Random.
   @run main RandomWitupTest_Pbb929772ec5b
 */
import java.util.Random;
import java.util.stream.IntStream;
public class RandomWitupTest_Pbb929772ec5b {
    public static void main(String[] args) {
        Random rnd = new Random();
        // Teste básico: gera um IntStream e verifica se não é nulo
        IntStream stream = rnd.ints();
        if (stream == null) {
            throw new AssertionError("Stream nulo") ;
        }
        // Teste de comportamento observável: consome alguns elementos
        int[] sample = stream.limit(10).toArray();
        if (sample.length != 10) {
            throw new AssertionError("Tamanho do array diferente de 10") ;
        }
        // Teste de repetibilidade com seed fixo
        Random rnd2 = new Random(12345L);
        int[] seq1 = rnd2.ints().limit(5).toArray();
        rnd2 = new Random(12345L);
        int[] seq2 = rnd2.ints().limit(5).toArray();
        if (!java.util.Arrays.equals(seq1, seq2)) {
            throw new AssertionError("Sequências com mesma seed diferem") ;
        }
        // Teste de exceção: não aplicável ao método ints() sem argumentos
        // Teste de comportamento sob condições de erro não aplicável aqui
    }
}
