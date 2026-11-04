/*
 * @test
 * @summary Sized public stream toArray should return exact contents, exercising the fixed-size node builder path
 * @run main NodesWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;

public class NodesWitupTest {
    public static void main(String[] args) {
        int[] sizes = {0, 1, 7, 100};
        for (int n : sizes) {
            Integer[] expected = new Integer[n];
            for (int i = 0; i < n; i++) {
                expected[i] = i;
            }
            Integer[] actual = IntStream.range(0, n).boxed().toArray(Integer[]::new);
            if (!Arrays.equals(expected, actual)) {
                throw new RuntimeException("Mismatch for range size " + n);
            }
        }

        String[] src = {"a", "b", "c"};
        String[] copy = Arrays.asList(src).stream().toArray(String[]::new);
        if (!Arrays.equals(src, copy)) {
            throw new RuntimeException("Mismatch for sized list stream");
        }
    }
}
