/*
 * @test
 * @summary Observable regression check for unsized stream toArray, which reaches the spined node builder copy path in Nodes
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NodesWitupTest {

    public static void main(String[] args) {
        Object[] unsized = Stream.iterate(0, i -> i + 1).limit(1000).toArray();
        check(unsized.length == 1000, "unexpected length for unsized stream: " + unsized.length);
        for (int i = 0; i < unsized.length; i++) {
            check(unsized[i].equals(i), "unexpected element at index " + i);
        }

        String[] strings = Stream.of("a", "b", "c", "d")
                .filter(x -> !x.equals("b"))
                .toArray(String[]::new);
        check(Arrays.equals(strings, new String[] {"a", "c", "d"}), "unexpected filtered array: " + Arrays.toString(strings));

        int[] ints = IntStream.range(0, 5000).filter(i -> i % 2 == 0).toArray();
        check(ints.length == 2500, "unexpected int array length: " + ints.length);
        for (int i = 0; i < ints.length; i++) {
            check(ints[i] == i * 2, "unexpected int element at index " + i);
        }

        Integer[] empty = Stream.<Integer>empty().toArray(Integer[]::new);
        check(empty.length == 0, "empty stream must produce empty array");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
