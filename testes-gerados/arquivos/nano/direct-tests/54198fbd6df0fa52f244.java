/*
 * @test    @summary Test for IntPipeline.min() method.
 * @run main IntPipelineWitupTest
 */
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testMinWithValues();
        testMinWithEmptyStream();
    }

    static void testMinWithValues() {
        IntStream stream = IntStream.of(5, 3, 8, 2, 7);
        OptionalInt result = stream.min();
        if (!result.isPresent()) {
            throw new AssertionError("Expected present OptionalInt");
        }
        if (result.getAsInt() != 2) {
            throw new AssertionError("Expected min to be 2, but was " + result.getAsInt());
        }
    }

    static void testMinWithEmptyStream() {
        IntStream emptyStream = IntStream.empty();
        OptionalInt result = emptyStream.min();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalInt");
        }
    }
}
