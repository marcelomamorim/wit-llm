/*
 * @test
 * @summary Tests for LongPipeline methods, focusing on exception paths.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.stream.Stream;
import java.util.function.LongFunction;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMapToObjWithNullMapper();
    }

    public static void testMapToObjWithNullMapper() {
        LongFunction<?> mapper = null;
        try {
            LongPipeline<Long> pipeline = new LongPipeline<Long>(null, 0, false) {
                @Override
                public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
                    return null;
                }
            };
            pipeline.mapToObj(mapper);
            // Expect NullPointerException
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
