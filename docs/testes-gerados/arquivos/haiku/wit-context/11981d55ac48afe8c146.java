/*
 * @test
 * @summary Exercises Nodes.ArrayNode.asArray indirectly through Stream.toArray with public APIs
 * @run main NodesWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NodesWitupTest {

    public static void main(String[] args) {
        String[] strings = Stream.of("a", "b", "c").toArray(String[]::new);
        check(strings.getClass() == String[].class, "array type must follow generator");
        check(Arrays.equals(strings, new String[]{"a", "b", "c"}), "sized stream content mismatch");

        Integer[] ints = IntStream.range(0, 10).boxed().toArray(Integer[]::new);
        check(ints.length == 10, "sized IntStream length mismatch");
        for (int i = 0; i < ints.length; i++) {
            check(ints[i] == i, "element mismatch at index " + i);
        }

        Object[] empty = Stream.empty().toArray(Object[]::new);
        check(empty.length == 0, "empty stream must yield empty array");

        Integer[] filtered = Stream.of(1, 2, 3, 4).filter(x -> x % 2 == 0).toArray(Integer[]::new);
        check(Arrays.equals(filtered, new Integer[]{2, 4}), "unsized stream content mismatch");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
