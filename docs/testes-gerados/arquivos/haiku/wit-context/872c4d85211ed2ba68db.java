/*
 * @test
 * @summary Public-API check for int array materialization of unsized streams, which reaches the Nodes int spined builder path
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class NodesWitupTest {

    public static void main(String[] args) {
        int[] expected = expectedMultiplesOfThree(50);

        int[] sequential = IntStream.range(0, 50)
                .filter(i -> i % 3 == 0)
                .toArray();
        check(Arrays.equals(expected, sequential), "sequential filtered toArray mismatch");

        int[] parallel = IntStream.range(0, 50)
                .parallel()
                .filter(i -> i % 3 == 0)
                .toArray();
        check(Arrays.equals(expected, parallel), "parallel filtered toArray mismatch");

        int[] empty = IntStream.range(0, 10)
                .filter(i -> false)
                .toArray();
        check(empty.length == 0, "empty filtered toArray should have length 0");

        int[] single = IntStream.range(0, 100)
                .filter(i -> i == 42)
                .toArray();
        check(Arrays.equals(new int[] {42}, single), "single-element toArray mismatch");
    }

    private static int[] expectedMultiplesOfThree(int bound) {
        int count = 0;
        for (int i = 0; i < bound; i++) {
            if (i % 3 == 0) {
                count++;
            }
        }
        int[] result = new int[count];
        int k = 0;
        for (int i = 0; i < bound; i++) {
            if (i % 3 == 0) {
                result[k++] = i;
            }
        }
        return result;
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
