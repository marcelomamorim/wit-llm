/*
 * @test
 * @summary Verifies IntStream.findAny behavior for empty, single, filtered and parallel pipelines
 * @run main IntPipelineWitupTest
 */
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        OptionalInt empty = IntStream.empty().findAny();
        check(!empty.isPresent(), "empty stream must yield an empty OptionalInt");

        OptionalInt single = IntStream.of(42).findAny();
        check(single.isPresent() && single.getAsInt() == 42, "single element stream must return that element");

        OptionalInt minValue = IntStream.of(Integer.MIN_VALUE).findAny();
        check(minValue.isPresent() && minValue.getAsInt() == Integer.MIN_VALUE, "MIN_VALUE must be returned as is");

        OptionalInt none = IntStream.range(0, 10).filter(x -> x > 100).findAny();
        check(!none.isPresent(), "filter with no matches must yield an empty OptionalInt");

        OptionalInt mapped = IntStream.range(0, 10).map(x -> x * 2).filter(x -> x > 10).findAny();
        check(mapped.isPresent(), "mapped and filtered stream must contain a match");
        check(mapped.getAsInt() > 10 && mapped.getAsInt() % 2 == 0,
              "returned value must satisfy the pipeline predicate");

        OptionalInt unique = IntStream.range(0, 1000).parallel().filter(x -> x == 500).findAny();
        check(unique.isPresent() && unique.getAsInt() == 500,
              "parallel findAny with a unique match must return that match");

        OptionalInt many = IntStream.of(3, 1, 2).findAny();
        check(many.isPresent(), "non-empty stream must yield a present OptionalInt");
        int v = many.getAsInt();
        check(v == 1 || v == 2 || v == 3, "returned value must be one of the stream elements");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
