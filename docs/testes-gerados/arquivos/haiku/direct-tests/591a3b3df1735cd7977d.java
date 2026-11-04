/*
 * @test
 * @summary Checks Random.longs(...) forEach traversal, which reaches RandomLongsSpliterator.forEachRemaining
 * @run main RandomWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        List<Long> a = collect(new Random(42L).longs(100, 5L, 15L));
        List<Long> b = collect(new Random(42L).longs(100, 5L, 15L));

        if (a.size() != 100) {
            throw new RuntimeException("Expected 100 elements, got " + a.size());
        }
        if (!a.equals(b)) {
            throw new RuntimeException("Same seed must produce identical sequences");
        }
        for (long v : a) {
            if (v < 5L || v >= 15L) {
                throw new RuntimeException("Value out of bounds: " + v);
            }
        }

        List<Long> empty = collect(new Random(1L).longs(0, 0L, 10L));
        if (!empty.isEmpty()) {
            throw new RuntimeException("Zero-size stream must not invoke consumer");
        }

        List<Long> c = collect(new Random(7L).longs(3));
        if (c.size() != 3) {
            throw new RuntimeException("Expected 3 elements, got " + c.size());
        }
    }

    static List<Long> collect(LongStream s) {
        List<Long> l = new ArrayList<>();
        s.forEach(v -> l.add(v));
        return l;
    }
}
