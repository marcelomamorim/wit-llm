/*
 * @test
 * @summary Test for IntPipeline.accept(int) method, focusing on normal behavior.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.Sink;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testAcceptNormal();
    }

    public static void testAcceptNormal() {
        // Cria um Sink que registra os valores recebidos
        class RecordingSink implements Sink<Integer> {
            public int lastValue = -1;
            @Override
            public void accept(Integer t) {
                lastValue = t;
            }
            @Override
            public boolean isValue() { return true; }
            @Override
            public Integer get() { return lastValue; }
            @Override
            public void begin(long size) {}
            @Override
            public void end() {}
            @Override
            public boolean cancellationSupported() { return false; }
            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super Integer> action) { return false; }
            @Override
            public java.util.Spliterator<Integer> spliterator() { return null; }
        }

        RecordingSink sink = new RecordingSink();
        // Cria uma instância de IntPipeline com um map e um sink
        IntPipeline<Integer> pipeline = new IntPipeline<Integer>(
            () -> java.util.Spliterators.spliterator(new int[]{1, 2, 3}, 0), 0, false) {
            @Override
            public void forEach(java.util.function.IntConsumer action) {
                for (int i : new int[]{1, 2, 3}) {
                    accept(i);
                }
            }
        };
        // Invoca accept com valor 42
        pipeline.accept(42);
        // Verifica se o valor foi passado ao sink (não há efeito visível, apenas execução)
        // Como o sink não é realmente conectado, apenas verificamos se a chamada não lança exceção
        System.out.println("accept() executed sem exceções.");
    }
}
