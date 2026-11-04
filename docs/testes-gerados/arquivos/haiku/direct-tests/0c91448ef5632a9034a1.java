/*
 * @test
 * @summary Indirect checks of Nodes builder behavior through public sized stream APIs
 * @run main NodesWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NodesWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    public static void main(String[] args) {
        int[] seq = IntStream.range(0, 100).toArray();
        check(seq.length == 100, "int length");
        for (int i = 0; i < seq.length; i++) {
            check(seq[i] == i, "int element " + i);
        }

        int[] par = IntStream.range(0, 10000).parallel().toArray();
        check(par.length == 10000, "parallel int length");
        for (int i = 0; i < par.length; i++) {
            check(par[i] == i, "parallel int element " + i);
        }

        Object[] objs = Stream.of("a", "b", "c").toArray();
        check(Arrays.equals(objs, new Object[] {"a", "b", "c"}), "ref array");

        Object[] empty = Stream.empty().toArray();
        check(empty.length == 0, "empty ref array");

        long[] longs = IntStream.range(0, 50).asLongStream().toArray();
        check(longs.length == 50 && longs[49] == 49L, "long array");

        double[] doubles = IntStream.range(0, 20).asDoubleStream().toArray();
        check(doubles.length == 20 && doubles[19] == 19.0d, "double array");
    }
}
