/*
 * @test
 * @summary Verifies public stream toArray behavior that exercises Nodes.asArray through the public API
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NodesWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSizedReferenceStream() {
        String[] result = Stream.of("a", "b", "c").toArray(String[]::new);
        check(Arrays.equals(result, new String[] {"a", "b", "c"}), "sized stream content");
        check(result.getClass().getComponentType() == String.class, "component type should be String");
    }

    static void testEmptyStream() {
        Object[] result = Stream.empty().toArray(Object[]::new);
        check(result.length == 0, "empty stream should produce empty array");
    }

    static void testSizedFromList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Integer[] result = list.stream().toArray(Integer[]::new);
        check(Arrays.equals(result, new Integer[] {1, 2, 3, 4}), "list stream content");
        check(result.getClass() == Integer[].class, "array class should be Integer[]");
    }

    static void testUnsizedFilteredStream() {
        String[] result = Stream.of("x", "yy", "zzz", "w")
                .filter(s -> s.length() > 1)
                .toArray(String[]::new);
        check(Arrays.equals(result, new String[] {"yy", "zzz"}), "filtered stream content");
    }

    static void testIntStreamToArray() {
        int[] result = IntStream.range(0, 10).toArray();
        check(result.length == 10, "int stream length");
        for (int i = 0; i < 10; i++) {
            check(result[i] == i, "int stream value at " + i);
        }
    }

    static void testParallelOrderedToArray() {
        List<Integer> list = Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12);
        Integer[] result = list.parallelStream().map(i -> i * 2).toArray(Integer[]::new);
        Integer[] expected = new Integer[] {10, 12, 14, 16, 18, 20, 22, 24};
        check(Arrays.equals(result, expected), "parallel ordered content");
    }

    public static void main(String[] args) {
        testSizedReferenceStream();
        testEmptyStream();
        testSizedFromList();
        testUnsizedFilteredStream();
        testIntStreamToArray();
        testParallelOrderedToArray();
    }
}
