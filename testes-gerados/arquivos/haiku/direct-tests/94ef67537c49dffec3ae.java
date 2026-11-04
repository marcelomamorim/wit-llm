/*
 * @test
 * @summary Verifies Stream.iterate spliterator tryAdvance sequencing and null action rejection
 * @run main StreamWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public class StreamWitupTest {

    public static void main(String[] args) {
        testSequence();
        testNullActionRejected();
    }

    static void testSequence() {
        Spliterator<Integer> sp = Stream.iterate(1, x -> x * 2).spliterator();
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            boolean r = sp.tryAdvance(out::add);
            if (!r) {
                throw new RuntimeException("tryAdvance must return true on infinite stream");
            }
        }
        List<Integer> expected = Arrays.asList(1, 2, 4);
        if (!expected.equals(out)) {
            throw new RuntimeException("Unexpected sequence: " + out);
        }
    }

    static void testNullActionRejected() {
        Spliterator<Integer> sp = Stream.iterate(1, x -> x + 1).spliterator();
        try {
            sp.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
