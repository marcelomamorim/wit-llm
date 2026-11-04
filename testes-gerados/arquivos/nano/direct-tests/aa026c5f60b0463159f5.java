/*
 * @test
 * @summary Test for LongPipeline.findAny() method.
 * @run main LongPipelineWitupTest
 */
import java.util.OptionalLong;
import java.util.stream.LongStream;
import java.util.stream.Collectors;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testFindAnyWithElements();
        testFindAnyEmpty();
    }

    static void testFindAnyWithElements() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        OptionalLong result = stream.findAny();
        if (!result.isPresent()) {
            throw new AssertionError("Expected present value, but was empty");
        }
        long value = result.getAsLong();
        if (value != 1L && value != 2L && value != 3L) {
            throw new AssertionError("Unexpected value: " + value);
        }
    }

    static void testFindAnyEmpty() {
        LongStream emptyStream = LongStream.empty();
        OptionalLong result = emptyStream.findAny();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty result, but was present: " + result.getAsLong());
        }
    }
}
