/*
 * @test
 * @summary Tests for LongPipeline.mapToDouble method behavior with null mapper.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.stream.DoubleStream;
import java.util.function.LongToDoubleFunction;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMapToDouble_NullMapper();
    }

    public static void testMapToDouble_NullMapper() {
        LongPipeline pipeline = createSamplePipeline();
        try {
            pipeline.mapToDouble(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }

    private static LongPipeline createSamplePipeline() {
        // Create a minimal LongPipeline instance for testing purposes.
        // Since LongPipeline is abstract, we need a concrete subclass or a mock.
        // For simplicity, assume a minimal anonymous subclass with stub methods.
        return new LongPipeline(null, 0, false) {
            @Override
            public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
                return null;
            }
        };
    }
}
