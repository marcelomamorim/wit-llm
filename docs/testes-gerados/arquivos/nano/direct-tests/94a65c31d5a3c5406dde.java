/*
 * @test    @summary Test the max() method of IntPipeline for correct behavior.
 * @run main IntPipelineWitupTest
 */
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.IntPipeline;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testMaxWithValues();
        testMaxWithEmptyStream();
    }

    static void testMaxWithValues() {
        IntStream stream = IntStream.of(1, 3, 2);
        OptionalInt result = ((IntPipeline) stream).max();
        if (!result.isPresent()) {
            throw new AssertionError("Expected present value for max()");
        }
        if (result.getAsInt() != 3) {
            throw new AssertionError("Expected max to be 3, but was " + result.getAsInt());
        }
    }

    static void testMaxWithEmptyStream() {
        IntStream emptyStream = IntStream.empty();
        OptionalInt result = ((IntPipeline) emptyStream).max();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional for max() on empty stream");
        }
    }
}
