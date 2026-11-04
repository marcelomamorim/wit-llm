/*
 * @test
 * @summary Exercises public stream APIs that route array collection through Nodes.copyInto paths
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NodesWitupTest {

    public static void main(String[] args) {
        String[] concat = Stream.concat(Stream.of("a", "b"), Stream.of("c", "d"))
                .toArray(String[]::new);
        check(Arrays.equals(concat, new String[]{"a", "b", "c", "d"}),
              "concat toArray mismatch: " + Arrays.toString(concat));

        Integer[] flat = Stream.of(1, 2, 3)
                .parallel()
                .flatMap(i -> Stream.of(i, i * 10))
                .toArray(Integer[]::new);
        check(Arrays.equals(flat, new Integer[]{1, 10, 2, 20, 3, 30}),
              "parallel flatMap toArray mismatch: " + Arrays.toString(flat));

        int[] ints = IntStream.range(0, 5)
                .parallel()
                .flatMap(i -> IntStream.of(i, i))
                .toArray();
        check(Arrays.equals(ints, new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4}),
              "parallel IntStream flatMap mismatch: " + Arrays.toString(ints));

        String[] empty = Stream.<String>empty()
                .parallel()
                .toArray(String[]::new);
        check(empty.length == 0, "empty parallel toArray should be empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
