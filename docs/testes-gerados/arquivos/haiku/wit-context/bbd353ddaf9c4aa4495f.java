/*
 * @test
 * @summary Checks public IntStream.concat(...).toArray() results, which exercise Nodes primitive array conversion
 * @run main NodesWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;

public class NodesWitupTest {
    public static void main(String[] args) {
        int[] expected = {1, 2, 3, 4, 5};
        int[] actual = IntStream.concat(IntStream.of(1, 2), IntStream.of(3, 4, 5)).toArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected concatenated array: " + Arrays.toString(actual));
        }

        int[] empty = IntStream.concat(IntStream.empty(), IntStream.empty()).toArray();
        if (empty.length != 0) {
            throw new RuntimeException("Expected empty array but got length " + empty.length);
        }
    }
}
