/*
 * @test
 * @summary Checks that sized IntStream collection through Nodes.intBuilder begin(size) produces arrays with the expected length and contents
 * @run main NodesWitupTest
 */

import java.util.stream.IntStream;

public class NodesWitupTest {

    public static void main(String[] args) {
        int[] a = IntStream.range(0, 16).toArray();
        check(a.length == 16, "length expected 16 but was " + a.length);
        for (int i = 0; i < a.length; i++) {
            check(a[i] == i, "element " + i + " expected " + i + " but was " + a[i]);
        }

        int[] empty = IntStream.empty().toArray();
        check(empty.length == 0, "empty stream length expected 0 but was " + empty.length);

        int[] big = IntStream.range(0, 1000).map(x -> x * 2).toArray();
        check(big.length == 1000, "big length expected 1000 but was " + big.length);
        check(big[999] == 1998, "big[999] expected 1998 but was " + big[999]);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
