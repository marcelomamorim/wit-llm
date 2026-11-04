/*
 * @test
 * @summary Test LongPipeline.findFirst() method for correct behavior.
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFindFirstWithElements();
        testFindFirstEmpty();
    }

    static void testFindFirstWithElements() {
        LongStream stream = LongStream.of(10L, 20L, 30L);
        OptionalLong result = stream.findFirst();
        if (!result.isPresent()) {
            throw new AssertionError("Expected present value for findFirst with elements");
        }
        if (result.getAsLong() != 10L) {
            throw new AssertionError("Expected 10L, but got " + result.getAsLong());
        }
    }

    static void testFindFirstEmpty() {
        LongStream emptyStream = LongStream.empty();
        OptionalLong result = emptyStream.findFirst();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalLong for empty stream");
        }
    }
}
