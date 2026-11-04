/*
 * @test
 * @summary Exercises the public sized-stream toArray path that reaches Nodes.FixedNodeBuilder.begin(long)
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class NodesWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Object[] fromSized = Stream.of("a", "b", "c").toArray();
        check(Arrays.equals(fromSized, new Object[]{"a", "b", "c"}),
              "sized reference stream toArray must preserve elements");

        String[] typed = Stream.of("x", "y").toArray(String[]::new);
        check(typed.length == 2 && "x".equals(typed[0]) && "y".equals(typed[1]),
              "typed toArray must preserve order and length");

        Object[] empty = Stream.empty().toArray();
        check(empty.length == 0, "empty sized stream must produce zero-length array");

        int[] ints = IntStream.range(0, 5).toArray();
        check(Arrays.equals(ints, new int[]{0, 1, 2, 3, 4}),
              "int sized stream toArray must match range contents");

        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Object[] fromList = list.stream().toArray();
        check(fromList.length == 4 && fromList[3].equals(4),
              "list stream toArray must match source size and last element");
    }
}
