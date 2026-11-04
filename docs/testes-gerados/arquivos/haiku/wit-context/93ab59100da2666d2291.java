/*
 * @test
 * @summary Public observable behavior of Stream/IntStream/LongStream sorted(), which exercises SortedOps sink begin() without relying on package-private access
 * @run main SortedOpsWitupTest
 */
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class SortedOpsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        List<Integer> natural = Stream.of(3, 1, 2).sorted().collect(Collectors.toList());
        check(natural.equals(Arrays.asList(1, 2, 3)), "natural order reference sort failed: " + natural);

        List<String> reversed = Stream.of("b", "a", "c")
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        check(reversed.equals(Arrays.asList("c", "b", "a")), "comparator sort failed: " + reversed);

        List<Integer> empty = Stream.<Integer>empty().sorted().collect(Collectors.toList());
        check(empty.isEmpty(), "empty sorted stream should be empty");

        int[] ints = IntStream.of(5, 4, 6).sorted().toArray();
        check(Arrays.equals(ints, new int[]{4, 5, 6}), "int sorted failed: " + Arrays.toString(ints));

        long[] longs = LongStream.of(9L, 7L, 8L).sorted().toArray();
        check(Arrays.equals(longs, new long[]{7L, 8L, 9L}), "long sorted failed: " + Arrays.toString(longs));

        boolean npe = false;
        try {
            Stream.of("a", null).sorted().collect(Collectors.toList());
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "sorting null elements in natural order should throw NullPointerException");
    }
}
