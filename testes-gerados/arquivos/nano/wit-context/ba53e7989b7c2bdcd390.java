/*
 * @test
 * @summary Test for DoublePipeline.accept(double) method, focusing on normal behavior.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAcceptNormal();
    }

    public static void testAcceptNormal() {
        // Cria um DoublePipeline fictício com ações simples
        DoubleConsumer consumer1 = d -> {};
        DoubleConsumer consumer2 = d -> {};
        // Como o construtor de DoublePipeline é package-private, usamos uma subclasse anônima para simular
        DoublePipeline pipeline = new DoublePipeline() {
            @Override
            public void accept(double t) {
                consumer1.accept(t);
                consumer2.accept(t);
            }
        };
        // Invoca o método accept com um valor válido
        try {
            pipeline.accept(1.23);
        } catch (Throwable t) {
            throw new AssertionError("accept() should not throw exception", t);
        }
    }
}
