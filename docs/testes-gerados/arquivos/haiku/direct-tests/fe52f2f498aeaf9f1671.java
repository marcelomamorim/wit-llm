/*
 * @test
 * @summary Exercises Nodes.begin indirectly through sized and unsized stream toArray() calls
 * @run main NodesWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NodesWitupTest {
    public static void main(String[] args) {
        String[] sized = Stream.of("a", "b", "c").toArray(String[]::new);
        if (!Arrays.equals(sized, new String[]{"a", "b", "c"})) {
            throw new RuntimeException("sized toArray mismatch: " + Arrays.toString(sized));
        }

        int[] ints = IntStream.range(0, 5).toArray();
        if (!Arrays.equals(ints, new int[]{0, 1, 2, 3, 4})) {
            throw new RuntimeException("IntStream toArray mismatch: " + Arrays.toString(ints));
        }

        Object[] empty = Stream.of().toArray();
        if (empty.length != 0) {
            throw new RuntimeException("empty toArray length: " + empty.length);
        }

        Integer[] filtered = Stream.of(1, 2, 3, 4, 5)
                .filter(i -> i % 2 == 0)
                .toArray(Integer[]::new);
        if (!Arrays.equals(filtered, new Integer[]{2, 4})) {
            throw new RuntimeException("unsized toArray mismatch: " + Arrays.toString(filtered));
        }
    }
}
