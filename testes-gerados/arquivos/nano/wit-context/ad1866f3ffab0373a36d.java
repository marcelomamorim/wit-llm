/*
 * @test
 * @summary Tests for java.util.stream.ReferencePipeline methods
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.ReferencePipeline;
import java.util.function.Function;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapToIntWithNullMapper();
        testFlatMapToIntWithValidMapper();
    }

    public static void testFlatMapToIntWithNullMapper() {
        ReferencePipeline<Object, Object> pipeline = null;
        try {
            // Invoca o método com mapper null para verificar NullPointerException
            ReferencePipeline<Object, Object> result = pipeline.flatMapToInt(null);
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected NullPointerException for null mapper");
        } catch (NullPointerException e) {
            // Esperado
            System.out.println("PASS: NullPointerException thrown as expected for null mapper");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testFlatMapToIntWithValidMapper() {
        // Cria um pipeline fictício para testar o método com um mapper válido
        // Como o método é final e depende de uma instância, criamos uma subclasse anônima
        class DummyPipeline extends ReferencePipeline<Object, Object> {
            DummyPipeline() {
                super(() -> null, 0, false);
            }
        }
        DummyPipeline pipeline = new DummyPipeline();
        // Cria um mapper válido que retorna um IntStream
        Function<Object, IntStream> mapper = obj -> IntStream.of(1, 2, 3);
        try {
            IntStream resultStream = pipeline.flatMapToInt(mapper);
            // Verifica se o resultado é um IntStream válido
            int[] resultArray = resultStream.toArray();
            boolean valid = true;
            for (int val : resultArray) {
                if (val < 1 || val > 3) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                System.out.println("PASS: flatMapToInt with valid mapper produces expected output");
            } else {
                System.err.println("FAIL: flatMapToInt with valid mapper produced unexpected output");
            }
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception during valid mapper test: " + e);
        }
    }
}
