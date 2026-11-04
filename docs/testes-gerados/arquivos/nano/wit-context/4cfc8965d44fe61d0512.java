/*
 * @test
 * @summary Tests for java.util.stream.ReferencePipeline methods
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Function;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNullMapper();
    }

    public static void testFlatMapWithNullMapper() {
        Stream<String> stream = Stream.of("a", "b", "c");
        try {
            ReferencePipeline.<String, String>flatMap(stream, null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null mapper");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
