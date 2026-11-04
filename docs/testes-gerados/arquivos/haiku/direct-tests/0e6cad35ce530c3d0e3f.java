/*
 * @test
 * @summary Verify IntStream.filter behavior observable through the public API
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        int[] evens = IntStream.range(0, 10).filter(x -> x % 2 == 0).toArray();
        check(Arrays.equals(evens, new int[]{0, 2, 4, 6, 8}), "evens");

        check(IntStream.range(0, 10).filter(x -> false).count() == 0, "none matching");

        check(IntStream.of(1, 2, 3).filter(x -> true).sum() == 6, "all matching");

        check(IntStream.of(5, -1, 7).filter(x -> x > 0).count() == 2, "positive count");

        int[] ordered = IntStream.of(3, 1, 4, 1, 5).filter(x -> x != 1).toArray();
        check(Arrays.equals(ordered, new int[]{3, 4, 5}), "order preserved");

        long chained = IntStream.range(0, 20)
                .filter(x -> x % 2 == 0)
                .filter(x -> x % 3 == 0)
                .sum();
        check(chained == 0 + 6 + 12 + 18, "chained filters");

        check(IntStream.empty().filter(x -> true).count() == 0, "empty source");

        boolean npe = false;
        try {
            IntStream.of(1).filter(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null predicate rejected");
    }

    static void check(boolean condition, String name) {
        if (!condition) {
            throw new RuntimeException("Failed: " + name);
        }
    }
}
