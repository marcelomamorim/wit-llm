/*
 * @test
 * @summary Witup generated checks for LongPipeline.findFirst through public LongStream API
 * @run main LongPipelineWitupTest
 */
import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        OptionalLong empty = LongStream.empty().findFirst();
        check(!empty.isPresent(), "findFirst on empty stream must be empty");

        OptionalLong first = LongStream.of(5L, 6L, 7L).findFirst();
        check(first.isPresent() && first.getAsLong() == 5L, "findFirst must return first element");

        OptionalLong mapped = LongStream.of(1L, 2L).map(x -> x * 2).findFirst();
        check(mapped.isPresent() && mapped.getAsLong() == 2L, "findFirst after map must return mapped first element");

        LongStream reused = LongStream.of(1L);
        reused.findFirst();
        try {
            reused.findFirst();
            throw new RuntimeException("reusing a consumed stream must throw IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
