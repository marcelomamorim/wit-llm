/*
 * @test
 * @summary Testa o método forEach de LongPipeline para comportamento observável.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachNormal();
        testForEachWithNull();
    }

    static void testForEachNormal() {
        try {
            LongConsumer consumer = new LongConsumer() {
                long sum = 0;
                @Override
                public void accept(long value) {
                    sum += value;
                }
            };
            LongPipeline pipeline = LongPipeline.of(new long[]{1, 2, 3, 4, 5});
            pipeline.forEach(consumer);
        } catch (Exception e) {
            throw new RuntimeException("Erro em testForEachNormal", e);
        }
    }

    static void testForEachWithNull() {
        try {
            LongConsumer consumer = null;
            LongPipeline pipeline = LongPipeline.of(new long[]{1, 2, 3});
            pipeline.forEach(consumer);
            // Espera-se NullPointerException
        } catch (NullPointerException e) {
            // esperado
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado em testForEachWithNull", e);
        }
    }
}
