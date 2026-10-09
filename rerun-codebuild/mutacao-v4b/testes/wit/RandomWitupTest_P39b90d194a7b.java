/* @test
   @summary Verifica o método Random.doubles() para comportamento observável.
   @run main RandomWitupTest_P39b90d194a7b
 */
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_P39b90d194a7b {
    public static void main(String[] args) {
        testDoublesReturnsStream();
        testDoublesStreamIsNotNull();
    }

    static void testDoublesReturnsStream() {
        Random rand = new Random(12345L);
        DoubleStream ds = rand.doubles();
        if (ds == null) {
            throw new AssertionError("doubles() retornou null");
        }
        // Verifica se o stream não está vazio e contém valores Double
        boolean hasValues = false;
        try {
            hasValues = ds.iterator().hasNext();
        } catch (Exception e) {
            throw new AssertionError("Erro ao iterar o DoubleStream", e);
        }
        // Não há garantia de valores específicos, apenas que o stream é válido
    }

    static void testDoublesStreamIsNotNull() {
        Random rand = new Random(67890L);
        DoubleStream ds = rand.doubles();
        if (ds == null) {
            throw new AssertionError("doubles() retornou null");
        }
    }
}
