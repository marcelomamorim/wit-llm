/*
 * @test
 * @summary Checks public IntStream.toArray behavior that goes through Nodes primitive array conversion
 * @run main NodesWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;

public class NodesWitupTest {

    public static void main(String[] args) {
        int[] range = IntStream.range(0, 5).toArray();
        check(Arrays.equals(range, new int[]{0, 1, 2, 3, 4}), "range toArray");

        int[] empty = IntStream.empty().toArray();
        check(empty.length == 0, "empty toArray");

        int[] iterated = IntStream.iterate(0, x -> x + 1).limit(7).toArray();
        check(Arrays.equals(iterated, new int[]{0, 1, 2, 3, 4, 5, 6}), "limited iterate toArray");

        int[] flat = IntStream.of(1, 2)
                .flatMap(x -> IntStream.of(x, x + 10))
                .toArray();
        check(Arrays.equals(flat, new int[]{1, 11, 2, 12}), "flatMap toArray");

        int[] parallel = IntStream.range(0, 10000)
                .parallel()
                .map(x -> x * 2)
                .toArray();
        check(parallel.length == 10000, "parallel length");
        check(parallel[0] == 0 && parallel[9999] == 19998, "parallel values");
        for (int i = 0; i < parallel.length; i++) {
            check(parallel[i] == i * 2, "parallel order at " + i);
        }

        int[] repeated = IntStream.range(0, 3).toArray();
        int[] again = IntStream.range(0, 3).toArray();
        check(Arrays.equals(repeated, again), "repeated toArray is deterministic");
        check(repeated != again, "each toArray returns a new array");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
