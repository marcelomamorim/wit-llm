/*
 * @test
 * @summary Public IntStream.toArray() path that exercises the sized int collector used by Nodes
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class NodesWitupTest {

    public static void main(String[] args) {
        int[] range = IntStream.range(0, 1000).toArray();
        if (range.length != 1000) {
            throw new RuntimeException("unexpected length: " + range.length);
        }
        for (int i = 0; i < range.length; i++) {
            if (range[i] != i) {
                throw new RuntimeException("unexpected value at " + i + ": " + range[i]);
            }
        }

        int[] src = {5, -3, 0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] copy = Arrays.stream(src).toArray();
        if (!Arrays.equals(src, copy)) {
            throw new RuntimeException("copy mismatch: " + Arrays.toString(copy));
        }

        int[] empty = IntStream.empty().toArray();
        if (empty.length != 0) {
            throw new RuntimeException("expected empty array, got length " + empty.length);
        }
    }
}
