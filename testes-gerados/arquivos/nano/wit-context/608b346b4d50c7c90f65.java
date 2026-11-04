/*
 * @test
 * @summary Testa o método flatMap de LongPipeline para comportamento esperado com entrada nula.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongFunction;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNullMapper();
    }

    public static void testFlatMapWithNullMapper() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        try {
            stream.flatMap(null);
            System.err.println("Erro: Esperado NullPointerException não foi lançada.");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Passou: NullPointerException lançada como esperado.");
        } catch (Exception e) {
            System.err.println("Erro: Exceção inesperada: " + e);
            System.exit(1);
        }
    }
}
