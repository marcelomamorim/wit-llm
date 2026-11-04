/*
 * @test
 * @summary Behavioral checks for ReferencePipeline.filter through the public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        List<String> kept = Arrays.asList("a", "bb", "ccc", "dd").stream()
                .filter(s -> s.length() == 2)
                .collect(Collectors.toList());
        check(kept.equals(Arrays.asList("bb", "dd")), "filter must keep matching elements in encounter order");

        long none = Stream.of(1, 2, 3).filter(x -> x > 5).count();
        check(none == 0, "filter with no matches must produce an empty stream");

        long all = Stream.of(1, 2, 3, 4).filter(x -> true).count();
        check(all == 4, "filter accepting all elements must keep every element");

        AtomicInteger calls = new AtomicInteger();
        Stream<Integer> lazy = Stream.of(1, 2, 3).filter(x -> {
            calls.incrementAndGet();
            return x % 2 == 1;
        });
        check(calls.get() == 0, "filter must be lazy until a terminal operation runs");
        long odd = lazy.count();
        check(odd == 2, "count after filter must reflect predicate results");
        check(calls.get() == 3, "predicate must be applied once per upstream element");

        List<Integer> chained = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(x -> x % 2 == 0)
                .filter(x -> x > 4)
                .collect(Collectors.toList());
        check(chained.equals(Arrays.asList(6, 8, 10)), "chained filters must compose");

        boolean npe = false;
        try {
            Stream.of(1).filter(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "filter(null) must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
