/*
 * @test
 * @summary Exercises the sized-stream toArray path that reaches Nodes FixedNodeBuilder.begin via public API
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

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Object[] arr = list.stream().toArray();
        check(arr.length == 3, "expected length 3 but was " + arr.length);
        check(Arrays.equals(arr, new Object[] {1, 2, 3}), "unexpected contents " + Arrays.toString(arr));

        Object[] empty = new ArrayList<Integer>().stream().toArray();
        check(empty.length == 0, "expected empty array");

        int[] ints = IntStream.range(0, 5).toArray();
        check(Arrays.equals(ints, new int[] {0, 1, 2, 3, 4}), "unexpected int contents " + Arrays.toString(ints));

        String[] strs = Stream.of("a", "b").toArray(String[]::new);
        check(strs.length == 2 && strs[0].equals("a") && strs[1].equals("b"), "unexpected string contents");
    }
}
