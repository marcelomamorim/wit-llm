/*
 * @test
 * @summary Public stream API smoke test for sized toArray paths that use Nodes internally
 * @run main NodesWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NodesWitupTest {

    public static void main(String[] args) {
        Object[] objs = Stream.of("a", "b", "c").toArray();
        check(objs.length == 3, "length of Object[] from Stream.of");
        check("a".equals(objs[0]) && "c".equals(objs[2]), "elements of Object[] from Stream.of");

        int[] ints = IntStream.range(0, 5).toArray();
        check(Arrays.equals(ints, new int[] {0, 1, 2, 3, 4}), "IntStream.range toArray");

        String[] strs = Stream.of("x", "y").toArray(String[]::new);
        check(strs.length == 2 && "x".equals(strs[0]) && "y".equals(strs[1]), "typed toArray");

        Object[] empty = Stream.empty().toArray();
        check(empty.length == 0, "empty stream toArray");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
