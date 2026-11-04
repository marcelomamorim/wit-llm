/*
 * @test    @summary Test for LongPipeline.accept(long t) method.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testAcceptMethod();
    }

    public static void testAcceptMethod() {
        class DummyDownstream implements LongConsumer {
            long lastValue = 0;
            boolean called = false;

            @Override
            public void accept(long value) {
                lastValue = value;
                called = true;
            }
        }

        DummyDownstream downstream = new DummyDownstream();
        LongPipeline pipeline = new LongPipeline() {
            @Override
            public void accept(long t) {
                downstream.accept(t);
            }
        };
        long testValue = 42L;
        pipeline.accept(testValue);
        assert downstream.called : "Downstream accept was not called";
        assert downstream.lastValue == testValue : "Value passed to downstream is incorrect";
    }
}
